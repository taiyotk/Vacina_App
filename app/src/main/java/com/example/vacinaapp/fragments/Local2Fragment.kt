package com.example.vacinaapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.vacinaapp.databinding.FragmentLocal2Binding

class Local2Fragment : Fragment() {
    private lateinit var binding: FragmentLocal2Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLocal2Binding.inflate(inflater)
        return binding.root
    }


}