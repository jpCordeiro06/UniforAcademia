package com.example.uniforacademia

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class Inicio_Aluno : AppCompatActivity() {

    private lateinit var imageNotificacao: ImageView
    private lateinit var btnVerTodos: Button
    private lateinit var btnTreinosPersonalizados: Button
    private lateinit var btnTreino1: Button
    private lateinit var btnMenu: ImageButton
    private lateinit var btnChamarProf: ImageButton
    private lateinit var btnInicio: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            setContentView(R.layout.activity_inicio_aluno)

            // findViewById
            imageNotificacao = findViewById(R.id.sino_notificacao)
            btnVerTodos = findViewById(R.id.btn_ver_todos)
            btnTreinosPersonalizados = findViewById(R.id.btn_treinos_personalizados)
            btnTreino1 = findViewById(R.id.btn_treino1)
            btnMenu = findViewById(R.id.imgButtonMenu)
            btnChamarProf = findViewById(R.id.imgButtonProf)
            btnInicio = findViewById(R.id.imgButtonInicio)

            // Listeners
            imageNotificacao.setOnClickListener {
                startActivity(Intent(this, Notificacoes::class.java))
            }

            btnVerTodos.setOnClickListener {
                startActivity(Intent(this, Aulas::class.java))
            }

            btnTreinosPersonalizados.setOnClickListener {
                startActivity(Intent(this, Treinos::class.java))
            }

            btnTreino1.setOnClickListener {
                startActivity(Intent(this, Treinos_Descricao::class.java))
            }

            btnMenu.setOnClickListener {
                startActivity(Intent(this, Menu::class.java))
            }

            btnChamarProf.setOnClickListener {
                startActivity(Intent(this, ChamaProf::class.java))
            }
        } catch (e: Exception) {
            Log.e("Inicio", "Error initializing activity", e)
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d("Inicio", "onResume: Atualizando UI da tela inicial")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Inicio", "onDestroy: Liberando recursos da tela inicial")
    }
}