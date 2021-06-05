package com.bangkit.laporaja.views.setting

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.bangkit.laporaja.MainActivity
import com.bangkit.laporaja.R

class SettingsFragment : PreferenceFragmentCompat() {
    private lateinit var currentActivity: MainActivity

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        currentActivity = activity as MainActivity

        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        currentActivity.removeBottomBar()
    }
}