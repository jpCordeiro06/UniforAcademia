package com.example.uniforacademia

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.uniforacademia.R

class PerfilFuncionarioActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil_funcionario)

        val tvTitulo: TextView = findViewById(R.id.tvTitulo)
        val ivFotoFuncionario: ImageView = findViewById(R.id.ivFotoFuncionario)
        val tvNomeFuncionario: TextView = findViewById(R.id.tvNomeFuncionario)
        val tvMatricula: TextView = findViewById(R.id.tvMatriculaFuncionario)
        val tvEspecialidade: TextView = findViewById(R.id.tvEspecialidadeFuncionario)
        val tvHorario: TextView = findViewById(R.id.tvHorarioFuncionario)
        val tvStatusPagamento: TextView = findViewById(R.id.tvStatusPagamento)
        val btnEditarPerfil: Button = findViewById(R.id.btnEditarPerfil)
        val btnBloquearAcesso: Button = findViewById(R.id.btnBloquearAcesso)

        tvTitulo.setOnClickListener {
            finish()
        }

        val nome = intent.getStringExtra("nome") ?: "Nome não informado"
        val matricula = intent.getStringExtra("matricula") ?: "Matrícula não informada"
        val especialidade = intent.getStringExtra("especialidade") ?: "Especialidade não informada"
        val horario = intent.getStringExtra("horario") ?: "Horário não informado"
        val pagamentoEmDia = intent.getBooleanExtra("pagamentoEmDia", true)

        tvNomeFuncionario.text = "Nome do Funcionário: $nome"
        tvMatricula.text = "Matrícula: $matricula"
        tvEspecialidade.text = "Especialidade: $especialidade"
        tvHorario.text = "Horário: $horario"
        tvStatusPagamento.text = if (pagamentoEmDia) {
            "Pagamento: Em dia"
        } else {
            "Pagamento: Pendente"
        }

        btnEditarPerfil.setOnClickListener {
            Toast.makeText(this, "Editar Perfil clicado", Toast.LENGTH_SHORT).show()
        }

        btnBloquearAcesso.setOnClickListener {
            Toast.makeText(this, "Acesso bloqueado", Toast.LENGTH_SHORT).show()
        }
    }
}
