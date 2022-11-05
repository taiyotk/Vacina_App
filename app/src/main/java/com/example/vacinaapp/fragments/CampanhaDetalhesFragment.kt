package com.example.vacinaapp.fragments

import android.database.Cursor
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.vacinaapp.DataHelper
import com.example.vacinaapp.R

class CampanhaDetalhesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_campanha_detalhes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()

    }

    private fun getKey(): Int { //pega o id do local do bundle
        val args = this.arguments
        val inputData = args?.get("key") // variavel do id_local
        return inputData.toString().toInt()
    }

    private fun getData() {
        val keyLocal = getKey()

        val db = DataHelper(requireContext())
        val campanhasCursor: Cursor = db.rawQuery(
            "SELECT id_campanha, distrito_fk, distrito_campanha, id_posto_campanha, posto_nome_campanha, nome_campanha, doenca_campanha, data, horario, publico_campanha, detalhes" +
                    " FROM tab_campanha WHERE id_campanha = " + keyLocal
        )

        if (campanhasCursor.moveToNext()) {
            val idCampanha = campanhasCursor.getInt(0)
            val idDistrito = campanhasCursor.getInt(1)
            val distritoCampanha = campanhasCursor.getString(2)
            val idPostoCampanha = campanhasCursor.getInt(3)
            val postoNomeCampanha = campanhasCursor.getString(4)
            val nomeCampanha = campanhasCursor.getString(5)
            val doencaCampanha = campanhasCursor.getString(6)
            val data = campanhasCursor.getString(7)
            val horario = campanhasCursor.getString(8)
            val publicoCampanha = campanhasCursor.getString(9)
            val detalhes = campanhasCursor.getString(10)

            Log.d(
                "listCategories()",
                "id_campanha= $idCampanha, distrito= $distritoCampanha, id_posto= $idPostoCampanha, id_distrito=$idDistrito"
            )

            val textviewNomeCampanha: TextView = requireView().findViewById(R.id.campanha_nome)
            val textviewDoenca: TextView = requireView().findViewById(R.id.doenca_label)
            val textviewData: TextView = requireView().findViewById(R.id.data_label)
            val textviewLocal: TextView = requireView().findViewById(R.id.posto_nome_label)
            val textviewHorario: TextView = requireView().findViewById(R.id.horario_label)
            val textviewPublicoAlvo: TextView = requireView().findViewById(R.id.publico_alvo_label)
            val textviewDetalhes: TextView = requireView().findViewById(R.id.detalhes_label)


            textviewDoenca.text = doencaCampanha
            textviewData.text = data
            textviewLocal.text = postoNomeCampanha
            textviewHorario.text = horario
            textviewPublicoAlvo.text = publicoCampanha
            textviewDetalhes.text = detalhes
            textviewNomeCampanha.text = nomeCampanha
        }


    }

}