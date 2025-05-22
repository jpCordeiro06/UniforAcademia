package com.example.uniforacademia

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class Avaliacao : AppCompatActivity() {
    private lateinit var btn1: TextView
    private lateinit var btn5: ImageButton
    private lateinit var btn6: ImageButton
    private lateinit var btn7: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_avaliacao)
        btn1 = findViewById(R.id.tvTituloConfirmarAvaliacao)
        btn5 = findViewById(R.id.imgButtonMenu)
        btn6 = findViewById(R.id.imgButtonProf)
        btn7 = findViewById(R.id.imgButtonInicio)

        btn1.setOnClickListener {
            val intent = Intent(this, Agendar_Avaliacao::class.java)
            startActivity(intent)
        }

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