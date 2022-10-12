package com.example.vacinaapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vacinaapp.dataClass.VacinasDataClass


class VacinasAdapter(val listaVacinas: ArrayList<VacinasDataClass>, val clickLambda: (Int)->Unit): RecyclerView.Adapter<VacinasViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacinasViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item_vacina,parent,false)
        return VacinasViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return listaVacinas.size
    }

    override fun onBindViewHolder(holder: VacinasViewHolder, position: Int) {

        val currentItem = listaVacinas[position]
        holder.doenca.text = currentItem.doenca
        holder.disponibilidade.text = currentItem.disponibilidade
        holder.ver.text = currentItem.publico


        holder.itemView.setOnClickListener {
            clickLambda(currentItem.id_posto)
        }
    }



}

class VacinasViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val doenca: TextView = itemView.findViewById(R.id.doenca_textview)
    val disponibilidade: TextView = itemView.findViewById(R.id.disp_textview)
    val ver: TextView = itemView.findViewById(R.id.ver_textview)

}