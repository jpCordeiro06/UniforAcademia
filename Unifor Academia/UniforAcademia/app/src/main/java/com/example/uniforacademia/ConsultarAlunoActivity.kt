package com.example.uniforacademia

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.uniforacademia.R

class ConsultarAlunoActivity : AppCompatActivity() {

    private lateinit var btn5: ImageButton
    private lateinit var btn6: ImageButton
    private lateinit var btn7: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consultar_aluno)

        val aluno1 = findViewById<Button>(R.id.btnAluno1)
        val aluno2 = findViewById<Button>(R.id.btnAluno2)
        val aluno3 = findViewById<Button>(R.id.btnAluno3)
        val aluno4 = findViewById<Button>(R.id.btnAluno4)
        val voltar = findViewById<TextView>(R.id.tvVoltar)

        val abrirPerfilAluno = {
            val intent = Intent(this, PerfilAlunoActivity::class.java)
            startActivity(intent)
            finish()
        }

        voltar.setOnClickListener {
            finish()
        }

        aluno1.setOnClickListener {
            abrirPerfilAluno()
        }
        aluno2.setOnClickListener {
            abrirPerfilAluno()
        }
        aluno3.setOnClickListener {
            abrirPerfilAluno()
        }
        aluno4.setOnClickListener {
            abrirPerfilAluno()
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
