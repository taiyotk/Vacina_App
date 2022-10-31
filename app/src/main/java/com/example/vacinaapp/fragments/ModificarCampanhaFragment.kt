package com.example.vacinaapp.fragments

import android.database.Cursor
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vacinaapp.DataHelper
import com.example.vacinaapp.R
import com.example.vacinaapp.dataClass.CampanhasDataClass
import com.example.vacinaapp.recyclerViewAdapters.CampanhasEditAdapter
import kotlinx.android.synthetic.main.fragment_modificar_campanha.*

class ModificarCampanhaFragment : Fragment() {

    private var db: DataHelper? = null
    private lateinit var postoSpinner: Spinner
    private lateinit var arrayCampanhasEdit: ArrayList<CampanhasDataClass>
    private lateinit var recyclerView: RecyclerView
    private lateinit var btnAdCampanhaFragment: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_modificar_campanha, container, false)

        postoSpinner = view.findViewById(R.id.spinner_postos)
        btnAdCampanhaFragment = view.findViewById(R.id.button_adic_campanha)

        recyclerView = view.findViewById(R.id.recycler_view_campanhas_edit)
        arrayCampanhasEdit = ArrayList()
        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        recyclerView.isNestedScrollingEnabled = false
        recyclerView.adapter = CampanhasEditAdapter(arrayCampanhasEdit) {
            arrayCampanhasEdit[it]
        }

        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        carregarCampanhas(0)

        btnAdCampanhaFragment.setOnClickListener {
            changeFragment()
        }

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.postos_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner_postos.adapter = adapter
        }

        postoSpinner.onItemSelectedListener = object :
            OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val indexValue: Int = postoSpinner.selectedItemPosition

                when(indexValue){
                    0 -> {
                        clear()
                        carregarCampanhas(0)
                    }
                    1 -> {
                        clear()
                        carregarCampanhas(1)

                    }
                    2 -> {
                        clear()
                        carregarCampanhas(2)

                    }
                    3 -> {
                        clear()
                        carregarCampanhas(3)
                    }
                    4 -> {
                        clear()
                        carregarCampanhas(4)
                    }
                    5 -> {
                        clear()
                        carregarCampanhas(5)
                    }
                    6 -> {
                        clear()
                        carregarCampanhas(6)
                    }
                    7 -> {
                        clear()
                        carregarCampanhas(7)
                    }
                    8 -> {
                        clear()
                        carregarCampanhas(8)
                    }
                    9 -> {
                        clear()
                        carregarCampanhas(9)
                    }

                }
                //Toast.makeText(context, "Item selecionado: "+ postoArray[position], Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

    }

    private fun changeFragment(){
        val fragAdCampanhaFragment = AdicionarCampanhaFragment()
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragAdCampanhaFragment)
            .addToBackStack(null)
            .commit()
    }

    fun clear() {
        arrayCampanhasEdit.clear()
    }

    fun carregarCampanhas(valorIdFiltro: Int) {
        db = DataHelper(requireContext())

        if (valorIdFiltro == 0) {

            val campanhasCursor: Cursor = db!!.rawQuery("SELECT * FROM tab_campanha")
            val campanhasSize: Int = campanhasCursor.count
            Log.d("listLocais()", "locaisSize= $campanhasSize")


            while (campanhasCursor.moveToNext()) {
                val campanhaId = campanhasCursor.getInt(0)
                val campanhaDistrito = campanhasCursor.getString(1)
                val campanhaIdposto = campanhasCursor.getInt(2)
                val campanhaPostoNome = campanhasCursor.getString(3)
                val campanhaNome = campanhasCursor.getString(4)
                val campanhaDoenca = campanhasCursor.getString(5)
                val campanhaData = campanhasCursor.getString(6)
                val campanhaHorario = campanhasCursor.getString(7)
                val campanhaPublico = campanhasCursor.getString(8)
                val campanhaDetalhes = campanhasCursor.getString(9)

                Log.d(
                    "listaCampanhasEdit()",
                    "campanha_id= $campanhaId, distrito_campanha= $campanhaDistrito, id_posto_campanha= $campanhaIdposto, posto_nome_campanha= $campanhaPostoNome, nome_campanha= $campanhaNome, doenca_campanha=$campanhaDoenca, data= $campanhaData, horario= $campanhaHorario, publico_campanha= $campanhaPublico, detalhes= $campanhaDetalhes"
                )

                arrayCampanhasEdit.add(
                    CampanhasDataClass(
                        campanhaId,
                        campanhaDistrito,
                        campanhaIdposto,
                        campanhaPostoNome,
                        campanhaNome,
                        campanhaDoenca,
                        campanhaData,
                        campanhaHorario,
                        campanhaPublico,
                        campanhaDetalhes
                    )
                )
            }
            recyclerView.adapter?.notifyDataSetChanged()

        } else {
            arrayCampanhasEdit.clear()

            val campanhasCursor: Cursor =
                db!!.rawQuery("SELECT * FROM tab_campanha WHERE id_posto_campanha = $valorIdFiltro")
            val campanhasSize: Int = campanhasCursor.count
            Log.d("listLocais()", "locaisSize= $campanhasSize")

            while (campanhasCursor.moveToNext()) {
                val campanhaId = campanhasCursor.getInt(0)
                val campanhaDistrito = campanhasCursor.getString(1)
                val campanhaIdposto = campanhasCursor.getInt(2)
                val campanhaPostoNome = campanhasCursor.getString(3)
                val campanhaNome = campanhasCursor.getString(4)
                val campanhaDoenca = campanhasCursor.getString(5)
                val campanhaData = campanhasCursor.getString(6)
                val campanhaHorario = campanhasCursor.getString(7)
                val campanhaPublico = campanhasCursor.getString(8)
                val campanhaDetalhes = campanhasCursor.getString(9)

                Log.d(
                    "listaCampanhasEdit()",
                    "campanha_id= $campanhaId, distrito_campanha= $campanhaDistrito, id_posto_campanha= $campanhaIdposto, posto_nome_campanha= $campanhaPostoNome, nome_campanha= $campanhaNome, doenca_campanha=$campanhaDoenca, data= $campanhaData, horario= $campanhaHorario, publico_campanha= $campanhaPublico, detalhes= $campanhaDetalhes"
                )

                arrayCampanhasEdit.add(
                    CampanhasDataClass(
                        campanhaId,
                        campanhaDistrito,
                        campanhaIdposto,
                        campanhaPostoNome,
                        campanhaNome,
                        campanhaDoenca,
                        campanhaData,
                        campanhaHorario,
                        campanhaPublico,
                        campanhaDetalhes
                    )
                )
            }

            recyclerView.adapter?.notifyDataSetChanged()
        }

    }


}