package com.example.vacinaapp.recyclerViewAdapters

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.vacinaapp.R
import com.example.vacinaapp.dataClass.VacinasDataClass
import com.example.vacinaapp.fragments.LocalDetalhesFragment

class VacinasResultAdapter (private var listaVacinasResultado: ArrayList<VacinasDataClass>): RecyclerView.Adapter<VacinasResultViewHolder>(){

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacinasResultViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.search_vacina_item_layout,parent,false)
        return VacinasResultViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return listaVacinasResultado.size
    }

    override fun onBindViewHolder(holder: VacinasResultViewHolder, position: Int){
        val currentItem = listaVacinasResultado[position]

        holder.doenca_result.text = currentItem.doenca
        holder.local_posto.text = currentItem.posto_nome
        holder.vacina_item.id = currentItem.id_posto

        holder.vacina_item.setOnClickListener{
            context = holder.itemView.context
            val id_posto = currentItem.id_posto
            val fragLocalDetalhes = LocalDetalhesFragment()
            fun getKey(key: Int){

                val bundle = Bundle()
                bundle.putInt("key", key)
                fragLocalDetalhes.arguments = bundle

                val fm = (context as FragmentActivity).supportFragmentManager
                val ft = fm.beginTransaction()
                ft.replace(R.id.fragment_container, fragLocalDetalhes, null)
                ft.commit()

            }
            getKey(id_posto)
        }
    }

    fun filterlist(filterlist: ArrayList<VacinasDataClass>){
        listaVacinasResultado = filterlist
        notifyDataSetChanged()
    }


}

class VacinasResultViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    val doenca_result: TextView = itemView.findViewById(R.id.doenca_item_result)
    val local_posto: TextView = itemView.findViewById(R.id.local_item_result)
    val vacina_item: LinearLayout = itemView.findViewById(R.id.vacina_result_item)
}