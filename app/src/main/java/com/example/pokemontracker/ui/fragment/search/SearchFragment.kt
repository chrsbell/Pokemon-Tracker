package com.example.pokemontracker.ui.fragment.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.pokemontracker.R
import com.example.pokemontracker.databinding.FragmentSearchBinding
import com.example.pokemontracker.ui.fragment.detail.DetailFragment
import com.example.pokemontracker.ui.fragment.search.list.PokemonAdapter
import com.example.pokemontracker.ui.snackbar.MessageView
import com.example.pokemontracker.ui.snackbar.MessageProvider
import org.koin.android.ext.android.inject

class SearchFragment : MessageView, DetailFragment() {
    private val presenter: SearchPresenter by inject()
    private lateinit var binding: FragmentSearchBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container,
            false)
        presenter.start(this, lifecycle)
        return binding.root
    }

    fun setListAdapter(adapter: PokemonAdapter) {
        binding.searchListing.adapter = adapter
    }

    override fun setSnackbarView(messageProvider: MessageProvider) {
        messageProvider.setView(binding.root, lifecycle)
    }
}