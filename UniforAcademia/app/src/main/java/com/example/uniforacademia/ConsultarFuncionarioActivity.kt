package com.example.uniforacademia

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.uniforacademia.R

class ConsultarFuncionarioActivity : AppCompatActivity() {

    private lateinit var btn5: ImageButton
    private lateinit var btn6: ImageButton
    private lateinit var btn7: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consultar_funcionario)

        val tvVoltarFuncionario = findViewById<TextView>(R.id.tvVoltarFuncionario)
        val etBuscarFuncionario = findViewById<EditText>(R.id.etBuscarFuncionario)

        val btnCarlos = findViewById<Button>(R.id.btnFuncionarioCarlos)
        val btnFernanda = findViewById<Button>(R.id.btnFuncionarioFernanda)
        val btnLucas = findViewById<Button>(R.id.btnFuncionarioLucas)
        val btnMaria = findViewById<Button>(R.id.btnFuncionarioMaria)

        tvVoltarFuncionario.setOnClickListener {
            finish()
        }

        btnCarlos.setOnClickListener {
            abrirPerfil("Carlos Mendes", "Professor de Musculação")
        }

        btnFernanda.setOnClickListener {
            abrirPerfil("Fernanda Costa", "Recepcionista")
        }

        btnLucas.setOnClickListener {
            abrirPerfil("Lucas Ribeiro", "Professor de Dança")
        }

        btnMaria.setOnClickListener {
            abrirPerfil("Maria Lopes", "Professora de Yoga")
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

    private fun abrirPerfil(nome: String, especialidade: String) {
        val intent = Intent(this, PerfilFuncionarioActivity::class.java)
        intent.putExtra("NOME_FUNCIONARIO", nome)
        intent.putExtra("ESPECIALIDADE_FUNCIONARIO", especialidade)
        startActivity(intent)
    }
}
