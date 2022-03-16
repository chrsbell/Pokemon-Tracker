package com.example.pokemontracker.ui.tracker.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.apollographql.apollo3.exception.ApolloException
import com.example.pokemontracker.R
import com.example.pokemontracker.databinding.FragmentSearchBinding
import com.example.pokemontracker.repositories.PokemonRepository
import org.koin.android.ext.android.inject
import timber.log.Timber

class SearchFragment : Fragment() {
    private val pokemonRepository: PokemonRepository by inject()
    private lateinit var binding: FragmentSearchBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)

        setupListAdapter()

        return binding.root
    }

    // Need to handle screen orientation change in onSaveInstanceState

    private fun setupListAdapter() {
        // lifecycle coroutine scope will cancel when fragment/activity destroyed
        lifecycleScope.launchWhenResumed {
            val response = try {
                pokemonRepository.getAllPokemon()
            } catch (e: ApolloException) {
                Timber.d("Failed to fetch pokemon.", e) // show an error toast here/image
                null
            }

            val pokemon = response?.data?.allPokemon?.filterNotNull()
            if (pokemon != null && !response.hasErrors()) {
                val adapter = PokemonAdapter(pokemon)
//                binding.searchListing.layoutManager = LinearLayoutManager(requireContext())
                binding.searchListing.adapter = adapter
            }
        }
    }
}