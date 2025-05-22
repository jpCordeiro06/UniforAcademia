package com.example.uniforacademia

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.ktx.auth

class Tela_Cadastrar : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    // Layout containers para exibir erros
    private lateinit var nomeLayout: TextInputLayout
    private lateinit var emailLayout: TextInputLayout
    private lateinit var senhaLayout: TextInputLayout

    // Campos de entrada
    private lateinit var editNome: EditText
    private lateinit var editEmail: EditText
    private lateinit var editCriaSenha: EditText

    private lateinit var btnCadastrar: Button
    private lateinit var btnVoltar: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tela_cadastrar)

        // 1) Inicializa o FirebaseAuth
        auth = Firebase.auth

        // 2) Liga as Views aos IDs do XML
        nomeLayout      = findViewById(R.id.campoNome)
        emailLayout = findViewById(R.id.campoEmail)
        senhaLayout     = findViewById(R.id.campoCriaSenha)

        editNome       = findViewById(R.id.editNome)
        editEmail  = findViewById(R.id.editEmail)
        editCriaSenha  = findViewById(R.id.editCriaSenha)

        btnCadastrar = findViewById(R.id.btnCadastrar)
        btnVoltar    = findViewById(R.id.imgVoltar)

        // 3) Clique em Cadastrar: valida, cria conta e atualiza perfil
        btnCadastrar.setOnClickListener {
            val nome     = editNome.text.toString().trim()
            val email    = editEmail.text.toString().trim()
            val senha    = editCriaSenha.text.toString().trim()

            // 3.1) Validações
            var valido = true
            if (nome.isEmpty()) {
                nomeLayout.error = "Informe seu nome"
                valido = false
            } else {
                nomeLayout.error = null
            }
            if (email.isEmpty()) {
                emailLayout.error = "Informe seu email"
                valido = false
            } else {
                emailLayout.error = null
            }
            if (senha.length < 6) {
                senhaLayout.error = "Senha deve ter ao menos 6 caracteres"
                valido = false
            } else {
                senhaLayout.error = null
            }
            if (!valido) return@setOnClickListener

            // 3.2) Feedback de processamento
            btnCadastrar.isEnabled = false
            btnCadastrar.text = "Cadastrando..."

            // 3.3) Chama Firebase para criar usuário
            auth.createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // 3.4) Se criou, atualiza o displayName com o nome completo
                        auth.currentUser?.let { user ->
                            val profileUpdate = UserProfileChangeRequest.Builder()
                                .setDisplayName(nome)
                                .build()
                            user.updateProfile(profileUpdate)
                                .addOnCompleteListener { updTask ->
                                    // Após atualizar, vai para MainActivity
                                    navegarParaLogin()
                                }
                        }
                    } else {
                        // 3.5) Em caso de erro, mostra mensagem e reativa botão
                        Toast.makeText(
                            this,
                            "Erro ao cadastrar: ${task.exception?.localizedMessage}",
                            Toast.LENGTH_LONG
                        ).show()
                        btnCadastrar.isEnabled = true
                        btnCadastrar.text = "Cadastrar"
                    }
                }
        }

        // 4) Clique em Voltar: retorna para Tela de Login
        btnVoltar.setOnClickListener {
            startActivity(Intent(this, Tela_Login::class.java))
            finish()
        }
    }

    private fun navegarParaLogin() {
        startActivity(Intent(this, Tela_Login::class.java))
        finish()
    }
}
