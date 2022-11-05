package com.example.vacinaapp.fragments

import android.database.Cursor
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vacinaapp.DataHelper
import com.example.vacinaapp.recyclerViewAdapters.LocaisAdapter
import com.example.vacinaapp.dataClass.LocaisDataclass
import com.example.vacinaapp.R

class LocaisFragment : Fragment() {

    private var db: DataHelper? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var locaisArraylist: ArrayList<LocaisDataclass>
    private lateinit var searchViewLocais: SearchView
    private lateinit var locaisAdapter: LocaisAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_locais, container, false)
        searchViewLocais = view.findViewById(R.id.searchViewLocal)

        val layoutManager = LinearLayoutManager(context)
        locaisArraylist = ArrayList()
        locaisAdapter = LocaisAdapter(locaisArraylist)
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = locaisAdapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataInitialize()

        searchViewLocais.setOnQueryTextListener(object : SearchView.OnQueryTextListener{

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filterLocal(newText)
                return false
            }
        })

        val closeButton: View? = searchViewLocais.findViewById(androidx.appcompat.R.id.search_close_btn)
        closeButton?.setOnClickListener{
            searchViewLocais.clearFocus()
            searchViewLocais.setQuery("", false)
        }

    }


    private fun dataInitialize(){
        db = DataHelper(requireContext())

        //checa se o fetch deu certo
        val locaisCursor: Cursor = db!!.rawQuery("SELECT id_local, posto_saude, id_distrito_fk, distrito, endereco, telefone," +
                "segunda, terca, quarta, quinta, sexta, sabado, domingo FROM tabela_postos")
        val locaisSize: Int = locaisCursor.count
        Log.d("listLocais()", "locaisSize=" + locaisSize)

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


            Log.d("listCategories()",
                "id_local=$localId posto_saude=$localPosto endereco=$localEndereco id_fk=$distritoId"
            )
            locaisArraylist.add(
                LocaisDataclass(localId, localPosto, distritoId, localDistrito, localEndereco, telefone,
            horSegunda, horTerca, horQuarta, horQuinta, horSexta, horSabado, horDomingo)
            )

        }

    }


    fun filterLocal(text: String){
        val filteredList: ArrayList<LocaisDataclass> = ArrayList()

        for(item in locaisArraylist){
            if(item.posto_saude.lowercase().contains(text.lowercase()) or item.distrito.lowercase().contains(text.lowercase())){
                filteredList.add(item)
            }

            if(filteredList.isNotEmpty()){
                locaisAdapter.filterlist(filteredList)
            }

        }
    }

}