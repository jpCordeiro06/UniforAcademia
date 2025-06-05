package com.example.uniforacademia

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FuncionarioAdapter(
    private val listaFuncionarios: MutableList<Funcionario>,
    private val onClick: (Funcionario) -> Unit
) : RecyclerView.Adapter<FuncionarioAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nome: TextView = itemView.findViewById(R.id.tvNomeFuncionario)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_funcionario, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val funcionario = listaFuncionarios[position]
        holder.nome.text = funcionario.nome

        holder.itemView.setOnClickListener {
            onClick(funcionario)
        }
    }

    override fun getItemCount(): Int {
        return listaFuncionarios.size
    }

    fun atualizarLista(novaLista: List<Funcionario>) {
        listaFuncionarios.clear()
        listaFuncionarios.addAll(novaLista)
        notifyDataSetChanged()
    }
}