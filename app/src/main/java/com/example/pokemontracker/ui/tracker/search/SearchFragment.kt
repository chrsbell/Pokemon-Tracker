package com.example.pokemontracker.ui.tracker.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.pokemontracker.R
import com.example.pokemontracker.databinding.FragmentSearchBinding
import com.example.pokemontracker.utils.MessageProvider
import org.koin.android.ext.android.inject

class SearchFragment : SearchView, Fragment() {
    private val presenter: SearchPresenter by inject()
    private lateinit var binding: FragmentSearchBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container,
            false)
        presenter.setView(this, lifecycle)
        presenter.start()
        return binding.root
    }

    override fun setListAdapter(adapter: PokemonAdapter) {
        binding.searchListing.adapter = adapter
    }

    fun setSnackbarView(messageProvider: MessageProvider) {
        messageProvider.setView(binding.root, lifecycle)
    }
    // Need to handle screen orientation change in onSaveInstanceState
}