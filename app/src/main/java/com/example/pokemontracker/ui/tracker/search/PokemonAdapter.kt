package com.example.pokemontracker.ui.tracker.search

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemontracker.repositories.PokemonData
import com.example.pokemontracker.utils.ImageProcessor

class PokemonAdapter (private val imageProcessor: ImageProcessor)
    : RecyclerView.Adapter<PokemonListItemViewHolder>() {

    private var allPokemon: List<PokemonData> = listOf()

    override fun getItemCount(): Int {
        return allPokemon.size
    }

    fun updateData(allPokemon: List<PokemonData>) {
        this.allPokemon = allPokemon
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: PokemonListItemViewHolder, position: Int) {
        holder.bind(allPokemon[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonListItemViewHolder {
       return PokemonListItemViewHolder.from(parent, imageProcessor)
    }
}