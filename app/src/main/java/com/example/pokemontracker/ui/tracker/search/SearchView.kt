package com.example.pokemontracker.ui.tracker.search

interface SearchView {
    fun setListAdapter(adapter: PokemonAdapter)
    fun showDialog(options: Map<String, String>)
}