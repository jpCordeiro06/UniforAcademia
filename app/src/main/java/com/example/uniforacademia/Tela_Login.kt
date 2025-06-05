package com.example.uniforacademia

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Tela_Login : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore // Instância do Firestore

    private lateinit var inputEmail: TextInputLayout
    private lateinit var inputSenha: TextInputLayout
    private lateinit var etEmail: EditText
    private lateinit var etSenha: EditText
    private lateinit var btnLogin: Button

    // Constante para a chave do Intent Extra
    companion object {
        const val EXTRA_TIPO_USUARIO = "TIPO_USUARIO"
        const val TAG = "TelaLogin" // Para logs
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_login)

        // Inicializa FirebaseAuth e FirebaseFirestore
        auth = Firebase.auth
        db = Firebase.firestore

        // Liga Views
        inputEmail = findViewById(R.id.inputEmail)
        inputSenha = findViewById(R.id.inputSenha)
        etEmail    = findViewById(R.id.etEmail)
        etSenha    = findViewById(R.id.etSenha)
        btnLogin   = findViewById(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val senha = etSenha.text.toString().trim()

            // Validações básicas
            if (email.isEmpty()) {
                inputEmail.error = "Informe seu e-mail"
                return@setOnClickListener
            } else {
                inputEmail.error = null
            }

            if (senha.isEmpty()) {
                inputSenha.error = "Informe sua senha"
                return@setOnClickListener
            } else {
                inputSenha.error = null
            }

            // Autentica no Firebase
            auth.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "signInWithEmail:success")
                        val user = auth.currentUser
                        user?.uid?.let { uid ->
                            buscarTipoUsuarioEContinuar(uid)
                        } ?: run {
                            // UID nulo, algo deu muito errado
                            Log.w(TAG, "UID do usuário é nulo após login bem-sucedido.")
                            Toast.makeText(
                                baseContext,
                                "Erro ao obter informações do usuário. Tente novamente.",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    } else {
                        // Falha na autenticação
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext, // Use baseContext ou applicationContext para Toasts fora do listener direto
                            "Falha na autenticação: ${task.exception?.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }
    }

    private fun buscarTipoUsuarioEContinuar(uid: String) {

        db.collection("usuarios").document(uid)
            .get()
            .addOnSuccessListener { document ->

                if (document != null && document.exists()) {
                    val tipoUsuario = document.getString("tipoUsuario")
                    if (tipoUsuario != null) {
                        Log.d(TAG, "Tipo de Usuário: $tipoUsuario")

                        // Navega para a tela inicial, passando o tipo de usuário
                        val intent = Intent(this, Inicio_Aluno::class.java).apply {
                            putExtra(EXTRA_TIPO_USUARIO, tipoUsuario)
                        }
                        startActivity(intent)
                        finish()
                    } else {
                        Log.w(TAG, "Campo 'tipoUsuario' não encontrado ou nulo para o UID: $uid")
                        Toast.makeText(
                            baseContext,
                            "Não foi possível determinar o tipo de usuário. Contate o suporte.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else {
                    Log.w(TAG, "Documento do usuário não encontrado no Firestore para o UID: $uid")
                    Toast.makeText(
                        baseContext,
                        "Perfil de usuário não encontrado. Contate o suporte.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Erro ao buscar tipo de usuário no Firestore:", exception)
                Toast.makeText(
                    baseContext,
                    "Erro ao buscar informações do perfil: ${exception.message}",
                    Toast.LENGTH_LONG
                ).show()
                // Opcional: deslogar o usuário
                // auth.signOut()
            }
    }
}
