package com.example.pokemontracker.ui.tracker.search

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemontracker.repositories.PokemonData
import com.example.pokemontracker.utils.ImageProcessor

class PokemonAdapter (private val onClickListener: OnClickListener, private val imageProcessor: ImageProcessor)
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
        val pokemon = allPokemon[position]
        holder.bind(pokemon)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(pokemon)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonListItemViewHolder {
       return PokemonListItemViewHolder.from(parent, imageProcessor)
    }

    class OnClickListener(private val clickListener: (pokemon: PokemonData) -> Unit) {
        fun onClick(pokemon: PokemonData) = clickListener(pokemon)
    }
}