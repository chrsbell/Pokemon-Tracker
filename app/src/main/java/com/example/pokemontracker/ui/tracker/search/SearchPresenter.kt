package com.example.pokemontracker.ui.tracker.search

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import com.apollographql.apollo3.exception.ApolloException
import com.example.pokemontracker.database.Pokemon
import com.example.pokemontracker.repositories.PokemonRepository
import com.example.pokemontracker.ui.BasePresenter
import com.example.pokemontracker.utils.ImageProcessor
import com.example.pokemontracker.utils.MessageProvider
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
        this.setupListAdapter()
    }

    private fun setupListAdapter() {
        // lifecycle coroutine scope will cancel when fragment/activity destroyed
        val adapter = PokemonAdapter(PokemonAdapter.OnClickListener {
            messageProvider.showMessage("${it.name} tapped")
        }, imageProcessor)
        view?.setListAdapter(adapter)
        viewLifecycle?.coroutineScope?.launchWhenResumed {
            view?.context?.let { pokemonRepository.initDb(it) }
            val allPokemon = pokemonRepository.getAllPokemon()

            if (allPokemon != null) {
                adapter.updateData(allPokemon)
            } else {
                messageProvider.showError("Network error.")
            }
        }
    }
}