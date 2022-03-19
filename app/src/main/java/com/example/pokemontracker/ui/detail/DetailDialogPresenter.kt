package com.example.pokemontracker.ui.detail

import android.widget.ImageView
import androidx.lifecycle.coroutineScope
import com.example.pokemontracker.repositories.PokemonRepository
import com.example.pokemontracker.ui.BasePresenter
import com.example.pokemontracker.utils.ImageProcessor
import org.koin.core.annotation.Factory

@Factory
class DetailDialogPresenter(
    private val imageProcessor: ImageProcessor,
    private val repository: PokemonRepository
    ) : BasePresenter<DetailDialogFragment>() {
        fun loadImage(imageView: ImageView, imageUrl: String) {
            imageProcessor.loadImage(imageView, imageUrl)
        }

    fun onFavorite(pokemonIndex: Int) {
            viewLifecycle.coroutineScope.launchWhenResumed {
                val pokemon = repository.findByPokedexNumber(pokemonIndex)
                pokemon.isFavorite = !pokemon.isFavorite
                repository.updatePokemon(pokemon)
                view.updateFavoriteIcon(pokemon.isFavorite)
            }
        }
}