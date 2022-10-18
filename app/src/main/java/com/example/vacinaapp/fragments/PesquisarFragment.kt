package com.example.vacinaapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.vacinaapp.databinding.FragmentPesquisarBinding

class PesquisarFragment : Fragment() {
    private lateinit var binding: FragmentPesquisarBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentPesquisarBinding.inflate(inflater)
        return binding.root
    }

}