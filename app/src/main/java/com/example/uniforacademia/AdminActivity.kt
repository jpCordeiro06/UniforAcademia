package com.example.uniforacademia

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class AdminActivity : AppCompatActivity() {

    private lateinit var btnConsultarAluno: Button
    private lateinit var btnConsultarFuncionario: Button
    private lateinit var btnAulas: Button
    private lateinit var btnRelatorio: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        btnConsultarAluno = findViewById(R.id.btnConsultarAluno)
        btnConsultarFuncionario = findViewById(R.id.btnConsultarFuncionario)
        btnAulas = findViewById(R.id.btnAulas)
        btnRelatorio = findViewById(R.id.btnRelatorio)

        configurarBotoes()
    }

    private fun configurarBotoes() {
        // Botão de Voltar/Menu Principal (tvTitulo)
        findViewById<TextView>(R.id.tvTitulo).setOnClickListener {
            startActivity(Intent(this, Menu::class.java))
            finish()
        }

        btnConsultarAluno.setOnClickListener {
            startActivity(Intent(this, ConsultarAlunoActivity::class.java))
        }

        btnConsultarFuncionario.setOnClickListener {
            startActivity(Intent(this, ConsultarFuncionarioActivity::class.java))
        }

        btnAulas.setOnClickListener {
            startActivity(Intent(this, Admin_Aulas_Activity::class.java))
        }

        btnRelatorio.setOnClickListener {
            startActivity(Intent(this, Relatorio::class.java))
        }

        findViewById<ImageButton>(R.id.imgButtonInicio).setOnClickListener {
            val intent = Intent(this, Inicio_Aluno::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }

        findViewById<ImageButton>(R.id.imgButtonProf).setOnClickListener {
            startActivity(Intent(this, ChamaProf::class.java))
        }

        findViewById<ImageButton>(R.id.imgButtonMenu).setOnClickListener {
            startActivity(Intent(this, Menu::class.java))
        }
    }
}