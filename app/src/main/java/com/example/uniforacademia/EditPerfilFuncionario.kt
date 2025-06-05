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

class EditPerfilFuncionario : AppCompatActivity() {

    private lateinit var etNome: EditText
    private lateinit var etEmail: EditText
    private lateinit var etMatricula: EditText

    private lateinit var btnSalvar: Button
    private lateinit var tvTituloEditarFuncionario: TextView

    private var funcionarioId: String? = null

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_perfil_funcionario)

        etNome = findViewById(R.id.etNomeFuncionario)
        etEmail = findViewById(R.id.etEmailFuncionario)
        etMatricula = findViewById(R.id.etMatriculaFuncionario)
        btnSalvar = findViewById(R.id.btnSalvarFuncionario)
        tvTituloEditarFuncionario = findViewById(R.id.tvTituloEditarFuncionario)

        funcionarioId = intent.getStringExtra("funcionarioId")

        if (funcionarioId != null) {
            carregarDadosParaEdicao(funcionarioId!!)
        } else {
            Toast.makeText(this, "Erro: ID do funcionário não fornecido para edição.", Toast.LENGTH_SHORT).show()
            finish()
        }

        tvTituloEditarFuncionario.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        btnSalvar.setOnClickListener {
            salvarAlteracoes()
        }

        val imgButtonInicio = findViewById<ImageButton>(R.id.imgButtonInicio)
        val imgButtonProf = findViewById<ImageButton>(R.id.imgButtonProf)
        val imgButtonMenu = findViewById<ImageButton>(R.id.imgButtonMenu)

        imgButtonInicio.setOnClickListener {
            val intent = Intent(this, PerfilFuncionarioActivity::class.java)
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
        db.collection("funcionarios").document(id).get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val funcionario = document.toObject(Funcionario::class.java)
                    if (funcionario != null) {
                        etNome.setText(funcionario.nome)
                        etEmail.setText(funcionario.email)
                        etMatricula.setText(funcionario.matricula)
                    } else {
                        Toast.makeText(this, "Erro ao carregar dados do funcionário.", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                } else {
                    Toast.makeText(this, "Funcionário não encontrado.", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
            .addOnFailureListener { e ->
                Log.e("EditPerfilFuncionario", "Erro ao carregar dados: ${e.message}", e) // Log atualizado
                Toast.makeText(this, "Erro ao carregar dados para edição: ${e.message}", Toast.LENGTH_LONG).show()
                finish()
            }
    }

    private fun salvarAlteracoes() {
        val novoNome = etNome.text.toString().trim()
        val novoEmail = etEmail.text.toString().trim()
        val novaMatricula = etMatricula.text.toString().trim()

        if (novoNome.isEmpty() || novoEmail.isEmpty() || novaMatricula.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
            return
        }

        if (funcionarioId != null) {
            val dadosAtualizados = hashMapOf(
                "nome" to novoNome,
                "email" to novoEmail,
                "matricula" to novaMatricula
            )

            db.collection("funcionarios").document(funcionarioId!!)
                .update(dadosAtualizados as Map<String, Any>)
                .addOnSuccessListener {
                    Toast.makeText(this, "Perfil atualizado com sucesso!", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .addOnFailureListener { e ->
                    Log.e("EditPerfilFuncionario", "Erro ao atualizar dados: ${e.message}", e)
                    Toast.makeText(this, "Erro ao salvar alterações: ${e.message}", Toast.LENGTH_LONG).show()
                }
        } else {
            Toast.makeText(this, "Erro: ID do funcionário ausente para salvar.", Toast.LENGTH_SHORT).show()
        }
    }
}