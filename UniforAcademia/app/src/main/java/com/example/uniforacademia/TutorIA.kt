package com.example.uniforacademia

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.launch

class TutorIA : AppCompatActivity() {

    private lateinit var btnMenu: ImageButton
    private lateinit var btnProf: ImageButton
    private lateinit var btnInicio: ImageButton
    private lateinit var tvTitulo: TextView

    private lateinit var etMensagem: EditText
    private lateinit var btnEnviar: ImageButton
    private lateinit var tvRespostas: TextView

    private val gemini by lazy {
        GenerativeModel(
            modelName = "gemini-2.0-flash-001",
            apiKey = "AIzaSyAnLSj1oohhL_VaWwgKClvds3Lz_gySHVg"
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutor_ia)

        initViews()
        setupNavigation()
    }

    private fun initViews() {
        // Componentes de navegação
        btnMenu = findViewById(R.id.imgButtonMenu)
        btnProf = findViewById(R.id.imgButtonProf)
        btnInicio = findViewById(R.id.imgButtonInicio)
        tvTitulo = findViewById(R.id.tvTituloTutorIA)

        // Componentes do chat
        etMensagem = findViewById(R.id.etMensagemTutor)
        btnEnviar = findViewById(R.id.btnEnviar)
        tvRespostas = findViewById(R.id.tvRespostas)
    }

    private fun setupNavigation() {
        // Navegação original
        btnMenu.setOnClickListener { startActivity(Intent(this, Menu::class.java)) }
        btnProf.setOnClickListener { startActivity(Intent(this, ChamaProf::class.java)) }
        btnInicio.setOnClickListener { startActivity(Intent(this, Inicio::class.java)) }
        tvTitulo.setOnClickListener { finish() }

        // Configurar botão de enviar
        btnEnviar.setOnClickListener { enviarPergunta() }
    }

    private fun enviarPergunta() {
        val pergunta = etMensagem.text.toString().trim()

        if (pergunta.isEmpty()) {
            Toast.makeText(this, "Digite sua dúvida", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            try {
                tvRespostas.text = "Processando..."
                val resposta = gemini.generateContent(
                    "Você é um personal trainer. Responda em português de forma técnica e direta, usando no máximo 300 caracteres: $pergunta"
                ).text
                tvRespostas.text = resposta
                etMensagem.setText("")
            } catch (e: Exception) {
                tvRespostas.text = "Erro: ${e.message}"
            }
        }
    }
}