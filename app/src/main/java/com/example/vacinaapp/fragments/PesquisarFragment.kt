package com.example.vacinaapp.fragments

import android.database.Cursor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vacinaapp.DataHelper
import com.example.vacinaapp.R
import com.example.vacinaapp.dataClass.VacinasDataClass
import com.example.vacinaapp.recyclerViewAdapters.VacinasResultAdapter
import kotlin.collections.ArrayList

class PesquisarFragment : Fragment() {

    private lateinit var recyclerViewVacina: RecyclerView
    private lateinit var vacinaArrayList: ArrayList<VacinasDataClass>
    private lateinit var vacinasResultAdapter: VacinasResultAdapter
    private lateinit var db: DataHelper
    private lateinit var searchViewVacina: SearchView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_pesquisar, container, false)
        searchViewVacina = view.findViewById(R.id.searchView_vacina)

        val layoutManager = LinearLayoutManager(context)
        vacinaArrayList = ArrayList()
        vacinasResultAdapter = VacinasResultAdapter(vacinaArrayList)
        recyclerViewVacina = view.findViewById(R.id.reciclerview_results)
        recyclerViewVacina.layoutManager = layoutManager
        recyclerViewVacina.adapter = vacinasResultAdapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getData()

        searchViewVacina.setOnQueryTextListener(object: SearchView.OnQueryTextListener{

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filterVacina(newText)
                return false
            }

        })

    }

    fun getData(){
        db = DataHelper(requireContext())
        val cursorVacina: Cursor = db.rawQuery(
            "SELECT * FROM tabela_vacina WHERE disponibilidade = 'Sim' ORDER BY doenca ASC"
        )

        while (cursorVacina.moveToNext()){
            val idVacina = cursorVacina.getInt(0)
            val idPosto = cursorVacina.getInt(1)
            val postoNome = cursorVacina.getString(2)
            val doenca = cursorVacina.getString(3)
            val disponibilidade = cursorVacina.getString(4)
            val publico = cursorVacina.getString(5)

            vacinaArrayList.add(
                VacinasDataClass(idVacina, idPosto, postoNome, doenca, disponibilidade, publico)
            )
        }

    }

    fun filterVacina(text: String){
        val filteredList: ArrayList<VacinasDataClass> = ArrayList()

        for(item in vacinaArrayList){

            if(item.doenca.lowercase().contains(text.lowercase())){
                filteredList.add(item)
            }

            if(filteredList.isNotEmpty()){
                vacinasResultAdapter.filterlist(filteredList)
            }
        }

    }

}