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
// Removido: import com.google.firebase.firestore.WriteBatch // Não é mais necessário aqui
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Tela_Cadastrar : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore // Instância do Firestore

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

    companion object {
        private const val TAG = "TelaCadastrar"
        // Constantes de slots de treino e exercício removidas
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // enableEdgeToEdge() // Considerar remover ou ajustar se causar problemas de layout
        setContentView(R.layout.activity_tela_cadastrar)

        // 1) Inicializa o FirebaseAuth e Firestore
        auth = Firebase.auth
        db = Firebase.firestore

        // 2) Liga as Views aos IDs do XML
        nomeLayout = findViewById(R.id.campoNome)
        emailLayout = findViewById(R.id.campoEmail)
        senhaLayout = findViewById(R.id.campoCriaSenha)

        editNome = findViewById(R.id.editNome)
        editEmail = findViewById(R.id.editEmail)
        editCriaSenha = findViewById(R.id.editCriaSenha)

        btnCadastrar = findViewById(R.id.btnCadastrar)
        btnVoltar = findViewById(R.id.imgVoltar)

        // 3) Clique em Cadastrar: valida, cria conta Auth, salva dados do usuário no Firestore e atualiza perfil Auth
        btnCadastrar.setOnClickListener {
            val nome = editNome.text.toString().trim()
            val email = editEmail.text.toString().trim()
            val senha = editCriaSenha.text.toString().trim()

            if (!validarCampos(nome, email, senha)) {
                return@setOnClickListener
            }

            alterarEstadoBotaoCadastro(false, "Cadastrando...")

            auth.createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "createUserWithEmail:success")
                        val firebaseUser = auth.currentUser
                        firebaseUser?.let { user ->
                            // Usuário criado no Auth, agora salvar apenas os dados básicos no Firestore
                            salvarDadosBasicosUsuarioFirestore(user.uid, nome, email) { sucessoFirestore ->
                                if (sucessoFirestore) {
                                    // Dados básicos no Firestore OK, agora atualizar perfil no Auth (displayName)
                                    atualizarPerfilAuth(user, nome) { sucessoAuthProfile ->
                                        if (sucessoAuthProfile) {
                                            Toast.makeText(this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show()
                                        } else {
                                            // Firestore OK, Auth profile update failed (minor issue)
                                            Toast.makeText(this, "Cadastro concluído. Nome de exibição não atualizado, você pode alterar no seu perfil.", Toast.LENGTH_LONG).show()
                                        }
                                        navegarParaLogin()
                                    }
                                } else {
                                    // Falha ao salvar dados básicos no Firestore.
                                    // Tentar deletar usuário do Auth para consistência.
                                    Log.w(TAG, "Falha ao salvar dados do usuário no Firestore. Tentando deletar usuário do Auth.")
                                    user.delete().addOnCompleteListener { deleteTask ->
                                        if (deleteTask.isSuccessful) {
                                            Log.d(TAG, "Usuário do Auth deletado após falha no Firestore.")
                                        } else {
                                            Log.w(TAG, "Falha ao deletar usuário do Auth.", deleteTask.exception)
                                        }
                                    }
                                    Toast.makeText(this, "Erro ao salvar dados do perfil. Tente novamente.", Toast.LENGTH_LONG).show()
                                    alterarEstadoBotaoCadastro(true, "Cadastrar")
                                }
                            }
                        } ?: run {
                            // Caso raro: task successful mas currentUser é null
                            Log.e(TAG, "createUserWithEmail:success mas currentUser é null.")
                            Toast.makeText(this, "Erro inesperado no cadastro. Tente novamente.", Toast.LENGTH_LONG).show()
                            alterarEstadoBotaoCadastro(true, "Cadastrar")
                        }
                    } else {
                        // Falha na criação do usuário no Auth
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        var mensagemErro = "Erro ao cadastrar: ${task.exception?.message}"
                        if (task.exception?.message?.contains("email address is already in use") == true) {
                            mensagemErro = "Este e-mail já está cadastrado. Tente fazer login ou use um e-mail diferente."
                            emailLayout.error = "E-mail já cadastrado" // Mantém erro específico no campo
                        }
                        Toast.makeText(this, mensagemErro, Toast.LENGTH_LONG).show()
                        alterarEstadoBotaoCadastro(true, "Cadastrar")
                    }
                }
        }

        btnVoltar.setOnClickListener {
            navegarParaLogin(false) // Não mostra mensagem de sucesso ao simplesmente voltar
        }
    }

    private fun validarCampos(nome: String, email: String, senha: String): Boolean {
        var valido = true
        if (nome.isEmpty()) {
            nomeLayout.error = "Informe seu nome"
            valido = false
        } else {
            nomeLayout.error = null
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

    private fun salvarDadosBasicosUsuarioFirestore(uid: String, nome: String, email: String, callback: (Boolean) -> Unit) {
        // Cria apenas o documento do usuário com informações básicas
        val userData = hashMapOf(
            "nome" to nome,
            "email" to email.lowercase(), // Salvar email em minúsculas para consistência
            "tipoUsuario" to "aluno",     // Definido automaticamente como aluno
            "dataCadastro" to FieldValue.serverTimestamp()
        )

        db.collection("usuarios").document(uid)
            .set(userData)
            .addOnSuccessListener {
                Log.d(TAG, "Dados básicos do usuário salvos no Firestore com UID: $uid")
                callback(true)
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Erro ao salvar dados básicos do usuário no Firestore para UID: $uid", e)
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

    private fun navegarParaLogin(mostrarMensagemSucesso: Boolean = true) {

        editNome.text.clear()
        editEmail.text.clear()
        editCriaSenha.text.clear()
        nomeLayout.error = null
        emailLayout.error = null
        senhaLayout.error = null

        val intent = Intent(this, Tela_Login::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
