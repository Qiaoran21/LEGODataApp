package com.example.legodataapp

import android.content.Context

class LoadSettings {
    companion object{
        private const val DARK_MODE_PREF_KEY = "dark_mode_preference"
        private const val SOUND_EFFECTS_PREF_KEY = "sound_effects_preference"
        private const val USER_ID_PREF_KEY = "user_id_preference"
    }

    fun loadDarkModeState(context: Context): Boolean{
        val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(DARK_MODE_PREF_KEY, false)
    }

    fun loadSoundEffectsState(context: Context): Boolean{
        val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(SOUND_EFFECTS_PREF_KEY, false)
    }

    fun loadUserState(context: Context): String?{
        val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString(USER_ID_PREF_KEY, "")
    }
}