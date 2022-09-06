package com.example.vacinaapp.fragments

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.example.vacinaapp.R

class ConfigFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}