package com.example.uniforacademia

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class EditPerfilAluno : AppCompatActivity() {

    private lateinit var etNome: EditText
    private lateinit var etEmail: EditText
    private lateinit var etMatricula: EditText
    private lateinit var etCurso: EditText

    private lateinit var btnSalvar: Button
    private lateinit var tvTituloEditarAluno: TextView

    private var alunoId: String? = null

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_perfil_aluno)

        etNome = findViewById(R.id.etNomeAluno)
        etEmail = findViewById(R.id.etEmailAluno)
        etMatricula = findViewById(R.id.etMatriculaAluno)
        etCurso = findViewById(R.id.etCursoAluno)
        btnSalvar = findViewById(R.id.btnSalvarAluno)
        tvTituloEditarAluno = findViewById(R.id.tvTituloEditarAluno)

        alunoId = intent.getStringExtra("alunoId")

        if (alunoId != null) {
            carregarDadosParaEdicao(alunoId!!)
        } else {
            Toast.makeText(this, "Erro: ID do aluno não fornecido para edição.", Toast.LENGTH_SHORT).show()
            finish()
        }

        tvTituloEditarAluno.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        btnSalvar.setOnClickListener {
            salvarAlteracoes()
        }

        val imgButtonInicio = findViewById<ImageButton>(R.id.imgButtonInicio)
        val imgButtonProf = findViewById<ImageButton>(R.id.imgButtonProf)
        val imgButtonMenu = findViewById<ImageButton>(R.id.imgButtonMenu)

        imgButtonInicio.setOnClickListener {
            val intent = Intent(this, Inicio_Aluno::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }

        imgButtonProf.setOnClickListener {
            val intent = Intent(this, ChamaProf::class.java)
            startActivity(intent)
        }

        imgButtonMenu.setOnClickListener {
            val intent = Intent(this, Menu::class.java)
            startActivity(intent)
        }
    }

    private fun carregarDadosParaEdicao(id: String) {
        db.collection("usuarios").document(id).get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val aluno = document.toObject(Aluno::class.java)
                    if (aluno != null) {
                        etNome.setText(aluno.nome)
                        etEmail.setText(aluno.email)
                        etMatricula.setText(aluno.matricula)
                        etCurso.setText(aluno.curso)
                    } else {
                        Toast.makeText(this, "Erro ao carregar dados do aluno.", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                } else {
                    Toast.makeText(this, "Aluno não encontrado.", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
            .addOnFailureListener { e ->
                Log.e("EditPerfilAluno", "Erro ao carregar dados: ${e.message}", e) // Log atualizado
                Toast.makeText(this, "Erro ao carregar dados para edição: ${e.message}", Toast.LENGTH_LONG).show()
                finish()
            }
    }

    private fun salvarAlteracoes() {
        val novoNome = etNome.text.toString().trim()
        val novoEmail = etEmail.text.toString().trim()
        val novaMatricula = etMatricula.text.toString().trim()
        val novoCurso = etCurso.text.toString().trim()

        if (novoNome.isEmpty() || novoEmail.isEmpty() || novaMatricula.isEmpty() || novoCurso.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
            return
        }

        if (alunoId != null) {
            val dadosAtualizados = hashMapOf(
                "nome" to novoNome,
                "email" to novoEmail,
                "matricula" to novaMatricula,
                "curso" to novoCurso
            )

            db.collection("usuarios").document(alunoId!!)
                .update(dadosAtualizados as Map<String, Any>)
                .addOnSuccessListener {
                    Toast.makeText(this, "Perfil atualizado com sucesso!", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .addOnFailureListener { e ->
                    Log.e("EditPerfilAluno", "Erro ao atualizar dados: ${e.message}", e) // Log atualizado
                    Toast.makeText(this, "Erro ao salvar alterações: ${e.message}", Toast.LENGTH_LONG).show()
                }
        } else {
            Toast.makeText(this, "Erro: ID do aluno ausente para salvar.", Toast.LENGTH_SHORT).show()
        }
    }
}