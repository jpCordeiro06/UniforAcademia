package com.example.uniforacademia

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView


class AdminActivity : AppCompatActivity() {

    private lateinit var btnRelatorio: Button
    private lateinit var btn5: ImageButton
    private lateinit var btn6: ImageButton
    private lateinit var btn7: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        val btnConsultarAluno = findViewById<Button>(R.id.btnConsultarAluno)
        val btnConsultarFuncionario = findViewById<Button>(R.id.btnConsultarFuncionario)
        val btnDesempenho = findViewById<Button>(R.id.btnDesempenho)
        val btnLotacao = findViewById<Button>(R.id.btnLotacao)
        val btnRelatorio = findViewById<Button>(R.id.btnRelatorio)
        val btnVoltar = findViewById<TextView>(R.id.tvTitulo)

        btnRelatorio.setOnClickListener {
            startActivity(Intent(this, Relatorio::class.java))
        }

        btnConsultarAluno.setOnClickListener {
            startActivity(Intent(this, ConsultarAlunoActivity::class.java))
        }

        btnConsultarFuncionario.setOnClickListener {
            startActivity(Intent(this, ConsultarFuncionarioActivity::class.java))
        }

        btnDesempenho.setOnClickListener {
            startActivity(Intent(this, DesempenhoActivity::class.java))
        }

        btnLotacao.setOnClickListener {
            startActivity(Intent(this, LotacaoActivity::class.java))
        }

        btnVoltar.setOnClickListener {
            startActivity(Intent(this, Menu::class.java))
        }

        btn5 = findViewById(R.id.imgButtonMenu)
        btn6 = findViewById(R.id.imgButtonProf)
        btn7 = findViewById(R.id.imgButtonInicio)

        btn5.setOnClickListener {
            val intent = Intent(this, Menu::class.java)
            startActivity(intent)
        }
        btn6.setOnClickListener {
            val intent = Intent(this, ChamaProf::class.java)
            startActivity(intent)
        }
        btn7.setOnClickListener {
            val intent = Intent(this, Inicio::class.java)
            startActivity(intent)

        }
    }
}
