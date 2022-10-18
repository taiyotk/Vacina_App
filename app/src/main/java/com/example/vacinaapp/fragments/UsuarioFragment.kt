package com.example.vacinaapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.vacinaapp.R
import com.example.vacinaapp.databinding.FragmentUsuarioBinding


class UsuarioFragment : Fragment() {
    lateinit var binding: FragmentUsuarioBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_usuario, container, false)
    }


}