package com.example.pokemontracker.repositories

import android.content.Context
import android.view.MenuItem
import com.example.pokemontracker.ui.activity.BaseActivity
import org.koin.core.annotation.Single

@Single
class PreferencesRepository {
    companion object {
        const val FILE_NAME = "theme"
        const val THEME_KEY = "isDark"
        const val DARK_THEME = "Dark Theme"
    }

    fun isDarkTheme(view: BaseActivity): Boolean {
        val preferences = view.getSharedPreferences(
            FILE_NAME,
            Context.MODE_PRIVATE)
        return preferences.getBoolean(THEME_KEY, false)
    }

    fun setTheme(view: BaseActivity, item: MenuItem) {
        val preferencesEditor = view
            .getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).edit()
        preferencesEditor
            .putBoolean(THEME_KEY, item.title.toString() == DARK_THEME)
            .apply()
    }
}