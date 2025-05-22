package com.example.uniforacademia

import android.content.Intent
import android.widget.Button
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DesempenhoActivity : AppCompatActivity() {

    private lateinit var btn5: ImageButton
    private lateinit var btn6: ImageButton
    private lateinit var btn7: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_desempenho)


        val btnSelecionarPeriodo = findViewById<Button>(R.id.btnSelecionarPeriodo)
        val tvFrequencia = findViewById<TextView>(R.id.tvFrequencia)
        val tvTempoTreino = findViewById<TextView>(R.id.tvTempoTreino)
        val ivGraficoPeso = findViewById<ImageView>(R.id.ivGraficoPeso)
        val ivGraficoIMC = findViewById<ImageView>(R.id.ivGraficoIMC)
        val tvTituloDesempenho = findViewById<TextView>(R.id.tvTituloDesempenho)


        tvFrequencia.text = "Frequência: 85%"
        tvTempoTreino.text = "Tempo de treino: 10h00"


        ivGraficoPeso.setImageResource(R.drawable.ic_grafico_peso)
        ivGraficoIMC.setImageResource(R.drawable.ic_grafico_imc)


        tvTituloDesempenho.setOnClickListener {
            finish()
        }


        btnSelecionarPeriodo.setOnClickListener {
            Toast.makeText(this, "Abrir seletor de período (futuro)", Toast.LENGTH_SHORT).show()
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
