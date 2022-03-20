package com.example.pokemontracker.ui.fragment.detail

import androidx.lifecycle.coroutineScope
import com.example.pokemontracker.repositories.PokemonRepository
import com.example.pokemontracker.ui.image.ImagePresenter
import com.example.pokemontracker.ui.image.ImageProcessor
import org.koin.core.annotation.Factory

@Factory
class DetailDialogPresenter(
    private val repository: PokemonRepository,
    imageProcessor: ImageProcessor
    ) : ImagePresenter<DetailDialogFragment>(imageProcessor) {

    fun onFavorite(pokemonIndex: Int) {
            viewLifecycle.coroutineScope.launchWhenResumed {
                // need to tell favorites and search fragment to update their pokemon data
                val pokemon = repository.findByPokedexNumber(pokemonIndex)
                pokemon.isFavorite = !pokemon.isFavorite
                repository.updatePokemon(pokemon)
                view.updateFavoriteIcon(pokemon.isFavorite)
            }
        }
}