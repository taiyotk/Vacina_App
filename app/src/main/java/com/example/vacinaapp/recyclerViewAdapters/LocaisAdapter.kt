package com.example.vacinaapp.recyclerViewAdapters

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.vacinaapp.R
import com.example.vacinaapp.dataClass.LocaisDataclass
import com.example.vacinaapp.fragments.LocalDetalhesFragment

class LocaisAdapter(private var listaLocais: ArrayList<LocaisDataclass>): RecyclerView.Adapter<LocaisViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocaisViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_item_model,parent,false)
        return LocaisViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return listaLocais.size
    }

    override fun onBindViewHolder(holder: LocaisViewHolder, position: Int) {

        val currentItem = listaLocais[position]
        holder.localNome.text = currentItem.posto_saude
        holder.endereco.text = currentItem.endereco

        holder.itemView.setOnClickListener {
            context = holder.itemView.context
            listOnClick(currentItem.id_local)
        }
    }

    fun filterlist(filterlist: ArrayList<LocaisDataclass>){
        listaLocais = filterlist
        notifyDataSetChanged()
    }

    fun listOnClick(itemID: Int){
        val fragmentoDetalhes = LocalDetalhesFragment()
        val bundle = Bundle()
        bundle.putInt("key", itemID)
        fragmentoDetalhes.arguments = bundle

        val fm = (context as FragmentActivity).supportFragmentManager
        val ft = fm.beginTransaction().replace(R.id.fragment_container, fragmentoDetalhes)
        ft.commit()

    }

}

class LocaisViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val localNome: TextView = itemView.findViewById(R.id.local_nome_item)
    val endereco: TextView = itemView.findViewById(R.id.endereco_local)

}