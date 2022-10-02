package com.example.vacinaapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.vacinaapp.R

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class LocalDetalhesFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    //o id do textview do titulo é nome_posto_titulo
    //id do textview do endereco é textview_endereco
    //id do textview do endereco é textview_telefone
    //id do textview dos horarios: segunda_label, terca_label ...
    //id da textview da tabela de vacinas no xml: doenca_texview, disp_textview, ver_textview

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
        return inflater.inflate(R.layout.fragment_local_detalhes, container, false)
    }




}