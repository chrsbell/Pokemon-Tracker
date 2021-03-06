package com.example.pokemontracker.ui.activity.tracker

import android.view.MenuItem
import androidx.lifecycle.Lifecycle
import com.example.pokemontracker.repositories.PreferencesRepository
import com.example.pokemontracker.ui.activity.BasePresenter
import com.example.pokemontracker.ui.snackbar.MessageProvider
import org.koin.core.annotation.Factory

@Factory
class TrackerPresenter(private val preferencesRepository: PreferencesRepository,
                       private val messageProvider: MessageProvider
)
    : BasePresenter<TrackerActivity>() {

    var themeOnCreate: Boolean = false

    fun setThemeOnCreate(trackerActivity: TrackerActivity) {
        themeOnCreate = preferencesRepository.isDarkTheme(trackerActivity)
    }

    override fun start(view: TrackerActivity, viewLifecycle: Lifecycle) {
        super.start(view, viewLifecycle)
        view.setSnackbarView(messageProvider)
    }

    fun onThemeSelect(item: MenuItem) {
        // this is the only activity whose descendants will use theming
        preferencesRepository.setTheme(view, item)
        view.restart()
    }

    fun checkIfNewTheme(lastTheme: Boolean) {
        if (themeOnCreate != lastTheme) {
            messageProvider.showMessage("Changed theme.")
        }
    }
}