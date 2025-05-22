package com.example.uniforacademia

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.ktx.auth

class Tela_Login : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var inputEmail: TextInputLayout
    private lateinit var inputSenha: TextInputLayout
    private lateinit var etEmail: EditText
    private lateinit var etSenha: EditText
    private lateinit var btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_login)

        // Inicializa FirebaseAuth
        auth = Firebase.auth

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
                inputEmail.error = "Informe seu e-mail  "
                return@setOnClickListener
            }
            if (senha.isEmpty()) {
                inputSenha.error = "Informe sua senha"
                return@setOnClickListener
            }
            inputEmail.error = null
            inputSenha.error = null

            // Autentica no Firebase
            auth.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sucesso: vai para a tela inicial
                        startActivity(Intent(this, Inicio::class.java))
                        finish()
                    } else {
                        // Falha: mostra erro
                        Toast.makeText(
                            this,
                            "Falha na autenticação: ${task.exception?.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }
    }
}
