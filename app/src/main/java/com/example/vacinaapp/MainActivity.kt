package com.example.vacinaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.vacinaapp.fragments.InicioFragment
import com.example.vacinaapp.fragments.LocaisFragment
import com.example.vacinaapp.fragments.PesquisarFragment
import com.example.vacinaapp.fragments.SettingsFragment
import kotlinx.android.synthetic.main.activity_main.*

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
            when(it.itemId){
                R.id.ic_home -> replaceFragment(inicioFragment)
                R.id.ic_local -> replaceFragment(locaisFragment)
                R.id.ic_syringe -> replaceFragment(pesquisarFragment)
                R.id.ic_config -> replaceFragment(settingsFragment)
            }
            true
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