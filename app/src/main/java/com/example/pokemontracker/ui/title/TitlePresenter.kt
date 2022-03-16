package com.example.pokemontracker.ui.title

import com.example.pokemontracker.databinding.ActivityTitleBinding
import com.example.pokemontracker.ui.BasePresenter
import com.example.pokemontracker.ui.tracker.TrackerActivity

class TitlePresenter() : BasePresenter<TitleView>() {
    private lateinit var binding: ActivityTitleBinding

    fun handleNavigate(shouldStart: Boolean) {
        if (shouldStart) view?.launchActivity(TrackerActivity::class.java)
    }
}