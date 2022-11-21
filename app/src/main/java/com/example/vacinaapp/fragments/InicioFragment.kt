package com.example.vacinaapp.fragments

import android.content.SharedPreferences
import android.database.Cursor
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
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

    private lateinit var inicioFragment: InicioFragment
    private var db: DataHelper? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var locaisArraylist: ArrayList<LocaisDataclass>
    private lateinit var recyclerViewCampanha: RecyclerView
    private lateinit var campanhasArraylist: ArrayList<CampanhasDataClass>
    private lateinit var locaisAdapter: LocaisAdapter
    private lateinit var campanhasCursor: Cursor
    private lateinit var locaisCursor: Cursor

    private lateinit var binding: FragmentInicioBinding
    private var filterSet: Set<String>? = setOf("1,2,3,4")//numero do filtro do distrito
    private lateinit var filterString: String
    private var key_distrito_pref = "resposta_distritos"
    private lateinit var sharedPrefs: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentInicioBinding.inflate(inflater)

        readSetString()
        locaisArraylist = ArrayList()

        return binding.root
    }

    private fun readSetString(){
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(requireContext())
        filterSet = sharedPrefs.getStringSet(key_distrito_pref, setOf("1,2,3,4"))
        filterString = filterSet.toString().replace("[", "").replace("]", "")
        //Log.d("filterset", "filterSet=$filterSet")
        //Log.d("filterString", "filterString=$filterString")


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
        if (filterString.isEmpty()){
            locaisCursor = db!!.rawQuery(
                "SELECT * FROM tabela_postos"
            )
        } else {
            locaisCursor = db!!.rawQuery(
                "SELECT * FROM tabela_postos WHERE id_distrito_fk IN ($filterString)"
            )
        }


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

        if(filterString.contains("1, 2, 3, 4")){
            campanhasCursor = db!!.rawQuery(
                "SELECT * FROM tab_campanha ORDER BY random() LIMIT 3"
            )
        } else if (filterString.isEmpty()){
            campanhasCursor = db!!.rawQuery(
                "SELECT * FROM tab_campanha ORDER BY RANDOM() LIMIT 3"
            )
        } else {
            campanhasCursor = db!!.rawQuery(
                "SELECT * FROM tab_campanha WHERE distrito_fk IN ($filterString)"
            )
        }


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
        val ft = parentFragmentManager.beginTransaction()
        ft.replace(R.id.fragment_container, fragmentoDetalhesCampanha)
        ft.addToBackStack("iniciofrag")
        ft.commit()


    }

}