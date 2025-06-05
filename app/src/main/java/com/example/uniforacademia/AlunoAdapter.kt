package com.example.uniforacademia

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AlunoAdapter(
    private val listaAlunos: MutableList<Aluno>,
    private val onClick: (Aluno) -> Unit
) : RecyclerView.Adapter<AlunoAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nome: TextView = itemView.findViewById(R.id.tvNomeAluno)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Infla o layout item_aluno.xml
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_aluno, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val aluno = listaAlunos[position]
        holder.nome.text = aluno.nome

        holder.itemView.setOnClickListener {
            onClick(aluno)
        }
    }

    override fun getItemCount(): Int {
        return listaAlunos.size
    }

    fun atualizarLista(novaLista: List<Aluno>) {
        listaAlunos.clear()
        listaAlunos.addAll(novaLista)
        notifyDataSetChanged()
    }
}