package com.example.vacinaapp.fragments

import android.os.Bundle
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.example.vacinaapp.R

class ConfigFragment : PreferenceFragmentCompat(), Preference.OnPreferenceChangeListener {

    companion object{
        private const val distritosKey = "resposta_distritos"
    }

    private lateinit var listDistrito: ListPreference

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        listDistrito = findPreference(distritosKey)!!
        fillSummary(listDistrito)

    }

    private fun fillSummary(preference: Preference){
        preference.onPreferenceChangeListener = this
        val pref = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val value = pref.getString(preference.key, "")
        onPreferenceChange(preference, value)

    }


    override fun onPreferenceChange(preference: Preference, newValue: Any?): Boolean {
        val stringValue = newValue.toString()

        if(preference == listDistrito){
            val index = listDistrito.findIndexOfValue(stringValue)
            if(index >= 0){
                listDistrito.summary = listDistrito.entries[index]
            }
        }
        return true
    }
}