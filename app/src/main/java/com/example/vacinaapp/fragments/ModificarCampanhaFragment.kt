package com.example.vacinaapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.vacinaapp.R
import com.example.vacinaapp.databinding.FragmentLocal2Binding
import com.example.vacinaapp.databinding.FragmentModificarCampanhaBinding

class ModificarCampanhaFragment : Fragment() {
    private lateinit var binding: FragmentModificarCampanhaBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentModificarCampanhaBinding.inflate(inflater)
        return binding.root
    }

}