package com.example.pokemontracker.ui.activity

import androidx.lifecycle.coroutineScope
import com.example.pokemontracker.database.Pokemon
import com.example.pokemontracker.repositories.PokemonRepository
import com.example.pokemontracker.ui.fragment.DetailFragment
import com.example.pokemontracker.ui.fragment.detail.DetailDialogFragment
import com.example.pokemontracker.ui.image.ImageProcessor

abstract class PokemonDetailPresenter<DialogPresenter: DetailFragment>
    (val pokemonRepository: PokemonRepository, imageProcessor: ImageProcessor
    ) : ImagePresenter<DialogPresenter>(imageProcessor) {
    fun createDetailDialog(item: Pokemon) {
        viewLifecycle.coroutineScope.launchWhenResumed {
            val pokemon = pokemonRepository.findByPokedexNumber(item.num)
            val options = mutableMapOf(
                DetailDialogFragment.BUNDLE_NUM_KEY to pokemon.num.toString(),
                DetailDialogFragment.BUNDLE_ENTRY_KEY to pokemon.entry,
                DetailDialogFragment.BUNDLE_TYPE_ONE_KEY to pokemon.typeOne,
                DetailDialogFragment.BUNDLE_IMAGE_KEY to pokemon.frontSprite,
                DetailDialogFragment.BUNDLE_FAVORITE_KEY to pokemon.isFavorite.toString()
            )
            if (pokemon.typeTwo != null) {
                options[DetailDialogFragment.BUNDLE_TYPE_TWO_KEY] = pokemon.typeTwo!!
            }
            view.showDialog(options)
        }
    }
}