package com.example.vacinaapp.recyclerViewAdapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.example.vacinaapp.R
import com.example.vacinaapp.dataClass.CampanhasDataClass

class CampanhasEditAdapter(val listaCampanhasEdit: ArrayList<CampanhasDataClass>, val clickId: (Int)-> Unit): RecyclerView.Adapter<CampanhasEditViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CampanhasEditViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_item_campanha_edit,parent,false)
        return CampanhasEditViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return listaCampanhasEdit.size
    }

    override fun onBindViewHolder(holder: CampanhasEditViewHolder, position: Int) {
        val currentItem = listaCampanhasEdit[position]

        holder.campanhaTituloTextview.text = currentItem.nome_campanha
        holder.dataTextview.text = currentItem.data
        holder.horarioTextview.text = currentItem.horario
        holder.postoNomeCampanha.text = currentItem.nome_posto

        /*holder.btnDelete.setOnClickListener {
            val id = clickId(currentItem.id_campanha)
            Toast.makeText(holder.itemView.context, "$id", Toast.LENGTH_SHORT).show()
        }*/
    }



}

class CampanhasEditViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    val campanhaTituloTextview: TextView = itemView.findViewById(R.id.item_nome_campanha)
    val dataTextview: TextView = itemView.findViewById(R.id.item_data_campanha)
    val horarioTextview: TextView = itemView.findViewById(R.id.item_horario_campanha)
    val postoNomeCampanha: TextView = itemView.findViewById(R.id.posto_nome_campanha_label)
    val btnDelete: Button = itemView.findViewById(R.id.button_delete_campanha)

}