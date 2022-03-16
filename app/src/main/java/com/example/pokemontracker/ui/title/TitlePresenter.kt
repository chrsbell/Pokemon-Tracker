package com.example.pokemontracker.ui.title

import com.example.pokemontracker.databinding.ActivityTitleBinding
import com.example.pokemontracker.ui.BasePresenter
import com.example.pokemontracker.ui.tracker.TrackerActivity
import org.koin.core.annotation.Factory

@Factory
class TitlePresenter() : BasePresenter<TitleView>() {
    fun handleNavigate(shouldStart: Boolean) {
        if (shouldStart) view?.launchActivity(TrackerActivity::class.java)
    }
}