package com.example.pokemontracker.ui.tracker.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemontracker.R
import com.example.pokemontracker.client.PokemonQuery
import com.example.pokemontracker.ui.tracker.search.PokemonAdapter.ViewHolder.Companion.from

class PokemonAdapter (private val allPokemon: List<PokemonQuery.AllPokemon>) : RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {
    override fun getItemCount(): Int {
        return allPokemon.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pokemon = allPokemon[position]
        holder.bind(pokemon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return from(parent)
    }

    class ViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val pokemonName: TextView = itemView.findViewById(R.id.pokemon_name)

        fun bind(item: PokemonQuery.AllPokemon) {
            // bind sprite, etc
            pokemonName.text = item.name
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater
                    .inflate(R.layout.list_item_pokemon_overview, parent, false)

                return ViewHolder(view)
            }
        }
    }

}