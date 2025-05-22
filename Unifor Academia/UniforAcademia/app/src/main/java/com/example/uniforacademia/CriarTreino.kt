package com.example.uniforacademia
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.FirebaseFirestore

class CriarTreino : AppCompatActivity() {

    private lateinit var btn5: ImageButton
    private lateinit var btn6: ImageButton
    private lateinit var btn7: ImageButton
    private lateinit var btn8: TextView
    private lateinit var etNomeTreino: EditText
    private lateinit var etDescricao: EditText
    private lateinit var checkBoxes: List<CheckBox>
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_criar_treino)
        btn5 = findViewById(R.id.imgButtonMenu)
        btn6 = findViewById(R.id.imgButtonProf)
        btn7 = findViewById(R.id.imgButtonInicio)
        btn8 = findViewById(R.id.tvTituloCriarTreino)

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
            val intent = Intent(this, Treinos::class.java)
            finish()
        }


        etNomeTreino = findViewById(R.id.etNomeTreino)
        etDescricao = findViewById(R.id.etDescricao)

        checkBoxes = listOf(
            findViewById(R.id.cbSegunda),
            findViewById(R.id.cbTerca),
            findViewById(R.id.cbQuarta),
            findViewById(R.id.cbQuinta),
            findViewById(R.id.cbSexta),
            findViewById(R.id.cbSabado)
        )

        findViewById<Button>(R.id.btnSalvar).setOnClickListener { salvarTreino() }
    }

    private fun salvarTreino() {
        val nomeTreino = etNomeTreino.text.toString()
        val descricaoTreino = etDescricao.text.toString()
        val diasSelecionados = obterDiasSelecionados()

        if (nomeTreino.isBlank() || descricaoTreino.isBlank()) {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
            return
        }

        val treinoMap = hashMapOf(
            "nome" to nomeTreino,
            "descricao" to descricaoTreino,
            "diasSelecionados" to diasSelecionados
        )

        db.collection("treinos").add(treinoMap)
            .addOnSuccessListener {
                Toast.makeText(this, "Treino salvo no Firebase!", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Erro ao salvar: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun obterDiasSelecionados(): List<String> {
        return checkBoxes.filter { it.isChecked }.map { it.text.toString() }
    }
}