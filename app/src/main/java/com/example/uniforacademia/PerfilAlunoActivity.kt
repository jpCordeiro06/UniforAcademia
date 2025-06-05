package com.example.uniforacademia

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class PerfilAlunoActivity : AppCompatActivity() {

    private lateinit var tvPerfilNomeAluno: TextView
    private lateinit var tvPerfilEmailAluno: TextView
    private lateinit var tvPerfilMatriculaAluno: TextView
    private lateinit var tvPerfilCursoAluno: TextView
    private lateinit var tvVoltarAluno: TextView
    private lateinit var btnEditarPerfilAluno: Button

    private var currentAlunoId: String? = null

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil_aluno)

        // ATUALIZADO: Inicializar Views com os IDs corretos (adicionado "Aluno" no final)
        tvPerfilNomeAluno = findViewById(R.id.tvPerfilNomeAluno)
        tvPerfilEmailAluno = findViewById(R.id.tvPerfilEmailAluno)
        tvPerfilMatriculaAluno = findViewById(R.id.tvPerfilMatriculaAluno)
        tvPerfilCursoAluno = findViewById(R.id.tvPerfilCursoAluno)
        tvVoltarAluno = findViewById(R.id.tvTitulo) // O TextView de voltar é o tvTitulo no XML
        btnEditarPerfilAluno = findViewById(R.id.btnEditarPerfilAluno)

        currentAlunoId = intent.getStringExtra("alunoId")

        if (currentAlunoId != null) {
            carregarDadosDoAluno(currentAlunoId!!)
        } else {
            Toast.makeText(this, "ID do aluno não fornecido.", Toast.LENGTH_SHORT).show()
            finish()
        }

        tvVoltarAluno.setOnClickListener {
            // Este é o TextView "Perfil do Aluno" no topo, que atua como botão de voltar
            onBackPressedDispatcher.onBackPressed()
        }

        btnEditarPerfilAluno.setOnClickListener {
            if (currentAlunoId != null) {
                // ATUALIZADO: Nome da Activity de edição (EditPerfilAluno)
                val intent = Intent(this, EditPerfilAluno::class.java)
                intent.putExtra("alunoId", currentAlunoId)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Não foi possível editar: ID do aluno ausente.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // Recarregar dados sempre que a Activity for retomada (após editar, por exemplo)
        if (currentAlunoId != null) {
            carregarDadosDoAluno(currentAlunoId!!)
        }
    }

    private fun carregarDadosDoAluno(alunoId: String) {
        db.collection("usuarios").document(alunoId).get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val aluno = document.toObject(Aluno::class.java)
                    if (aluno != null) {
                        tvPerfilNomeAluno.text = "Nome: ${aluno.nome ?: "N/A"}"
                        tvPerfilEmailAluno.text = "Email: ${aluno.email ?: "N/A"}"
                        tvPerfilMatriculaAluno.text = "Matrícula: ${aluno.matricula ?: "N/A"}"
                        tvPerfilCursoAluno.text = "Curso: ${aluno.curso ?: "N/A"}"

                    } else {
                        Log.e("PerfilAlunoActivity", "Erro ao converter documento para Aluno para o ID: $alunoId")
                        Toast.makeText(this, "Erro ao carregar dados do aluno.", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                } else {
                    Log.d("PerfilAlunoActivity", "Documento do aluno não encontrado para o ID: $alunoId")
                    Toast.makeText(this, "Aluno não encontrado.", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
            .addOnFailureListener { exception ->
                Log.e("PerfilAlunoActivity", "Erro ao buscar documento do aluno para o ID: $alunoId", exception)
                Toast.makeText(this, "Erro ao carregar perfil: ${exception.message}", Toast.LENGTH_LONG).show()
                finish()
            }
    }
}