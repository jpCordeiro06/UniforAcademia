package com.example.uniforacademia

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class Menu : AppCompatActivity() {
    private lateinit var btn1: LinearLayout
    private lateinit var btn2: LinearLayout
    private lateinit var btn3: LinearLayout
    private lateinit var btn4: LinearLayout
    private lateinit var btn5: ImageButton
    private lateinit var btn6: ImageButton
    private lateinit var btn7: ImageButton
    private lateinit var btn8: LinearLayout
    private lateinit var btn9: LinearLayout
    private lateinit var btn10: LinearLayout
    private lateinit var btn11: LinearLayout
    private lateinit var btn12: LinearLayout
    private lateinit var btn13: LinearLayout
    private lateinit var btn14: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu)

        btn1 = findViewById(R.id.MenuConfig)
        btn2 = findViewById(R.id.MenuAcessibilidade)
        btn3 = findViewById(R.id.MenuPlanoTreino)
        btn4 = findViewById(R.id.MenuFrequencia)
        btn5 = findViewById(R.id.imgButtonMenu)
        btn6 = findViewById(R.id.imgButtonProf)
        btn7 = findViewById(R.id.imgButtonInicio)
        btn8 = findViewById(R.id.MenuQuantPessoas)
        btn9 = findViewById(R.id.MenuTutorIA)
        btn10 = findViewById(R.id.MenuAgendarAvaliacao)
        btn11 = findViewById(R.id.MenuAulas)
        btn12 = findViewById(R.id.MenuAjuda)
        btn13 = findViewById(R.id.MenuAdmin)
        btn14 = findViewById(R.id.MenuAssinaturas)

        btn1.setOnClickListener {
            val intent = Intent(this, Configuracoes::class.java)
            startActivity(intent)
        }

        btn2.setOnClickListener {
            val intent = Intent(this, Acessibilidade::class.java)
            startActivity(intent)
        }

        btn3.setOnClickListener {
            val intent = Intent(this, Treinos::class.java)
            startActivity(intent)
        }

        btn4.setOnClickListener {
            val intent = Intent(this, CalendarActivity::class.java)
            startActivity(intent)
        }

        btn5.setOnClickListener {
            val intent = Intent(this, Inicio::class.java)
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
            val intent = Intent(this, QuantPessoas::class.java)
            startActivity(intent)
        }

        btn9.setOnClickListener {
            val intent = Intent(this, TutorIA::class.java) // CORRECTED LINE
            startActivity(intent)
        }

        btn10.setOnClickListener {
            val intent = Intent(this, Agendar_Avaliacao::class.java)
            startActivity(intent)
        }

        btn11.setOnClickListener {
            val intent = Intent(this, Aulas::class.java)
            startActivity(intent)
        }

        btn12.setOnClickListener {
            val intent = Intent(this, AjudaActivity::class.java)
            startActivity(intent)
        }

        btn13.setOnClickListener {
            val intent = Intent(this, AdminActivity::class.java)
            startActivity(intent)
        }
        btn14.setOnClickListener {
            val intent = Intent(this, Assinatura::class.java)
            startActivity(intent)
        }
    }
}