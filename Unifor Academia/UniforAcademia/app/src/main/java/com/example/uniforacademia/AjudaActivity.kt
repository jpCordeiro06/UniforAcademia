package com.example.uniforacademia

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class AjudaActivity : AppCompatActivity() {

    private lateinit var btn5: ImageButton
    private lateinit var btn6: ImageButton
    private lateinit var btn7: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ajuda)

        btn5 = findViewById(R.id.imgButtonMenu)
        btn6 = findViewById(R.id.imgButtonProf)
        btn7 = findViewById(R.id.imgButtonInicio)

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

        // Lista de tópicos e descrições
        val helpTopics = listOf(
            Pair("Conceitos básicos sobre o app", "Aqui estão os conceitos fundamentais sobre como usar o app."),
            Pair("Como personalizar meu app", "Aprenda a personalizar as configurações do seu aplicativo."),
            Pair("Dúvidas frequentes", "Respostas às perguntas mais comuns dos usuários.")
        )

        val listView = findViewById<ListView>(R.id.help_topics_list)
        listView.adapter = HelpTopicsAdapter(helpTopics)

        val voltar = findViewById<TextView>(R.id.txtAcessibilidade)

        voltar.setOnClickListener {
            val intent = Intent(this, Menu::class.java)
            startActivity(intent)
            finish()
        }


        // Botão de busca
        val searchButton = findViewById<ImageButton>(R.id.search_button)
        searchButton.setOnClickListener {
            val intent = Intent(this, BuscaActivity::class.java)
            startActivity(intent)
        }
    }

    // Adaptador customizado para exibir e alternar descrições
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

            // Alternar visibilidade ao clicar no título
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


