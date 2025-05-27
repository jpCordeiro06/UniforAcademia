package com.example.uniforacademia

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView

class AdminActivity : AppCompatActivity() {

    private lateinit var btnMenuNavegacao: ImageButton
    private lateinit var btnChamaProfNavegacao: ImageButton
    private lateinit var btnInicioNavegacao: ImageButton

    // Variável de classe para o botão de relatório
    private lateinit var btnRelatorioAcao: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        val btnConsultarAluno = findViewById<Button>(R.id.btnConsultarAluno)
        val btnConsultarFuncionario = findViewById<Button>(R.id.btnConsultarFuncionario)
        val btnAulas = findViewById<Button>(R.id.btnAulas)
        btnRelatorioAcao = findViewById(R.id.btnRelatorio)
        val btnVoltar = findViewById<TextView>(R.id.tvTitulo)

        btnRelatorioAcao.setOnClickListener {
            startActivity(Intent(this, Relatorio::class.java))
        }

        btnConsultarAluno.setOnClickListener {
            startActivity(Intent(this, ConsultarAlunoActivity::class.java))
        }

        btnConsultarFuncionario.setOnClickListener {
            startActivity(Intent(this, ConsultarFuncionarioActivity::class.java))
        }

        btnAulas.setOnClickListener {
            val intent = Intent(this, Admin_Aulas_Activity::class.java)
            startActivity(intent)
        }

        btnVoltar.setOnClickListener {
            startActivity(Intent(this, Menu::class.java))
        }

        btnMenuNavegacao = findViewById(R.id.imgButtonMenu)
        btnChamaProfNavegacao = findViewById(R.id.imgButtonProf)
        btnInicioNavegacao = findViewById(R.id.imgButtonInicio)

        btnMenuNavegacao.setOnClickListener {
            val intent = Intent(this, Menu::class.java)
            startActivity(intent)
        }
        btnChamaProfNavegacao.setOnClickListener {
            val intent = Intent(this, ChamaProf::class.java)
            startActivity(intent)
        }
        btnInicioNavegacao.setOnClickListener {
            val intent = Intent(this, Inicio_Aluno::class.java)
            startActivity(intent)
        }
    }
}
