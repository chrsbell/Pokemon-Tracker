package com.example.pokemontracker.ui.fragment.favorites

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import com.example.pokemontracker.repositories.PokemonRepository
import com.example.pokemontracker.ui.fragment.detail.PokemonDetailPresenter
import com.example.pokemontracker.ui.image.ImageProcessor
import org.koin.core.annotation.Factory

@Factory
class FavoritesPresenter(pokemonRepository: PokemonRepository,
                         imageProcessor: ImageProcessor
                         ) :
    PokemonDetailPresenter<FavoritesFragment>(pokemonRepository, imageProcessor) {
    override fun start(view: FavoritesFragment, viewLifecycle: Lifecycle) {
        super.start(view, viewLifecycle)
        setupGrid()
    }

    fun setupGrid() {
        viewLifecycle.coroutineScope.launchWhenResumed {
            val favorites = pokemonRepository.getAllFavorites()
            view.addImagesToGrid(favorites.map { it.frontSprite }, favorites.map {it.num})
        }
    }

    fun onCellClick(num: Int) {
        viewLifecycle.coroutineScope.launchWhenResumed {
            val pokemon = pokemonRepository.findByPokedexNumber(num)
            createDetailDialog(pokemon)
        }
    }
}