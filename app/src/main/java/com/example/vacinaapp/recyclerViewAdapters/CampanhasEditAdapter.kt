package com.example.vacinaapp.recyclerViewAdapters


import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.vacinaapp.DataHelper
import com.example.vacinaapp.R
import com.example.vacinaapp.dataClass.CampanhasDataClass
import com.example.vacinaapp.fragments.AtualizarCampanhaFragment

class CampanhasEditAdapter(val listaCampanhasEdit: ArrayList<CampanhasDataClass>, val clickId: (Int)-> Unit): RecyclerView.Adapter<CampanhasEditViewHolder>() {

    private lateinit var context: Context


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
        holder.btnDelete.setOnClickListener {
            context = holder.itemView.context
            val id = currentItem.id_campanha
            fun onClick(v: View) {
                val builder: AlertDialog.Builder = AlertDialog.Builder(context)
                builder.setTitle("Apagar Campanha")
                builder.setMessage("Deseja Realmente apagar a campanha?")
                builder.setPositiveButton("Sim", DialogInterface.OnClickListener { dialogInterface, i ->
                    val db = DataHelper(context)
                    db.deleteCampanha(id)
                    if(db.deleteCampanha(id) == true){
                        Toast.makeText(context, "Campanha apagada", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Campanha não apagada", Toast.LENGTH_SHORT).show()
                    }
                    listaCampanhasEdit.removeAt(position)

                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position, listaCampanhasEdit.size)

                })
                builder.setNegativeButton("Não", DialogInterface.OnClickListener { dialogInterface, i ->
                    dialogInterface.cancel()
                })
                    builder.show()

                //val fragment = DialogDeleteFragment.newInstance(id)
                //val fm = (context as FragmentActivity).supportFragmentManager.beginTransaction()
                //fragment.show(fm, "myFragment")
            }
            onClick(holder.btnDelete)
            //Toast.makeText(holder.itemView.context, "$id", Toast.LENGTH_SHORT).show()
        }

        holder.btnUpdate.setOnClickListener {
            context = holder.itemView.context
            val id_key = currentItem.id_campanha
            fun getKey(key: Int){
                val fragment = AtualizarCampanhaFragment()
                val bundle = Bundle()
                bundle.putInt("Id_campanha", key)
                fragment.arguments = bundle

                val fm = (context as FragmentActivity).supportFragmentManager
                val ft = fm.beginTransaction()
                ft.replace(R.id.fragment_container, fragment)
                ft.addToBackStack(null)
                ft.commit()

            }
            getKey(id_key)
        }
    }


}

class CampanhasEditViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    val campanhaTituloTextview: TextView = itemView.findViewById(R.id.item_nome_campanha)
    val dataTextview: TextView = itemView.findViewById(R.id.item_data_campanha)
    val horarioTextview: TextView = itemView.findViewById(R.id.item_horario_campanha)
    val postoNomeCampanha: TextView = itemView.findViewById(R.id.posto_nome_campanha_label)
    val btnDelete: Button = itemView.findViewById(R.id.button_delete_campanha)
    val btnUpdate: Button = itemView.findViewById(R.id.button_update_campanha)

}