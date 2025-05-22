package com.example.uniforacademia

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.uniforacademia.R

class BuscaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_busca)

        // Lista de tópicos e descrições
        val helpTopics = listOf(
            Pair("Conceitos básicos sobre o app", "Aqui estão os conceitos fundamentais sobre como usar o app."),
            Pair("Como personalizar meu app", "Aprenda a personalizar as configurações do seu aplicativo."),
            Pair("Dúvidas frequentes", "Respostas às perguntas mais comuns dos usuários.")
        )

        // Configuração da ListView com adaptador customizado
        val listView = findViewById<ListView>(R.id.search_results_list)
        val adapter = HelpTopicsAdapter(helpTopics)
        listView.adapter = adapter

        // Configuração da barra de busca
        val searchBar = findViewById<EditText>(R.id.search_bar)
        searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString().lowercase()
                val filteredTopics = helpTopics.filter {
                    it.first.lowercase().contains(query) || it.second.lowercase().contains(query)
                }
                listView.adapter = HelpTopicsAdapter(filteredTopics)
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        // Configuração do botão de voltar
        val backButton = findViewById<ImageButton>(R.id.back_button)
        backButton.setOnClickListener {
            finish() // Volta para a tela de ajuda
        }
    }

    // Adaptador customizado para exibir títulos e descrições
    private class HelpTopicsAdapter(private val items: List<Pair<String, String>>) : BaseAdapter() {
        override fun getCount(): Int = items.size
        override fun getItem(position: Int): Pair<String, String> = items[position]
        override fun getItemId(position: Int): Long = position.toLong()

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view = convertView ?: LayoutInflater.from(parent?.context)
                .inflate(R.layout.list_item_help, parent, false)

            val titleText = view.findViewById<TextView>(R.id.item_title)
            val descriptionText = view.findViewById<TextView>(R.id.item_description)

            val item = getItem(position)
            titleText.text = item.first
            descriptionText.text = item.second

            // Alternar visibilidade da descrição ao clicar no título
            titleText.setOnClickListener {
                if (descriptionText.visibility == View.GONE) {
                    descriptionText.visibility = View.VISIBLE
                } else {
                    descriptionText.visibility = View.GONE
                }
            }

            return view
        }
    }
}

