package com.example.vacinaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.vacinaapp.fragments.InicioFragment
import com.example.vacinaapp.fragments.LocaisFragment
import com.example.vacinaapp.fragments.PesquisarFragment
import com.example.vacinaapp.fragments.SettingsFragment

class MainActivity : AppCompatActivity() {

    private val inicioFragment = InicioFragment()
    private val locaisFragment = LocaisFragment()
    private val settingsFragment = SettingsFragment()
    private val pesquisarFragment = PesquisarFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(inicioFragment)

        bottom_navigation.setOnNavigationItemSelectedListener {
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        if(fragment != null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()
        }
    }
}