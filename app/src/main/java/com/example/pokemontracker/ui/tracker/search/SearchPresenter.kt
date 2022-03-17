package com.example.pokemontracker.ui.tracker.search

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import com.apollographql.apollo3.exception.ApolloException
import com.example.pokemontracker.repositories.PokemonData
import com.example.pokemontracker.repositories.PokemonRepository
import com.example.pokemontracker.ui.BasePresenter
import com.example.pokemontracker.utils.ImageProcessor
import com.example.pokemontracker.utils.MessageProvider
import org.koin.core.annotation.Factory
import timber.log.Timber

@Factory
class SearchPresenter(private val pokemonRepository: PokemonRepository,
                      private val imageProcessor: ImageProcessor,
                      private val messageProvider: MessageProvider
)
    : BasePresenter<SearchFragment>() {
    private var allPokemon: List<PokemonData>? = listOf()

    override fun setView(view: SearchFragment, viewLifecycle: Lifecycle) {
        super.setView(view, viewLifecycle)
        view.setSnackbarView(messageProvider)
        imageProcessor.setMessageProvider(messageProvider)
    }

    fun start() {
    // lifecycle coroutine scope will cancel when fragment/activity destroyed
    val adapter = PokemonAdapter(imageProcessor)
    view?.setListAdapter(adapter)
    viewLifecycle?.coroutineScope?.launchWhenResumed {
        val response = try {
            pokemonRepository.getAllPokemon()
        } catch (e: ApolloException) {
            Timber.d("Failed to fetch pokemon.", e) // show an error toast here/image
            null
        }

        allPokemon = response?.data?.allPokemon?.filterNotNull()
            ?.map { info ->
                PokemonData(
                    name = info.name,
                    num = info.nat_dex_num,
                    generation = info.generation,
                    typeOne = info.types?.get(0)?.name,
                    typeTwo = info.types?.get(0)?.name,
                    frontSprite = imageProcessor.getUri(info.sprites?.front_default),
                    backSprite = imageProcessor.getUri(info.sprites?.back_default)
                )
        }
        if (allPokemon != null) {
            adapter.updateData(allPokemon!!)
        }
    }
}
}