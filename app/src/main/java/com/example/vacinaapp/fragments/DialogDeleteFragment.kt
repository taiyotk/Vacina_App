package com.example.vacinaapp.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import com.example.vacinaapp.DataHelper
import com.example.vacinaapp.recyclerViewAdapters.CampanhasEditAdapter


class DialogDeleteFragment : DialogFragment() {

    companion object {

        fun newInstance(IdPosto: Int): DialogDeleteFragment {
            val frag = DialogDeleteFragment()
            val args = Bundle()
            args.putInt("ID_CAMPANHA_KEY", IdPosto)
            frag.arguments = args
            return frag
        }
    }

    private val dataHelper: DataHelper = DataHelper(requireContext())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(androidx.appcompat.R.layout.abc_alert_dialog_material, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val b = AlertDialog.Builder(requireContext())
            .setTitle("Apagar Campanha")
            .setMessage("Deseja Realmente apagar a campanha?")
            .setPositiveButton("Sim") { _, _ ->
                val id_camp = arguments?.getInt("ID_CAMPANHA_KEY")

                dataHelper.deleteCampanha(id_camp)
                if(dataHelper.deleteCampanha(id_camp) == true){
                    Toast.makeText(requireContext(), "Campanha apagada", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Campanha não apagada", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancelar") { dialogInterface, _ ->
                dialogInterface.cancel()
            }


        return b.create()
    }

    /*fun deleteCamp(): Boolean{
        val id_camp = arguments?.getInt("ID_CAMPANHA_KEY")

        dataHelper.deleteCampanha(id_camp)
        if(dataHelper.deleteCampanha(id_camp) == true){
            Toast.makeText(requireContext(), "Campanha apagada", Toast.LENGTH_SHORT).show()
            return true
        } else{
            Toast.makeText(requireContext(), "Campanha não apagada", Toast.LENGTH_SHORT).show()
            return false
        }
    }*/
}