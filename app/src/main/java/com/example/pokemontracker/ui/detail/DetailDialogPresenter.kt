package com.example.pokemontracker.ui.detail

import android.graphics.Bitmap
import android.view.View
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
        fun loadImage(
            imageView: ImageView,
            imageUrl: String,
            resourceReadyCallback: ImageProcessor.ResourceReadyCallback
        ) {
            imageProcessor.loadImage(imageView, imageUrl, resourceReadyCallback)
        }

    fun onFavorite(pokemonIndex: Int) {
            viewLifecycle.coroutineScope.launchWhenResumed {
                val pokemon = repository.findByPokedexNumber(pokemonIndex)
                pokemon.isFavorite = !pokemon.isFavorite
                repository.updatePokemon(pokemon)
                view.updateFavoriteIcon(pokemon.isFavorite)
            }
        }

    fun getBackgroundColor(view: View, bitmap: Bitmap,
                           paletteReadyCallback: ImageProcessor.PaletteReadyCallback) =
        imageProcessor.getColorPalette(view, bitmap, paletteReadyCallback)
}