package com.example.pokemontracker.ui.fragment.detail

import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.coroutineScope
import com.example.pokemontracker.repositories.PokemonRepository
import com.example.pokemontracker.ui.activity.BasePresenter
import com.example.pokemontracker.ui.activity.ImagePresenter
import com.example.pokemontracker.ui.image.ImageProcessor
import com.example.pokemontracker.ui.image.PaletteReadyCallback
import com.example.pokemontracker.ui.image.ResourceReadyCallback
import org.koin.core.annotation.Factory

@Factory
class DetailDialogPresenter(
    private val repository: PokemonRepository,
    imageProcessor: ImageProcessor
    ) : ImagePresenter<DetailDialogFragment>(imageProcessor) {

    fun onFavorite(pokemonIndex: Int) {
            viewLifecycle.coroutineScope.launchWhenResumed {
                val pokemon = repository.findByPokedexNumber(pokemonIndex)
                pokemon.isFavorite = !pokemon.isFavorite
                repository.updatePokemon(pokemon)
                view.updateFavoriteIcon(pokemon.isFavorite)
            }
        }
}