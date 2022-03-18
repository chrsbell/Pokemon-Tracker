package com.example.pokemontracker.ui.tracker.search

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import com.example.pokemontracker.database.Pokemon
import com.example.pokemontracker.repositories.PokemonRepository
import com.example.pokemontracker.ui.BasePresenter
import com.example.pokemontracker.ui.detail.DetailDialogFragment
import com.example.pokemontracker.utils.ImageProcessor
import com.example.pokemontracker.ui.snackbar.MessageProvider
import org.koin.core.annotation.Factory

@Factory
class SearchPresenter(
    private val pokemonRepository: PokemonRepository,
    private val imageProcessor: ImageProcessor,
    private val messageProvider: MessageProvider
) : BasePresenter<SearchFragment>() {

    override fun start(view: SearchFragment, viewLifecycle: Lifecycle) {
        super.start(view, viewLifecycle)
        view.setSnackbarView(messageProvider)
        imageProcessor.setMessageProvider(messageProvider)
        setupListAdapter()
    }

    private fun createDetailDialog(item: Pokemon) {
        viewLifecycle.coroutineScope.launchWhenResumed {
            val pokemon = pokemonRepository.findByPokedexNumber(item.num)
            val options = mutableMapOf(
                DetailDialogFragment.BUNDLE_NUM_KEY to pokemon.num.toString(),
                DetailDialogFragment.BUNDLE_ENTRY_KEY to pokemon.entry,
                DetailDialogFragment.BUNDLE_TYPE_ONE_KEY to pokemon.typeOne,
                DetailDialogFragment.BUNDLE_IMAGE_KEY to pokemon.frontSprite
            )
            if (pokemon.typeTwo != null) {
                options[DetailDialogFragment.BUNDLE_TYPE_TWO_KEY] = pokemon.typeTwo!!
            }
            view.showDialog(options)
        }
    }

    private fun setupListAdapter() {
        // lifecycle coroutine scope will cancel when fragment/activity destroyed
        val adapter = PokemonAdapter(PokemonAdapter.OnClickListener {
            createDetailDialog(it)
        }, imageProcessor)

        view.setListAdapter(adapter)
        viewLifecycle.coroutineScope.launchWhenResumed {
            view.context?.let { pokemonRepository.initDb(it) }
            val allPokemon = pokemonRepository.getAllPokemon()

            if (allPokemon != null) {
                adapter.updateData(allPokemon)
            } else {
                messageProvider.showError("Network error.")
            }
        }
    }
}