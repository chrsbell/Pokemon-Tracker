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
    private var allPokemon: List<Pokemon>? = listOf()

    override fun setView(view: SearchFragment, viewLifecycle: Lifecycle) {
        super.setView(view, viewLifecycle)
        view.setSnackbarView(messageProvider)
        imageProcessor.setMessageProvider(messageProvider)
    }

    fun start() {
        // lifecycle coroutine scope will cancel when fragment/activity destroyed
        val adapter = PokemonAdapter(PokemonAdapter.OnClickListener {
            messageProvider.showMessage("${it.name} tapped")
        }, imageProcessor)
        view?.setListAdapter(adapter)
        viewLifecycle?.coroutineScope?.launchWhenResumed {
            view?.context?.let { pokemonRepository.initDb(it) }
            allPokemon = try {
                pokemonRepository.getAllPokemon()
            } catch (e: ApolloException) {
                messageProvider.showError("Network error.")
                null
            }

            if (allPokemon != null) {
                adapter.updateData(allPokemon!!)
            }
        }
    }
}