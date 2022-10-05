package com.example.vacinaapp.fragments

import android.database.Cursor
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.vacinaapp.DataHelper
import com.example.vacinaapp.R


class LocalDetalhesFragment : Fragment() {

    //o id do textview do titulo é nome_posto_titulo
    //id do textview do endereco é textview_endereco
    //id do textview do endereco é textview_telefone
    //id do textview dos horarios: segunda_label, terca_label ...
    //id da textview da tabela de vacinas no xml: doenca_texview, disp_textview, ver_textview

    //private lateinit var recyclerViewVacina: RecyclerView
    //private lateinit var locaisArraylist: ArrayList<LocaisDataclass>


    companion object {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_local_detalhes, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()

    }

    fun getKey(): Int { //pega o id do local do bundle
        val args = this.arguments
        val inputData = args?.get("key") // variavel do id_local
        return inputData.toString().toInt()
    }

    private fun getData(){
        val key_local = getKey()

        val db = DataHelper(requireContext())
        val CursorDados: Cursor? = db.rawQuery("SELECT id_local, posto_saude, distrito, endereco, telefone," +
                "segunda, terca, quarta, quinta, sexta, sabado, domingo FROM tabela_postos WHERE id_local = " + key_local )
        if(CursorDados!!.moveToNext()){
            val localId = CursorDados.getInt(0)
            val localPosto = CursorDados.getString(1)
            val localDistrito = CursorDados.getString(2)
            val localEndereco = CursorDados.getString(3)
            val telefone = CursorDados.getString(4)
            val horSegunda = CursorDados.getString(5)
            val horTerca = CursorDados.getString(6)
            val horQuarta = CursorDados.getString(7)
            val horQuinta = CursorDados.getString(8)
            val horSexta = CursorDados.getString(9)
            val horSabado = CursorDados.getString(10)
            val horDomingo = CursorDados.getString(11)

            Log.d("Dados_posto: ", "id_local=" + localId + " posto_saude=" + localPosto + " distrito= " + localDistrito
                    + " endereco=" + localEndereco + " telefone=" + telefone + " segunda=" + horSegunda)

            val textviewPosto: TextView = requireView().findViewById(R.id.nome_posto_titulo)
            val textviewEndereco: TextView = requireView().findViewById(R.id.textview_endereco)
            val textviewTelefone: TextView = requireView().findViewById(R.id.textview_telefone)
            val textviewSegunda: TextView = requireView().findViewById(R.id.segunda_label)
            val textviewTerca: TextView = requireView().findViewById(R.id.terca_label)
            val textviewQuarta: TextView = requireView().findViewById(R.id.quarta_label)
            val textviewQuinta: TextView = requireView().findViewById(R.id.quinta_label)
            val textviewSexta: TextView = requireView().findViewById(R.id.sexta_label)
            val textviewSabado: TextView = requireView().findViewById(R.id.sabado_label)
            val textviewDomingo: TextView = requireView().findViewById(R.id.domingo_label)

            textviewPosto.text = localPosto
            textviewEndereco.text = localEndereco
            textviewTelefone.text = telefone
            textviewSegunda.text = horSegunda
            textviewTerca.text = horTerca
            textviewQuarta.text = horQuarta
            textviewQuinta.text = horQuinta
            textviewSexta.text = horSexta
            textviewSabado.text = horSabado
            textviewDomingo.text = horDomingo

        }

    }

}