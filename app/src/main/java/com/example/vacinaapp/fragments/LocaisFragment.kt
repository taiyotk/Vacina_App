package com.example.vacinaapp.fragments

import android.database.Cursor
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vacinaapp.DataHelper
import com.example.vacinaapp.LocaisAdapter
import com.example.vacinaapp.recyclerViewAdapters.LocaisDataclass
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
        var locaisCursor: Cursor? = db!!.rawQuery("SELECT id, posto_saude, endereco FROM tabela_postos WHERE id > 0")
        var locaisSize: Int = locaisCursor!!.count
        Log.d("listLocais()", "locaisSize=" + locaisSize)

        // Add a list of locais
        locaisArraylist = ArrayList<LocaisDataclass>()
        while (locaisCursor.moveToNext()) {
            val localId = locaisCursor.getInt(0)
            val localPosto = locaisCursor.getString(1)
            val localEndereco = locaisCursor.getString(2)
            Log.d("listCategories()", "id=" + localId + " posto_saude=" + localPosto + " endereco=" +
                    localEndereco)
            locaisArraylist.add(LocaisDataclass(localId, localPosto, localEndereco))

        }

    }

    fun listOnClick(itemID: Int){
        Toast.makeText(context, "Item clicked has ID " + itemID, Toast.LENGTH_SHORT).show()
    }

}