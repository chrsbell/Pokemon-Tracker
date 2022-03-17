package com.example.pokemontracker.ui.tracker.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemontracker.R
import com.example.pokemontracker.repositories.PokemonData
import com.example.pokemontracker.utils.ImageProcessor
import com.example.pokemontracker.utils.formatPokemonName
import com.example.pokemontracker.utils.formatPokemonNumber

class PokemonListItemViewHolder(itemView: View, private val imageProcessor: ImageProcessor)
    : RecyclerView.ViewHolder(itemView) {
    private val pokemonName: TextView = itemView.findViewById(R.id.pokemon_name)
    private val pokemonImg: ImageView = itemView.findViewById(R.id.pokemon_image)
    private val pokemonTypeOne: TextView = itemView.findViewById(R.id.pokemon_type_one)
    private val pokemonNumber: TextView = itemView.findViewById(R.id.pokemon_number)

    fun bind(item: PokemonData) {
        // bind sprite, etc
        pokemonName.text = item.name?.let { formatPokemonName(it) }
        pokemonTypeOne.text = item.typeOne
        pokemonNumber.text = item.num?.let { formatPokemonNumber(item.num) }
        item.frontSprite?.let { imageProcessor.loadImage(pokemonImg, it) }
        if (pokemonImg.drawable != null) {
            imageProcessor.setGradientAsync(itemView, pokemonImg.drawable.toBitmap())
        }
    }

    companion object {
        fun from(parent: ViewGroup, imageProcessor: ImageProcessor): PokemonListItemViewHolder {
            return PokemonListItemViewHolder(
                LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_pokemon_overview, parent, false),
                imageProcessor)
        }
    }
}