package com.example.vacinaapp.fragments

import android.database.Cursor
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vacinaapp.DataHelper
import com.example.vacinaapp.R
import com.example.vacinaapp.dataClass.CampanhasDataClass
import com.example.vacinaapp.dataClass.LocaisDataclass
import com.example.vacinaapp.databinding.FragmentInicioBinding
import com.example.vacinaapp.recyclerViewAdapters.CampanhasAdapter
import com.example.vacinaapp.recyclerViewAdapters.LocaisAdapter

class InicioFragment : Fragment() {

    private var db: DataHelper? = null

    private lateinit var recyclerView: RecyclerView
    private lateinit var locaisArraylist: ArrayList<LocaisDataclass>

    private lateinit var recyclerViewCampanha: RecyclerView
    private lateinit var campanhasArraylist: ArrayList<CampanhasDataClass>

    private lateinit var binding: FragmentInicioBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentInicioBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataInitializeLocais()
        dataInitializeCampanhas()

        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = LocaisAdapter(locaisArraylist) {
            locaisArraylist[it]
        }
        recyclerView.adapter = LocaisAdapter(locaisArraylist) {
            listOnClickLocais(it)
        }

        val campanhaslayoutManager = LinearLayoutManager(context)
        recyclerViewCampanha = view.findViewById(R.id.campanhas_recyclerview)
        recyclerViewCampanha.layoutManager = campanhaslayoutManager
        recyclerViewCampanha.setHasFixedSize(true)
        recyclerViewCampanha.isNestedScrollingEnabled = false
        recyclerViewCampanha.adapter = CampanhasAdapter(campanhasArraylist) {
            campanhasArraylist[it]
        }
        recyclerViewCampanha.adapter = CampanhasAdapter(campanhasArraylist) {
            listOnClickCampanhas(it)
        }


    }


    private fun dataInitializeLocais() {
        db = DataHelper(requireContext())

        //checa se o fetch deu certo
        val locaisCursor: Cursor = db!!.rawQuery(
            "SELECT id_local, posto_saude, distrito, endereco, telefone," +
                    "segunda, terca, quarta, quinta, sexta, sabado, domingo FROM tabela_postos"
        )
        val locaisSize: Int = locaisCursor.count
        Log.d("listLocais()", "locaisSize=$locaisSize")

        // Add a list of locais
        locaisArraylist = ArrayList()
        while (locaisCursor.moveToNext()) {
            val localId = locaisCursor.getInt(0)
            val localPosto = locaisCursor.getString(1)
            val localDistrito = locaisCursor.getString(2)
            val localEndereco = locaisCursor.getString(3)
            val telefone = locaisCursor.getString(4)
            val horSegunda = locaisCursor.getString(5)
            val horTerca = locaisCursor.getString(6)
            val horQuarta = locaisCursor.getString(7)
            val horQuinta = locaisCursor.getString(8)
            val horSexta = locaisCursor.getString(9)
            val horSabado = locaisCursor.getString(10)
            val horDomingo = locaisCursor.getString(11)


            Log.d(
                "listCategories()",
                "id_local=" + localId + " posto_saude=" + localPosto + " endereco=" +
                        localEndereco
            )
            locaisArraylist.add(
                LocaisDataclass(
                    localId, localPosto, localDistrito, localEndereco, telefone,
                    horSegunda, horTerca, horQuarta, horQuinta, horSexta, horSabado, horDomingo
                )
            )

        }

    }

    private fun dataInitializeCampanhas() {

        db = DataHelper(requireContext())

        //checa se o fetch deu certo
        val campanhasCursor: Cursor = db!!.rawQuery(
            "SELECT id_campanha, distrito_campanha, id_posto_campanha, posto_nome_campanha, nome_campanha, doenca_campanha, data, horario, publico_campanha, detalhes" +
                    " FROM tab_campanha"
        )

        val vacinasSize: Int = campanhasCursor.count
        Log.d("listVacinas()", "vacinasSize=$vacinasSize")

        // Add a list of vacinas
        campanhasArraylist = ArrayList()
        while (campanhasCursor.moveToNext()) {
            val idCampanha = campanhasCursor.getInt(0)
            val distritoCampanha = campanhasCursor.getString(1)
            val idPostoCampanha = campanhasCursor.getInt(2)
            val postoNomeCampanha = campanhasCursor.getString(3)
            val nomeCampanha = campanhasCursor.getString(4)
            val doencaCampanha = campanhasCursor.getString(5)
            val data = campanhasCursor.getString(6)
            val horario = campanhasCursor.getString(7)
            val publicoCampanha = campanhasCursor.getString(8)
            val detalhes = campanhasCursor.getString(9)

            campanhasArraylist.add(
                CampanhasDataClass(
                    idCampanha,
                    distritoCampanha,
                    idPostoCampanha,
                    postoNomeCampanha,
                    nomeCampanha,
                    doencaCampanha,
                    data,
                    horario,
                    publicoCampanha,
                    detalhes
                )
            )

        }

    }

    private fun listOnClickCampanhas(itemID: Int) {
        val fragmentoDetalhesCampanha = CampanhaDetalhesFragment()
        val bundle = Bundle()
        bundle.putInt("key", itemID)
        fragmentoDetalhesCampanha.arguments = bundle
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragmentoDetalhesCampanha).commit()

    }

    private fun listOnClickLocais(itemID: Int) {
        val fragmentoDetalhes = LocalDetalhesFragment()
        val bundle = Bundle()
        bundle.putInt("key", itemID)
        fragmentoDetalhes.arguments = bundle
        parentFragmentManager.beginTransaction().replace(R.id.fragment_container, fragmentoDetalhes)
            .commit()

    }

}