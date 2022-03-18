package com.example.pokemontracker.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pokemontracker.R
import com.example.pokemontracker.repositories.PreferencesRepository
import org.koin.android.ext.android.inject
import timber.log.Timber

abstract class BaseActivity : AppCompatActivity() {
    private val preferencesRepository: PreferencesRepository by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // theme needs to be applied before set content view
        applyTheme()
    }

    private fun applyTheme() {
        if (preferencesRepository.isDarkTheme(this)) {
            setTheme(R.style.Theme_PokemonTracker_Dark)
            Timber.d("DARK")
        } else {
            setTheme(R.style.Theme_PokemonTracker_Light)
            Timber.d("LIGHT")
        }
    }
}