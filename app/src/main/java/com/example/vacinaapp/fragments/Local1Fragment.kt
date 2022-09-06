package com.example.vacinaapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.vacinaapp.R
import com.example.vacinaapp.databinding.FragmentLocal1Binding

class Local1Fragment : Fragment() {
    lateinit var binding: FragmentLocal1Binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLocal1Binding.inflate(inflater)
        return binding.root
    }

}