package com.example.vacinaapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vacinaapp.dataClass.LocaisDataclass

class LocaisAdapter(val listaLocais: ArrayList<LocaisDataclass>, val clickLambda: (Int)->Unit): RecyclerView.Adapter<LocaisViewHolder>() {

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
            clickLambda(currentItem.id_local)
        }
    }



}

class LocaisViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val localNome: TextView = itemView.findViewById(R.id.local_nome_item)
    val endereco: TextView = itemView.findViewById(R.id.endereco_local)

}