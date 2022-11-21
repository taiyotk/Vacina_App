package com.example.vacinaapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.vacinaapp.R
import kotlinx.android.synthetic.main.fragment_usuario.*


class UsuarioFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_usuario, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        modificar_dados.setOnClickListener {
            val userFragment = ModificarUsuarioFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, userFragment)
                .addToBackStack(null)
                .commit()
        }

    }

}