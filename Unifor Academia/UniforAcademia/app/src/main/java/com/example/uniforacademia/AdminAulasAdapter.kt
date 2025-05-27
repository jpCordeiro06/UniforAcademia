package com.example.uniforacademia

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdminAulasAdapter(
    private var aulas: MutableList<AulaAcademia>,
    private val onEditClick: (AulaAcademia) -> Unit,
    private val onRemoveClick: (AulaAcademia) -> Unit
) : RecyclerView.Adapter<AdminAulasAdapter.AulaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AulaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_aula_admin, parent, false)
        return AulaViewHolder(view)
    }

    override fun onBindViewHolder(holder: AulaViewHolder, position: Int) {
        val aula = aulas[position]
        holder.bind(aula)
    }

    override fun getItemCount(): Int = aulas.size

    fun submitList(newAulas: List<AulaAcademia>) {
        aulas.clear()
        aulas.addAll(newAulas)
        notifyDataSetChanged() // Para uma lista pequena. Para listas grandes, use DiffUtil.
    }

    fun getAulaAt(position: Int): AulaAcademia {
        return aulas[position]
    }

    inner class AulaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nomeAula: TextView = itemView.findViewById(R.id.tvItemNomeAula)
        private val horarioAula: TextView = itemView.findViewById(R.id.tvItemHorarioAula)
        private val diasAula: TextView = itemView.findViewById(R.id.tvItemDiasAula)
        private val instrutorAula: TextView = itemView.findViewById(R.id.tvItemInstrutorAula)
        private val vagasAula: TextView = itemView.findViewById(R.id.tvItemVagasAula)
        private val btnEditar: ImageButton = itemView.findViewById(R.id.btnItemEditarAula)
        private val btnRemover: ImageButton = itemView.findViewById(R.id.btnItemRemoverAula)

        fun bind(aula: AulaAcademia) {
            nomeAula.text = aula.nomeAula
            horarioAula.text = aula.horario
            diasAula.text = aula.diasSemana
            instrutorAula.text = "Instrutor: ${aula.instrutorNome}"
            vagasAula.text = "Vagas: ${aula.vagasOcupadas}/${aula.vagasMaximas}"

            btnEditar.setOnClickListener { onEditClick(aula) }
            btnRemover.setOnClickListener { onRemoveClick(aula) }
        }
    }
}