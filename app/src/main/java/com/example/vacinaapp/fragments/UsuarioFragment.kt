package com.example.vacinaapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.FragmentTransaction

import com.example.vacinaapp.R
import com.example.vacinaapp.databinding.FragmentUsuarioBinding


class UsuarioFragment : Fragment() {
    lateinit var binding: FragmentUsuarioBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val inflar = inflater.inflate(R.layout.fragment_usuario, container, false)

        val botaoentrar = inflar.findViewById<Button>(R.id.botao_entrar)
        botaoentrar.setOnClickListener {
            val inicioFragment = InicioFragment()

            val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, inicioFragment)

            transaction.commit()
        }

        return inflar
    }


}