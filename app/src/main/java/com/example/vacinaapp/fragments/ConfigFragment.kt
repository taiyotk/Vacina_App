package com.example.vacinaapp.fragments

import android.os.Bundle
import androidx.preference.MultiSelectListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.vacinaapp.R

class ConfigFragment : PreferenceFragmentCompat(), Preference.OnPreferenceChangeListener {

    companion object{
        private const val distritosKey = "resposta_distritos"
    }

    //private lateinit var listDistrito: ListPreference
    private lateinit var listDistrito: MultiSelectListPreference

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        listDistrito = findPreference(distritosKey)!!

    }


    override fun onPreferenceChange(preference: Preference, newValue: Any?): Boolean {
        return true
    }

}