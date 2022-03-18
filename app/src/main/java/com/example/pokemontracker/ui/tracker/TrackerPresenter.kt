package com.example.pokemontracker.ui.tracker

import android.view.MenuItem
import com.example.pokemontracker.repositories.PreferencesRepository
import com.example.pokemontracker.ui.BasePresenter
import org.koin.core.annotation.Factory

@Factory
class TrackerPresenter(private val preferencesRepository: PreferencesRepository)
    : BasePresenter<TrackerActivity>() {

    fun onThemeSelect(item: MenuItem) {
        // this is the only activity whose descendants will use theming
        view?.let { preferencesRepository.setTheme(it, item) }
        view?.restart()
    }
}