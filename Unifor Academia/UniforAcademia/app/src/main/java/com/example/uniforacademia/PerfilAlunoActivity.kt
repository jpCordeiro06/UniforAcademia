package com.example.uniforacademia

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PerfilAlunoActivity : AppCompatActivity() {

    private lateinit var btn5: ImageButton
    private lateinit var btn6: ImageButton
    private lateinit var btn7: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil_aluno)

        val tvNomeAluno = findViewById<TextView>(R.id.tvNomeAluno)
        val tvMatricula = findViewById<TextView>(R.id.tvMatricula)
        val tvCurso = findViewById<TextView>(R.id.tvCurso)
        val tvStatusAluno = findViewById<TextView>(R.id.tvStatusAluno)
        val tvTitulo = findViewById<TextView>(R.id.tvTitulo)

        val nome = "Nome não informado"
        val matricula = "Matrícula não informada"
        val curso = "Curso: Engenharia de Software"
        val status = "Status: Ativo"

        tvNomeAluno.text = "Nome do Aluno: $nome"
        tvMatricula.text = "Matrícula: $matricula"
        tvCurso.text = curso
        tvStatusAluno.text = status

        tvTitulo.setOnClickListener {
            val intent = Intent(this, ConsultarAlunoActivity::class.java)
            startActivity(intent)
            finish()
        }

        val btnEditarPerfil = findViewById<Button>(R.id.btnEditarPerfil)
        val btnBloquearAcesso = findViewById<Button>(R.id.btnBloquearAcesso)

        btnEditarPerfil.setOnClickListener {
            Toast.makeText(this, "Editar Perfil clicado", Toast.LENGTH_SHORT).show()
        }

        btnBloquearAcesso.setOnClickListener {
            Toast.makeText(this, "Bloquear Acesso clicado", Toast.LENGTH_SHORT).show()
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
            val intent = Intent(this, Inicio_Aluno::class.java)
            startActivity(intent)
        }
    }
}
