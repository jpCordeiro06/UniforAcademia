package com.example.uniforacademia

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
import java.util.Date

class Admin_Aulas_Activity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var adapter: AdminAulasAdapter
    private val aulasList = mutableListOf<AulaAcademia>()

    private lateinit var rvAulasAdmin: RecyclerView
    private lateinit var llFormularioAula: LinearLayout
    private lateinit var etNomeAula: EditText
    private lateinit var etInstrutorAula: EditText
    private lateinit var etDiasSemanaAula: EditText
    private lateinit var etHorarioAula: EditText
    private lateinit var etVagasMaximasAula: EditText
    private lateinit var etDescricaoAula: EditText
    private lateinit var btnConfirmarAula: Button
    private lateinit var btnCancelarAula: Button
    private lateinit var btnAddAula: FloatingActionButton
    private lateinit var tvTituloAdminAulas: TextView

    // Bottom Navigation
    private lateinit var bottomNavView: BottomNavigationView
    private lateinit var imgButtonMenu: ImageButton
    private lateinit var imgButtonProf: ImageButton
    private lateinit var imgButtonInicio: ImageButton
    // (Assume que os LinearLayouts dentro da BottomNav não precisam de IDs para listeners diretos,
    // os ImageButtons são os que têm os listeners)


    private var aulaEmEdicao: AulaAcademia? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_aulas)

        db = FirebaseFirestore.getInstance()

        // Inicialização das Views do formulário e RecyclerView
        rvAulasAdmin = findViewById(R.id.rvAulasAdmin)
        llFormularioAula = findViewById(R.id.llFormularioAula)
        etNomeAula = findViewById(R.id.etNomeAula)
        etInstrutorAula = findViewById(R.id.etInstrutorAula)
        etDiasSemanaAula = findViewById(R.id.etDiasSemanaAula)
        etHorarioAula = findViewById(R.id.etHorarioAula)
        etVagasMaximasAula = findViewById(R.id.etVagasMaximasAula)
        etDescricaoAula = findViewById(R.id.etDescricaoAula)
        btnConfirmarAula = findViewById(R.id.btnConfirmarAula)
        btnCancelarAula = findViewById(R.id.btnCancelarAula)
        btnAddAula = findViewById(R.id.btnAddAula)
        tvTituloAdminAulas = findViewById(R.id.tvTituloAdminAulas)

        // Configurar Bottom Navigation
        bottomNavView = findViewById(R.id.bottomNavigationViewAdminAulas)
        // Encontrar os ImageButtons dentro dos LinearLayouts da BottomNavigationView
        // Esta é uma forma, pode ser melhorado se você der IDs aos LinearLayouts clicáveis
        val inicioLayout = bottomNavView.findViewById<View>(R.id.imgButtonInicio).parent as LinearLayout
        val profLayout = bottomNavView.findViewById<View>(R.id.imgButtonProf).parent as LinearLayout
        val menuLayout = bottomNavView.findViewById<View>(R.id.imgButtonMenu).parent as LinearLayout

        imgButtonInicio = inicioLayout.findViewById(R.id.imgButtonInicio)
        imgButtonProf = profLayout.findViewById(R.id.imgButtonProf)
        imgButtonMenu = menuLayout.findViewById(R.id.imgButtonMenu)


        setupRecyclerView()
        carregarAulas()

        tvTituloAdminAulas.setOnClickListener {
            // Se esta Activity foi chamada pela AdminActivity, finish() retorna para ela.
            // Se não, defina um comportamento de "voltar" mais específico se necessário.
            finish()
        }

        btnAddAula.setOnClickListener {
            aulaEmEdicao = null // Garante que é uma nova aula
            etNomeAula.text.clear()
            etInstrutorAula.text.clear()
            etDiasSemanaAula.text.clear()
            etHorarioAula.text.clear()
            etVagasMaximasAula.text.clear()
            etDescricaoAula.text.clear()
            llFormularioAula.visibility = View.VISIBLE
            btnAddAula.visibility = View.GONE
        }

        btnCancelarAula.setOnClickListener {
            llFormularioAula.visibility = View.GONE
            btnAddAula.visibility = View.VISIBLE
            aulaEmEdicao = null
        }

        btnConfirmarAula.setOnClickListener {
            salvarAula()
        }

        // Listeners da Bottom Navigation (mantendo a sua lógica original)
        imgButtonMenu.setOnClickListener {
            val intent = Intent(this, Menu::class.java)
            // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP) // Considerar flags se necessário
            startActivity(intent)
        }
        imgButtonProf.setOnClickListener {
            val intent = Intent(this, ChamaProf::class.java)
            // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
        }
        imgButtonInicio.setOnClickListener {
            // A navegação para Inicio_Aluno a partir de uma tela de Admin pode ser confusa.
            // Considere navegar para uma tela inicial de Admin ou a AdminActivity principal.
            val intent = Intent(this, Inicio_Aluno::class.java)
            // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        adapter = AdminAulasAdapter(aulasList,
            onEditClick = { aula ->
                aulaEmEdicao = aula
                etNomeAula.setText(aula.nomeAula)
                etInstrutorAula.setText(aula.instrutorNome)
                etDiasSemanaAula.setText(aula.diasSemana)
                etHorarioAula.setText(aula.horario)
                etVagasMaximasAula.setText(aula.vagasMaximas.toString())
                etDescricaoAula.setText(aula.descricao)
                llFormularioAula.visibility = View.VISIBLE
                btnAddAula.visibility = View.GONE
            },
            onRemoveClick = { aula ->
                confirmarRemocaoAula(aula)
            }
        )
        rvAulasAdmin.layoutManager = LinearLayoutManager(this)
        rvAulasAdmin.adapter = adapter
    }

    private fun carregarAulas() {
        db.collection("aulasAcademia")
            .orderBy("dataCriacao", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    Log.w("AdminAulasActivity", "Erro ao carregar aulas.", e)
                    Toast.makeText(this, "Erro ao carregar aulas: ${e.message}", Toast.LENGTH_SHORT).show()
                    return@addSnapshotListener
                }

                val novasAulas = mutableListOf<AulaAcademia>()
                for (doc in snapshots!!) {
                    val aula = doc.toObject(AulaAcademia::class.java)
                    aula.id = doc.id // Atribui o ID do documento
                    novasAulas.add(aula)
                }
                adapter.submitList(novasAulas)
            }
    }

    private fun salvarAula() {
        val nome = etNomeAula.text.toString().trim()
        val instrutor = etInstrutorAula.text.toString().trim()
        val dias = etDiasSemanaAula.text.toString().trim()
        val horario = etHorarioAula.text.toString().trim()
        val vagasMaxStr = etVagasMaximasAula.text.toString().trim()
        val descricao = etDescricaoAula.text.toString().trim()

        if (nome.isEmpty() || instrutor.isEmpty() || dias.isEmpty() || horario.isEmpty() || vagasMaxStr.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos obrigatórios.", Toast.LENGTH_SHORT).show()
            return
        }

        val vagasMax = vagasMaxStr.toIntOrNull()
        if (vagasMax == null || vagasMax <= 0) {
            Toast.makeText(this, "Número de vagas inválido.", Toast.LENGTH_SHORT).show()
            return
        }

        val aulaData = AulaAcademia(
            nomeAula = nome,
            instrutorNome = instrutor,
            diasSemana = dias,
            horario = horario,
            vagasMaximas = vagasMax,
            descricao = descricao,
            status = aulaEmEdicao?.status ?: "ativa", // Mantém status se editando, senão "ativa"
            vagasOcupadas = aulaEmEdicao?.vagasOcupadas ?: 0, // Mantém se editando, senão 0
            // dataCriacao é definido pelo servidor na primeira vez
            dataUltimaModificacao = Date() // Atualiza a data de modificação
        )

        val operacao: String
        val task = if (aulaEmEdicao == null) { // Nova aula
            operacao = "criada"
            aulaData.dataCriacao = Date() // Timestamp local para ordenação antes do servidor confirmar
            db.collection("aulasAcademia").add(aulaData)
        } else { // Edição de aula existente
            operacao = "atualizada"
            aulaData.id = aulaEmEdicao!!.id
            aulaData.dataCriacao = aulaEmEdicao!!.dataCriacao // Preserva data de criação original
            db.collection("aulasAcademia").document(aulaEmEdicao!!.id).set(aulaData, SetOptions.merge())
        }

        task.addOnSuccessListener {
            Toast.makeText(this, "Aula $operacao com sucesso!", Toast.LENGTH_SHORT).show()
            llFormularioAula.visibility = View.GONE
            btnAddAula.visibility = View.VISIBLE
            aulaEmEdicao = null // Limpa estado de edição
        }.addOnFailureListener { e ->
            Toast.makeText(this, "Erro ao salvar aula: ${e.message}", Toast.LENGTH_LONG).show()
            Log.e("AdminAulasActivity", "Erro ao salvar aula", e)
        }
    }

    private fun confirmarRemocaoAula(aula: AulaAcademia) {
        AlertDialog.Builder(this)
            .setTitle("Confirmar Remoção")
            .setMessage("Tem certeza que deseja remover a aula '${aula.nomeAula}'?")
            .setPositiveButton("Remover") { _, _ ->
                removerAula(aula)
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun removerAula(aula: AulaAcademia) {
        if (aula.id.isEmpty()) {
            Toast.makeText(this, "ID da aula inválido.", Toast.LENGTH_SHORT).show()
            return
        }
        db.collection("aulasAcademia").document(aula.id)
            .delete()
            .addOnSuccessListener {
                Toast.makeText(this, "Aula '${aula.nomeAula}' removida.", Toast.LENGTH_SHORT).show()
                // A lista será atualizada automaticamente pelo SnapshotListener
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Erro ao remover aula: ${e.message}", Toast.LENGTH_LONG).show()
                Log.e("AdminAulasActivity", "Erro ao remover aula ${aula.id}", e)
            }
    }
}