package com.bangkit.laporaja.views.setting

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.preference.*
import com.bangkit.laporaja.MainActivity
import com.bangkit.laporaja.R

class SettingsFragment : PreferenceFragmentCompat() {
    private lateinit var currentActivity: MainActivity

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        currentActivity = activity as MainActivity
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        changeLanguage()
    }

    private fun changeLanguage(){
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(currentActivity)
        val language = sharedPreferences.getString("language","ID")
        Log.d("League", language.toString())
        Toast.makeText(currentActivity,language,Toast.LENGTH_SHORT).show()
        if(language=="ID"){
            Toast.makeText(currentActivity,"English",Toast.LENGTH_SHORT).show()
            currentActivity.language("id")
        }else if(language=="US"){
            Toast.makeText(currentActivity,"Indonesia",Toast.LENGTH_SHORT).show()
            currentActivity.language("en")
        }
    }

    override fun onResume() {
        changeLanguage()
        super.onResume()
    }
}
