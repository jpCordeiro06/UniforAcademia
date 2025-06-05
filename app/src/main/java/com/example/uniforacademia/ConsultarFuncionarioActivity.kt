package com.example.uniforacademia

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Locale

class ConsultarFuncionarioActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var funcionarioAdapter: FuncionarioAdapter
    private lateinit var etBuscarFuncionario: EditText
    private lateinit var tvVoltarFuncionario: TextView


    private val listaFuncionariosCompleta = mutableListOf<Funcionario>()
    private val listaFuncionariosExibida = mutableListOf<Funcionario>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consultar_funcionario)

        recyclerView = findViewById(R.id.recyclerViewFuncionarios)
        etBuscarFuncionario = findViewById(R.id.etBuscarFuncionario)
        tvVoltarFuncionario = findViewById(R.id.tvVoltarFuncionario)

        recyclerView.layoutManager = LinearLayoutManager(this)

        funcionarioAdapter = FuncionarioAdapter(listaFuncionariosExibida) { funcionario ->
            abrirPerfilFuncionario(funcionario)
        }
        recyclerView.adapter = funcionarioAdapter

        carregarFuncionarios()

        tvVoltarFuncionario.setOnClickListener {
            finish()
        }

        etBuscarFuncionario.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filtrarFuncionarios(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

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
            // Removido o Toast para deixar o TODO mais claro
            // Toast.makeText(this, "Chamar Professor clicado", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, ChamaProf::class.java) // Exemplo: Se ChamaProf for uma Activity
            startActivity(intent)
        }

        imgButtonMenu.setOnClickListener {
            val intent = Intent(this, Menu::class.java)
            startActivity(intent)
        }
    }


    private fun carregarFuncionarios() {
        val db = FirebaseFirestore.getInstance()

        db.collection("funcionarios")
            .whereEqualTo("tipoUsuario", "funcionario")
            .get()
            .addOnSuccessListener { result ->
                listaFuncionariosCompleta.clear()
                for (document in result) {
                    val funcionario = document.toObject(Funcionario::class.java)
                    if (funcionario != null) {
                        funcionario.id = document.id
                        listaFuncionariosCompleta.add(funcionario)
                    } else {
                        Log.w("ConsultarFuncionarioActivity", "Documento nulo ou com erro de conversão para Funcionario: ${document.id}")
                    }
                }
                listaFuncionariosExibida.clear()
                listaFuncionariosExibida.addAll(listaFuncionariosCompleta)
                funcionarioAdapter.notifyDataSetChanged()
                Log.d("ConsultarFuncionarioActivity", "Funcionários carregados: ${listaFuncionariosCompleta.size}")
            }
            .addOnFailureListener { exception ->
                Log.w("ConsultarFuncionarioActivity", "Erro ao buscar funcionários", exception)
                Toast.makeText(this, "Erro ao carregar funcionários: ${exception.message}", Toast.LENGTH_LONG).show()
            }
    }

    private fun filtrarFuncionarios(texto: String) {
        listaFuncionariosExibida.clear()
        if (texto.isBlank()) {
            listaFuncionariosExibida.addAll(listaFuncionariosCompleta) // Se a busca estiver vazia, mostra todos
        } else {
            val textoNormalizado = texto.lowercase(Locale.getDefault())
            val funcionariosFiltrados = listaFuncionariosCompleta.filter { funcionario ->
                // Filtra por nome OU matrícula
                (funcionario.nome?.lowercase(Locale.getDefault())?.contains(textoNormalizado) == true) ||
                        (funcionario.matricula?.lowercase(Locale.getDefault())?.contains(textoNormalizado) == true)
            }
            listaFuncionariosExibida.addAll(funcionariosFiltrados)
        }
        funcionarioAdapter.notifyDataSetChanged()
    }

    private fun abrirPerfilFuncionario(funcionario: Funcionario) {
        if (funcionario.id != null) {
            val intent = Intent(this, PerfilFuncionarioActivity::class.java)
            intent.putExtra("funcionarioId", funcionario.id) // Passa o ID do documento
            startActivity(intent)
        } else {
            Toast.makeText(this, "Não foi possível abrir o perfil: ID do funcionário ausente.", Toast.LENGTH_SHORT).show()
            Log.e("ConsultarFuncionarioActivity", "Tentativa de abrir perfil com funcionario.id == null para ${funcionario.nome}")
        }
    }
}