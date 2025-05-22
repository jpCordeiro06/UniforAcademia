package com.example.uniforacademia

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class Cadastro : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cadastro)

        val btnEntrar: Button = findViewById(R.id.btnEntrar)
        val btnCadastro: Button = findViewById(R.id.btnCadastro)

        btnCadastro.setOnClickListener {

            val intent = Intent(this, Tela_Cadastrar::class.java)
            startActivity(intent)
        }
        btnEntrar.setOnClickListener {

            val intent = Intent(this, Tela_Login::class.java)
            startActivity(intent)
        }
    }
}

