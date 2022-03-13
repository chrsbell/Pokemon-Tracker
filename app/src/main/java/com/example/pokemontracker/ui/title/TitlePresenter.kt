package com.example.pokemontracker.ui.title

import com.example.pokemontracker.ui.BasePresenter

class TitlePresenter(private val view: TitleActivity) : BasePresenter {
    init {
        view.supportActionBar?.hide()
    }
}