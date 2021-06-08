package com.bangkit.laporaja.views.settings

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.bangkit.laporaja.R

class SettingsFragment : PreferenceFragmentCompat() {
    private var languageChange: Preference? = null

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        languageChange = findPreference("select_language")

        val languageIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
        languageChange?.intent = languageIntent
    }
}