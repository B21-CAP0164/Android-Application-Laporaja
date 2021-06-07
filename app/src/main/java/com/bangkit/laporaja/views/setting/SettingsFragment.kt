package com.bangkit.laporaja.views.setting

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.preference.*
import com.bangkit.laporaja.MainActivity
import com.bangkit.laporaja.R

class SettingsFragment : PreferenceFragmentCompat() {
    private lateinit var currentActivity: MainActivity

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        currentActivity = activity as MainActivity
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
        startActivity(mIntent)

    }
}
