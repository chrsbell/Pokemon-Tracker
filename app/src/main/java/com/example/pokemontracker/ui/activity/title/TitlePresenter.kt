package com.example.pokemontracker.ui.activity.title

import com.example.pokemontracker.ui.activity.BasePresenter
import com.example.pokemontracker.ui.activity.tracker.TrackerActivity
import org.koin.core.annotation.Factory

@Factory
class TitlePresenter() : BasePresenter<TitleActivity>() {
    fun handleNavigate(shouldStart: Boolean) {
        if (shouldStart) view.launchActivity(TrackerActivity::class.java)
    }
}