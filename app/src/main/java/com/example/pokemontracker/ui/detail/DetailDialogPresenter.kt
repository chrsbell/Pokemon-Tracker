package com.example.pokemontracker.ui.detail

import android.widget.ImageView
import com.example.pokemontracker.repositories.PokemonRepository
import com.example.pokemontracker.ui.BasePresenter
import com.example.pokemontracker.utils.ImageProcessor
import org.koin.core.annotation.Factory

@Factory
class DetailDialogPresenter(
    private val imageProcessor: ImageProcessor
    ) : BasePresenter<DetailDialogFragment>() {
        fun loadImage(imageView: ImageView, imageUrl: String) {
            imageProcessor.loadImage(imageView, imageUrl)
        }
    }