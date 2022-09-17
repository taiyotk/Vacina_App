package com.example.vacinaapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.FragmentTransaction
import com.example.vacinaapp.R

class LoginFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflar = inflater.inflate(R.layout.fragment_login, container, false)

        val botaoentrar = inflar.findViewById<Button>(R.id.botao_entrar)
        botaoentrar.setOnClickListener {
            val inicioFragment = InicioFragment()

            val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, inicioFragment)

            transaction.commit()
        }

        return inflar
    }

    companion object {

    }
}