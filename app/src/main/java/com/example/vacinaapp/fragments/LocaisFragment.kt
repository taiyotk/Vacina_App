package com.example.vacinaapp.fragments

import android.database.Cursor
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vacinaapp.DataHelper
import com.example.vacinaapp.recyclerViewAdapters.LocaisAdapter
import com.example.vacinaapp.dataClass.LocaisDataclass
import com.example.vacinaapp.R
import com.example.vacinaapp.databinding.FragmentLocaisBinding

class LocaisFragment : Fragment() {

    var db: DataHelper? = null

    private lateinit var adapter: LocaisAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var locaisArraylist: ArrayList<LocaisDataclass>




    lateinit var binding: FragmentLocaisBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLocaisBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataInitialize()

        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = LocaisAdapter(locaisArraylist) {
            locaisArraylist[it]
        }
        recyclerView.adapter = LocaisAdapter(locaisArraylist){
            listOnClick(it)
        }


    }


    fun dataInitialize(){
        db = DataHelper(requireContext())

        //checa se o fetch deu certo
        var locaisCursor: Cursor? = db!!.rawQuery("SELECT id_local, posto_saude, distrito, endereco, telefone," +
                "segunda, terca, quarta, quinta, sexta, sabado, domingo FROM tabela_postos")
        var locaisSize: Int = locaisCursor!!.count
        Log.d("listLocais()", "locaisSize=" + locaisSize)

        // Add a list of locais
        locaisArraylist = ArrayList<LocaisDataclass>()
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


            Log.d("listCategories()", "id_local=" + localId + " posto_saude=" + localPosto + " endereco=" +
                    localEndereco)
            locaisArraylist.add(
                LocaisDataclass(localId, localPosto, localDistrito, localEndereco, telefone,
            horSegunda, horTerca, horQuarta, horQuinta, horSexta, horSabado, horDomingo)
            )

        }

    }

    fun listOnClick(itemID: Int){
        val fragmentoDetalhes = LocalDetalhesFragment()
        val bundle = Bundle()
        bundle.putInt("key", itemID)
        fragmentoDetalhes.arguments = bundle
        parentFragmentManager.beginTransaction().replace(R.id.fragment_container, fragmentoDetalhes).commit()

    }

}