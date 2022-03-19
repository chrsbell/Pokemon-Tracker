package com.example.pokemontracker.ui.fragment.search

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import com.example.pokemontracker.repositories.PokemonRepository
import com.example.pokemontracker.ui.activity.PokemonDetailPresenter
import com.example.pokemontracker.ui.fragment.search.list.PokemonAdapter
import com.example.pokemontracker.ui.image.ImageProcessor
import com.example.pokemontracker.ui.snackbar.MessageProvider
import org.koin.core.annotation.Factory

@Factory
class SearchPresenter(
    pokemonRepository: PokemonRepository,
    imageProcessor: ImageProcessor,
    private val messageProvider: MessageProvider
) : PokemonDetailPresenter<SearchFragment>(pokemonRepository, imageProcessor) {

    override fun start(view: SearchFragment, viewLifecycle: Lifecycle) {
        super.start(view, viewLifecycle)
        view.setSnackbarView(messageProvider)
        imageProcessor.setMessageProvider(messageProvider)
        setupListAdapter()
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