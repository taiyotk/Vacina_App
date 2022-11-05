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
import com.example.vacinaapp.recyclerViewAdapters.CampanhasAdapterHome
import com.example.vacinaapp.recyclerViewAdapters.LocaisAdapter

class InicioFragment : Fragment() {

    private var db: DataHelper? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var locaisArraylist: ArrayList<LocaisDataclass>
    private lateinit var recyclerViewCampanha: RecyclerView
    private lateinit var campanhasArraylist: ArrayList<CampanhasDataClass>
    private lateinit var locaisAdapter: LocaisAdapter
    private lateinit var binding: FragmentInicioBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentInicioBinding.inflate(inflater)
        locaisArraylist = ArrayList()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataInitializeLocais()
        dataInitializeCampanhas()

        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.recycler_view)
        locaisAdapter = LocaisAdapter(locaisArraylist)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = locaisAdapter

        val campanhaslayoutManager = LinearLayoutManager(context)
        recyclerViewCampanha = view.findViewById(R.id.campanhas_recyclerview)
        recyclerViewCampanha.layoutManager = campanhaslayoutManager
        recyclerViewCampanha.setHasFixedSize(true)
        recyclerViewCampanha.isNestedScrollingEnabled = false
        recyclerViewCampanha.adapter = CampanhasAdapterHome(campanhasArraylist) {
            campanhasArraylist[it]
        }
        recyclerViewCampanha.adapter = CampanhasAdapterHome(campanhasArraylist) {
            listOnClickCampanhas(it)
        }


    }


    private fun dataInitializeLocais() {
        db = DataHelper(requireContext())

        //checa se o fetch deu certo
        val locaisCursor: Cursor = db!!.rawQuery(
            "SELECT * FROM tabela_postos"
        )
        val locaisSize: Int = locaisCursor.count
        Log.d("listLocais()", "locaisSize=$locaisSize")

        // Add a list of locais
        while (locaisCursor.moveToNext()) {
            val localId = locaisCursor.getInt(0)
            val localPosto = locaisCursor.getString(1)
            val distritoId = locaisCursor.getInt(2)
            val localDistrito = locaisCursor.getString(3)
            val localEndereco = locaisCursor.getString(4)
            val telefone = locaisCursor.getString(5)
            val horSegunda = locaisCursor.getString(6)
            val horTerca = locaisCursor.getString(7)
            val horQuarta = locaisCursor.getString(8)
            val horQuinta = locaisCursor.getString(9)
            val horSexta = locaisCursor.getString(10)
            val horSabado = locaisCursor.getString(11)
            val horDomingo = locaisCursor.getString(12)


            Log.d(
                "listCategories()",
                "id_local=" + localId + " posto_saude=" + localPosto + " endereco=" +
                        localEndereco
            )
            locaisArraylist.add(
                LocaisDataclass(
                    localId, localPosto, distritoId, localDistrito, localEndereco, telefone,
                    horSegunda, horTerca, horQuarta, horQuinta, horSexta, horSabado, horDomingo
                )
            )

        }

    }

    private fun dataInitializeCampanhas() {

        db = DataHelper(requireContext())

        //checa se o fetch deu certo
        val campanhasCursor: Cursor = db!!.rawQuery(
            "SELECT * FROM tab_campanha WHERE id_campanha ORDER BY random() LIMIT 3"
        )

        val vacinasSize: Int = campanhasCursor.count
        Log.d("listVacinas()", "vacinasSize=$vacinasSize")

        // Add a list of vacinas
        campanhasArraylist = ArrayList()
        while (campanhasCursor.moveToNext()) {
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

            campanhasArraylist.add(
                CampanhasDataClass(
                    idCampanha,
                    idDistrito,
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

}