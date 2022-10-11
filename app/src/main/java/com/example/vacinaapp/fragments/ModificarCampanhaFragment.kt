package com.example.vacinaapp.fragments

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.example.vacinaapp.R
import com.example.vacinaapp.databinding.FragmentLocal2Binding
import com.example.vacinaapp.databinding.FragmentModificarCampanhaBinding
import kotlinx.android.synthetic.main.fragment_modificar_campanha.*

class ModificarCampanhaFragment : Fragment() {

    private lateinit var db: SQLiteDatabase
    private lateinit var distritoSpinner: Spinner
    private lateinit var postoSpinner: Spinner
    private lateinit var distrito_array: Array<String>
    private lateinit var distritoArrayAdapter: ArrayAdapter<CharSequence>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_modificar_campanha, container, false)

        return view
    }
    //id do botao de adicionar vacina button_adic_campanha

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        distrito_array = arrayOf("Todos os distritos", "Galena", "Ponte Firme", "Presidente Olegário", "Santiago de Minas")
        val postoArray: Array<String> = resources.getStringArray(R.array.postos_array)
        postoSpinner = view.findViewById(R.id.spinner_postos)
        //distritoArrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, distrito_array)

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.postos_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner_postos.adapter = adapter
        }

        var valorIdfiltro: Int
        postoSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val indexValue: Int = postoSpinner.selectedItemPosition

                when(indexValue){
                    0 -> {
                        valorIdfiltro = 1

                    }
                    1 -> {

                    }
                }
                //Toast.makeText(context, "Item selecionado: "+ postoArray[position], Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

    }


}