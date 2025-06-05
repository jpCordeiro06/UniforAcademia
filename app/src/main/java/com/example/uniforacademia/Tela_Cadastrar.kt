package com.example.uniforacademia

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Tela_Cadastrar : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    // Layout containers para exibir erros
    private lateinit var nomeLayout: TextInputLayout
    private lateinit var matriculaLayout: TextInputLayout
    private lateinit var cursoLayout: TextInputLayout
    private lateinit var emailLayout: TextInputLayout
    private lateinit var senhaLayout: TextInputLayout

    // Campos de entrada
    private lateinit var editNome: EditText
    private lateinit var editMatricula: EditText
    private lateinit var editCurso: EditText
    private lateinit var editEmail: EditText
    private lateinit var editCriaSenha: EditText

    private lateinit var btnCadastrar: Button
    private lateinit var btnVoltar: ImageView

    companion object {
        private const val TAG = "TelaCadastrar"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // enableEdgeToEdge()
        setContentView(R.layout.activity_tela_cadastrar)

        auth = Firebase.auth
        db = Firebase.firestore

        // Liga as Views aos IDs do XML
        nomeLayout = findViewById(R.id.campoNome)
        matriculaLayout = findViewById(R.id.campoMatricula)
        cursoLayout = findViewById(R.id.campoCurso)
        emailLayout = findViewById(R.id.campoEmail)
        senhaLayout = findViewById(R.id.campoCriaSenha)

        editNome = findViewById(R.id.editNome)
        editMatricula = findViewById(R.id.editMatricula)
        editCurso = findViewById(R.id.editCurso)
        editEmail = findViewById(R.id.editEmail)
        editCriaSenha = findViewById(R.id.editCriaSenha)

        btnCadastrar = findViewById(R.id.btnCadastrar)
        btnVoltar = findViewById(R.id.imgVoltar)

        btnCadastrar.setOnClickListener {
            val nome = editNome.text.toString().trim()
            val matricula = editMatricula.text.toString().trim()
            val curso = editCurso.text.toString().trim()
            val email = editEmail.text.toString().trim()
            val senha = editCriaSenha.text.toString().trim()

            // Valida todos os campos, incluindo os novos
            if (!validarCampos(nome, matricula, curso, email, senha)) {
                return@setOnClickListener
            }

            alterarEstadoBotaoCadastro(false, "Cadastrando...")

            auth.createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "createUserWithEmail:success")
                        val firebaseUser = auth.currentUser
                        firebaseUser?.let { user ->
                            // Salva todos os dados do usuário no Firestore
                            salvarDadosUsuarioFirestore(user.uid, nome, matricula, curso, email) { sucessoFirestore ->
                                if (sucessoFirestore) {
                                    atualizarPerfilAuth(user, nome) { sucessoAuthProfile ->
                                        if (sucessoAuthProfile) {
                                            Toast.makeText(this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show()
                                        } else {
                                            Toast.makeText(this, "Cadastro concluído. Nome de exibição não atualizado.", Toast.LENGTH_LONG).show()
                                        }
                                        navegarParaLogin()
                                    }
                                } else {
                                    Toast.makeText(this, "Erro ao salvar dados do perfil. Tente novamente.", Toast.LENGTH_LONG).show()
                                    alterarEstadoBotaoCadastro(true, "Cadastrar")
                                }
                            }
                        } ?: run {
                            alterarEstadoBotaoCadastro(true, "Cadastrar")
                        }
                    } else {
                        var mensagemErro = "Erro ao cadastrar: ${task.exception?.message}"
                        if (task.exception?.message?.contains("email address is already in use") == true) {
                            mensagemErro = "Este e-mail já está cadastrado."
                            emailLayout.error = "E-mail já cadastrado"
                        }
                        Toast.makeText(this, mensagemErro, Toast.LENGTH_LONG).show()
                        alterarEstadoBotaoCadastro(true, "Cadastrar")
                    }
                }
        }

        btnVoltar.setOnClickListener {
            navegarParaLogin(false)
        }
    }

    // Função de validação atualizada para incluir matrícula e curso
    private fun validarCampos(nome: String, matricula: String, curso: String, email: String, senha: String): Boolean {
        var valido = true
        if (nome.isEmpty()) {
            nomeLayout.error = "Informe seu nome"
            valido = false
        } else {
            nomeLayout.error = null
        }

        if (matricula.isEmpty()) {
            matriculaLayout.error = "Informe sua matrícula"
            valido = false
        } else {
            matriculaLayout.error = null
        }

        if (curso.isEmpty()) {
            cursoLayout.error = "Informe seu curso"
            valido = false
        } else {
            cursoLayout.error = null
        }

        if (email.isEmpty()) {
            emailLayout.error = "Informe seu e-mail"
            valido = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailLayout.error = "Informe um e-mail válido"
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
        return valido
    }

    private fun alterarEstadoBotaoCadastro(habilitado: Boolean, texto: String) {
        btnCadastrar.isEnabled = habilitado
        btnCadastrar.text = texto
    }

    // Função de salvar no Firestore atualizada para incluir matrícula e curso
    private fun salvarDadosUsuarioFirestore(uid: String, nome: String, matricula: String, curso: String, email: String, callback: (Boolean) -> Unit) {
        val userData = hashMapOf(
            "nome" to nome,
            "matricula" to matricula, // NOVO
            "curso" to curso,         // NOVO
            "email" to email.lowercase(),
            "tipoUsuario" to "aluno",
            "dataCadastro" to FieldValue.serverTimestamp()
        )

        db.collection("usuarios").document(uid)
            .set(userData)
            .addOnSuccessListener {
                Log.d(TAG, "Dados do usuário salvos no Firestore com UID: $uid")
                callback(true)
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Erro ao salvar dados do usuário no Firestore para UID: $uid", e)
                callback(false)
            }
    }

    private fun atualizarPerfilAuth(user: com.google.firebase.auth.FirebaseUser, nome: String, callback: (Boolean) -> Unit) {
        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName(nome)
            .build()

        user.updateProfile(profileUpdates)
            .addOnCompleteListener { taskUpdate ->
                if (taskUpdate.isSuccessful) {
                    Log.d(TAG, "Perfil do Firebase Authentication atualizado (displayName).")
                    callback(true)
                } else {
                    Log.w(TAG, "Falha ao atualizar perfil do Firebase Authentication.", taskUpdate.exception)
                    callback(false)
                }
            }
    }

    // Função de navegação atualizada para limpar os novos campos
    private fun navegarParaLogin(mostrarMensagemSucesso: Boolean = true) {
        editNome.text.clear()
        editMatricula.text.clear()
        editCurso.text.clear()
        editEmail.text.clear()
        editCriaSenha.text.clear()

        nomeLayout.error = null
        matriculaLayout.error = null
        cursoLayout.error = null
        emailLayout.error = null
        senhaLayout.error = null

        val intent = Intent(this, Tela_Login::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}