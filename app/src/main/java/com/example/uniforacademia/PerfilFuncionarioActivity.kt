package com.example.uniforacademia

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class PerfilFuncionarioActivity : AppCompatActivity() {

    private lateinit var tvPerfilNome: TextView
    private lateinit var tvPerfilEmail: TextView
    private lateinit var tvPerfilMatricula: TextView

    private lateinit var tvTituloPerfilFuncionario: TextView
    private lateinit var btnEditarPerfilFuncionario: Button

    private var currentFuncionarioId: String? = null

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil_funcionario)

        tvPerfilNome = findViewById(R.id.tvNomeFuncionario)
        tvPerfilEmail = findViewById(R.id.tvEmailFuncionario)
        tvPerfilMatricula = findViewById(R.id.tvMatriculaFuncionario)

        tvTituloPerfilFuncionario = findViewById(R.id.tvTituloPerfilFuncionario)
        btnEditarPerfilFuncionario = findViewById(R.id.btnEditarPerfilFuncionario)

        currentFuncionarioId = intent.getStringExtra("funcionarioId")

        if (currentFuncionarioId != null) {
            carregarDadosDoFuncionario(currentFuncionarioId!!)
        } else {
            Toast.makeText(this, "ID do funcionário não fornecido.", Toast.LENGTH_SHORT).show()
            finish()
        }

        tvTituloPerfilFuncionario.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        btnEditarPerfilFuncionario.setOnClickListener {
            if (currentFuncionarioId != null) {
                val intent = Intent(this, EditPerfilFuncionario::class.java)
                intent.putExtra("funcionarioId", currentFuncionarioId)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Não foi possível editar: ID do funcionário ausente.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (currentFuncionarioId != null) {
            carregarDadosDoFuncionario(currentFuncionarioId!!)
        }
    }

    private fun carregarDadosDoFuncionario(funcionarioId: String) {
        db.collection("funcionarios").document(funcionarioId).get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val funcionario = document.toObject(Funcionario::class.java)
                    if (funcionario != null) {
                        tvPerfilNome.text = "Nome: ${funcionario.nome ?: "N/A"}"
                        tvPerfilEmail.text = "Email: ${funcionario.email ?: "N/A"}"
                        tvPerfilMatricula.text = "Matrícula: ${funcionario.matricula ?: "N/A"}"
                    } else {
                        Log.e("PerfilFuncionarioActivity", "Erro ao converter documento para Funcionario para o ID: $funcionarioId")
                        Toast.makeText(this, "Erro ao carregar dados do funcionário.", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                } else {
                    Log.d("PerfilFuncionarioActivity", "Documento do funcionário não encontrado para o ID: $funcionarioId")
                    Toast.makeText(this, "Funcionário não encontrado.", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
            .addOnFailureListener { exception ->
                Log.e("PerfilFuncionarioActivity", "Erro ao buscar documento do funcionário para o ID: $funcionarioId", exception)
                Toast.makeText(this, "Erro ao carregar perfil: ${exception.message}", Toast.LENGTH_LONG).show()
                finish()
            }
    }
}