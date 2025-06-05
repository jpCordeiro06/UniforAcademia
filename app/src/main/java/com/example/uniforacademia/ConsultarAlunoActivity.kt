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

class ConsultarAlunoActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var alunoAdapter: AlunoAdapter
    private lateinit var etBuscarAluno: EditText
    private lateinit var tvVoltar: TextView

    private val listaAlunosCompleta = mutableListOf<Aluno>()
    private val listaAlunosExibida = mutableListOf<Aluno>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consultar_aluno)

        recyclerView = findViewById(R.id.recyclerView)
        etBuscarAluno = findViewById(R.id.etBuscarAluno)
        tvVoltar = findViewById(R.id.tvVoltar)

        recyclerView.layoutManager = LinearLayoutManager(this)

        alunoAdapter = AlunoAdapter(listaAlunosExibida) { aluno ->
            abrirPerfilAluno(aluno)
        }
        recyclerView.adapter = alunoAdapter

        carregarAlunos()

        tvVoltar.setOnClickListener {
            finish()
        }

        etBuscarAluno.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filtrarAlunos(s.toString())
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
            Toast.makeText(this, "Chamar Professor clicado", Toast.LENGTH_SHORT).show()
        }

        imgButtonMenu.setOnClickListener {
            Toast.makeText(this, "Menu clicado", Toast.LENGTH_SHORT).show()
        }
    }

    private fun carregarAlunos() {
        val db = FirebaseFirestore.getInstance()

        db.collection("usuarios")
            .whereEqualTo("tipoUsuario", "aluno")
            .get()
            .addOnSuccessListener { result ->
                listaAlunosCompleta.clear()
                for (document in result) {
                    val aluno = document.toObject(Aluno::class.java)


                    if (aluno != null) {
                        aluno.id = document.id
                        listaAlunosCompleta.add(aluno)
                    } else {
                        Log.w("ConsultarAlunoActivity", "Documento nulo ou com erro de conversão para Aluno: ${document.id}")
                    }
                }
                listaAlunosExibida.clear()
                listaAlunosExibida.addAll(listaAlunosCompleta)
                alunoAdapter.notifyDataSetChanged()
                Log.d("ConsultarAlunoActivity", "Alunos carregados: ${listaAlunosCompleta.size}")
            }
            .addOnFailureListener { exception ->
                Log.w("ConsultarAlunoActivity", "Erro ao buscar alunos", exception)
                Toast.makeText(this, "Erro ao carregar alunos: ${exception.message}", Toast.LENGTH_LONG).show()
            }
    }

    private fun filtrarAlunos(texto: String) {
        listaAlunosExibida.clear()
        if (texto.isBlank()) {
            listaAlunosExibida.addAll(listaAlunosCompleta)
        } else {
            val textoNormalizado = texto.lowercase(Locale.getDefault())
            val alunosFiltrados = listaAlunosCompleta.filter { aluno ->
                (aluno.nome?.lowercase(Locale.getDefault())?.contains(textoNormalizado) == true) ||
                        (aluno.matricula?.lowercase(Locale.getDefault())?.contains(textoNormalizado) == true)
            }
            listaAlunosExibida.addAll(alunosFiltrados)
        }
        alunoAdapter.notifyDataSetChanged()
    }

    private fun abrirPerfilAluno(aluno: Aluno) {
        if (aluno.id != null) {
            val intent = Intent(this, PerfilAlunoActivity::class.java)
            intent.putExtra("alunoId", aluno.id)
            startActivity(intent)
        } else {
            Toast.makeText(this, "Não foi possível abrir o perfil: ID do aluno ausente.", Toast.LENGTH_SHORT).show()
            Log.e("ConsultarAlunoActivity", "Tentativa de abrir perfil com aluno.id == null para ${aluno.nome}")
        }
    }
}