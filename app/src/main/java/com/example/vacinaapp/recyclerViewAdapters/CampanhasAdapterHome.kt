package com.example.vacinaapp.recyclerViewAdapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vacinaapp.R
import com.example.vacinaapp.dataClass.CampanhasDataClass

class CampanhasAdapterHome(private val listaCampanhas: ArrayList<CampanhasDataClass>, val clickLambda: (Int)-> Unit): RecyclerView.Adapter<CampanhasHome>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CampanhasHome {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_item_campanha_home,parent,false)
        return CampanhasHome(itemView)
    }

    override fun getItemCount(): Int {
        return listaCampanhas.size
    }

    override fun onBindViewHolder(holder: CampanhasHome, position: Int) {
        val currentItem = listaCampanhas[position]

        holder.campanhaTituloTextview.text = currentItem.nome_campanha
        holder.dataTextview.text = currentItem.data
        holder.horarioTextview.text = currentItem.horario
        holder.local.text = currentItem.nome_posto

        holder.itemView.setOnClickListener {
            clickLambda(currentItem.id_campanha)
        }
    }



}

class CampanhasHome(itemView: View): RecyclerView.ViewHolder(itemView){

    val campanhaTituloTextview: TextView = itemView.findViewById(R.id.campanha_titulo)
    val dataTextview: TextView = itemView.findViewById(R.id.datas)
    val horarioTextview: TextView = itemView.findViewById(R.id.horarios)
    val local: TextView = itemView.findViewById(R.id.posto)

}