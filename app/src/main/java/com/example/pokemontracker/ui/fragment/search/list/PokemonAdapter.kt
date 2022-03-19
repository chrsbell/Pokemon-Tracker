package com.example.pokemontracker.ui.fragment.search.list

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemontracker.R
import com.example.pokemontracker.database.Pokemon
import com.example.pokemontracker.ui.image.ImageProcessor

class PokemonAdapter (private val onClickListener: OnClickListener, private val imageProcessor: ImageProcessor)
    : RecyclerView.Adapter<PokemonListItemViewHolder>() {

    private var allPokemon: List<Pokemon> = listOf()

    override fun getItemCount(): Int = allPokemon.size

    fun updateData(allPokemon: List<Pokemon>) {
        this.allPokemon = allPokemon
        notifyDataSetChanged()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: PokemonListItemViewHolder, position: Int) {
        val pokemon = allPokemon[position]
        holder.bind(pokemon)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(pokemon)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonListItemViewHolder =
        PokemonListItemViewHolder(
           LayoutInflater.from(parent.context)
               .inflate(R.layout.list_item_pokemon_overview, parent, false),
           imageProcessor)

    class OnClickListener(private val clickListener: (pokemon: Pokemon) -> Unit) {
        fun onClick(pokemon: Pokemon) = clickListener(pokemon)
    }
}