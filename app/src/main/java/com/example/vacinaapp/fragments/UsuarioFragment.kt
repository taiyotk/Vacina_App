package com.example.vacinaapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.vacinaapp.databinding.FragmentUsuarioBinding


class UsuarioFragment : Fragment() {
    lateinit var binding: FragmentUsuarioBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUsuarioBinding.inflate(inflater)
        return binding.root
    }

}