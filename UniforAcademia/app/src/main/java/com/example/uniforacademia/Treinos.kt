package com.example.uniforacademia

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Treinos : AppCompatActivity() {


    private lateinit var btn1: TextView
    private lateinit var btn2: TextView
    private lateinit var btn3: TextView
    private lateinit var btn4: TextView
    private lateinit var btn5: ImageButton
    private lateinit var btn6: ImageButton
    private lateinit var btn7: ImageButton
    private lateinit var btn8: ImageView
    private lateinit var btn9: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_treinos_jp)


        btn1 = findViewById(R.id.tvPernas)
        btn2 = findViewById(R.id.tvBracos)
        btn3 = findViewById(R.id.tvCardio)
        btn4 = findViewById(R.id.tvTreinosTitle)
        btn5 = findViewById(R.id.imgButtonMenu)
        btn6 = findViewById(R.id.imgButtonProf)
        btn7 = findViewById(R.id.imgButtonInicio)
        btn8 = findViewById(R.id.iconeEditar2)
        btn9 = findViewById(R.id.imgAdd2)

        btn1.setOnClickListener {
            val intent = Intent(this, Treinos_Descricao::class.java)
            startActivity(intent)
        }
        btn2.setOnClickListener {
            val intent = Intent(this, Treinos_Descricao::class.java)
            startActivity(intent)
        }
        btn3.setOnClickListener {
            val intent = Intent(this, Treinos_Descricao::class.java)
            startActivity(intent)
        }
        btn4.setOnClickListener {
            val intent = Intent(this, Inicio::class.java)
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
        btn8.setOnClickListener {
            val intent = Intent(this, EditarTreinoActivity::class.java)
            startActivity(intent)
        }
        btn9.setOnClickListener {
            val intent = Intent(this, CriarTreino::class.java)
            startActivity(intent)
        }

    }
}