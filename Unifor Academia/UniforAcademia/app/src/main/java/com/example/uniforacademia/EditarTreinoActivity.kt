package com.example.uniforacademia

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class EditarTreinoActivity : AppCompatActivity() {

    private lateinit var btn5: ImageButton
    private lateinit var btn6: ImageButton
    private lateinit var btn7: ImageButton
    private lateinit var btn8: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_treino)

        btn5 = findViewById(R.id.imgButtonMenu)
        btn6 = findViewById(R.id.imgButtonProf)
        btn7 = findViewById(R.id.imgButtonInicio)
        btn8 = findViewById(R.id.tvTituloEditarTreino)

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
            finish()
        }

    }
}
