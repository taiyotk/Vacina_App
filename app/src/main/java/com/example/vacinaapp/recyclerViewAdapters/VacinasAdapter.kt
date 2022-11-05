package com.example.vacinaapp.recyclerViewAdapters

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.vacinaapp.R
import com.example.vacinaapp.dataClass.VacinasDataClass
import com.example.vacinaapp.fragments.AtualizarVacinaFragment
import kotlinx.android.synthetic.main.fragment_dialog_ver.view.*


class VacinasAdapter(
    private val listaVacinas: ArrayList<VacinasDataClass>,
    val clickLambda: (Int) -> Unit
) : RecyclerView.Adapter<VacinasViewHolder>() {

    private lateinit var context: Context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacinasViewHolder {

        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_item_vacina, parent, false)
        return VacinasViewHolder(itemView)

    }

    override fun getItemCount(): Int {
        return listaVacinas.size
    }

    override fun onBindViewHolder(holder: VacinasViewHolder, position: Int) {

        val currentItem = listaVacinas[position]
        holder.doenca.text = currentItem.doenca
        holder.disponibilidade.text = currentItem.disponibilidade
        //holder.ver.text = currentItem.publico

        //funcao que muda a cor do sim para verde e não para vermelho
        if (holder.disponibilidade.text == "Sim") {
            holder.disponibilidade.setTextColor(Color.parseColor("#00b400"))
            holder.disponibilidade.setTypeface(null, Typeface.BOLD)
        } else if (holder.disponibilidade.text == "Não") {
            holder.disponibilidade.setTextColor(Color.parseColor("#db0200"))
            holder.disponibilidade.setTypeface(null, Typeface.BOLD)
        }

        //clicklistener do Publico
        holder.ver.setOnClickListener {

            clickLambda(currentItem.id_posto)
            //Toast.makeText(holder.itemView.context, "Ver foi Clicado!", Toast.LENGTH_SHORT).show()

            context = holder.itemView.context
            val verDialogView =
                LayoutInflater.from(context).inflate(R.layout.fragment_dialog_ver, null)
            val builder = AlertDialog.Builder(context).setView(verDialogView)

            verDialogView.ver.text = currentItem.publico

            builder.show()

        }

        //clicklistener do Editar
        holder.edit.setOnClickListener {
            //Toast.makeText(holder.itemView.context, "Editar clicado!", Toast.LENGTH_SHORT).show()

            context = holder.itemView.context
            val id_key = currentItem.id_vacina
            fun getKey(key: Int) {
                val fragment = AtualizarVacinaFragment()
                val bundle = Bundle()
                bundle.putInt("Id_vacina", key)
                fragment.arguments = bundle

                val fm = (context as FragmentActivity).supportFragmentManager
                val ft = fm.beginTransaction()
                ft.replace(R.id.fragment_container, fragment, "fragment")
                ft.addToBackStack("fragment")
                ft.commit()

            }
            getKey(id_key)

        }
    }
}

class VacinasViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val doenca: TextView = itemView.findViewById(R.id.doenca_textview)
    val disponibilidade: TextView = itemView.findViewById(R.id.disp_textview)
    val ver: ImageButton = itemView.findViewById(R.id.ver_botao)
    val edit: TextView = itemView.findViewById(R.id.edit_icon_clickable)

}

