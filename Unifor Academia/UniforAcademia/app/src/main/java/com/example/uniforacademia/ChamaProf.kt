package com.example.uniforacademia

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast

class ChamaProf : AppCompatActivity() {
    private lateinit var tvTitulo: TextView
    private lateinit var btnMenu: ImageButton
    private lateinit var btnProf: ImageButton
    private lateinit var btnInicio: ImageButton

    private lateinit var btnSim: Button
    private lateinit var btnNao: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_chama_prof)

        // cabeçalho
        tvTitulo  = findViewById(R.id.tvTituloChamarProf)
        btnMenu   = findViewById(R.id.imgButtonMenu)
        btnProf   = findViewById(R.id.imgButtonProf)
        btnInicio = findViewById(R.id.imgButtonInicio)

        tvTitulo.setOnClickListener {
            startActivity(Intent(this, Menu::class.java))
        }
        btnMenu.setOnClickListener {
            startActivity(Intent(this, Menu::class.java))
        }
        btnProf.setOnClickListener {
            // abre ele mesmo (talvez aqui queira outra ação)
            startActivity(Intent(this, ChamaProf::class.java))
        }
        btnInicio.setOnClickListener {
            startActivity(Intent(this, Inicio::class.java))
        }

        // novos listeners para Sim e Não
        btnSim = findViewById(R.id.btnSimChamar)
        btnNao = findViewById(R.id.btnNaoChamar)

        btnSim.setOnClickListener {
            Toast.makeText(this, "Professor chamado com sucesso!", Toast.LENGTH_SHORT).show()
        }

        btnNao.setOnClickListener {
            Toast.makeText(this, "Chamada ao professor foi cancelada.", Toast.LENGTH_SHORT).show()
        }
    }
}
