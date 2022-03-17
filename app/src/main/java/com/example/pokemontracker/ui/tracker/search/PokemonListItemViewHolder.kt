package com.example.pokemontracker.ui.tracker.search

import android.opengl.Visibility
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemontracker.R
import com.example.pokemontracker.repositories.PokemonData
import com.example.pokemontracker.utils.ImageProcessor
import com.example.pokemontracker.utils.formatPokemonName
import com.example.pokemontracker.utils.formatPokemonNumber
import kotlin.random.Random

class PokemonListItemViewHolder(itemView: View, private val imageProcessor: ImageProcessor)
    : RecyclerView.ViewHolder(itemView) {
    private val pokemonName: TextView = itemView.findViewById(R.id.pokemon_name)
    private val pokemonImg: ImageView = itemView.findViewById(R.id.pokemon_image)
    private val pokemonTypeOne: TextView = itemView.findViewById(R.id.pokemon_type_one)
    private val pokemonTypeOneCard: CardView = itemView.findViewById(R.id.type_info_one)
    private val pokemonTypeTwo: TextView = itemView.findViewById(R.id.pokemon_type_two)
    private val pokemonTypeTwoCard: CardView = itemView.findViewById(R.id.type_info_two)
    private val pokemonNumber: TextView = itemView.findViewById(R.id.pokemon_number)

    private var animateUp = true;
    private var handler = Handler()
    private var defaultAnimationTime = 1000L
    private val yAnimationDistance = 25;

    private fun animateSprite(pokemonData: PokemonData, imageView: ImageView) {
        var animationTime = pokemonData.weight?.toLong()?.div(3);
        if (animationTime == null || animationTime == 0L) {
            animationTime = defaultAnimationTime
        }
        handler.postDelayed({
            if (animateUp) {
                imageView.y -= yAnimationDistance;
                animateUp = false
            } else {
                imageView.y += yAnimationDistance;
                animateUp = true
            }
            handler.postDelayed({
                animateSprite(pokemonData, imageView)
            }, animationTime)
        }, animationTime)
    }

    fun bind(item: PokemonData) {
        // bind sprite, etc
        pokemonName.text = item.name?.let { formatPokemonName(it) }
        pokemonTypeOne.text = item.typeOne
        if (item.typeTwo != null) {
            pokemonTypeTwo.text = item.typeTwo
        } else {
            // center type card if there is only one
            pokemonTypeTwoCard.visibility = View.INVISIBLE
            val constraintLayout = pokemonTypeOneCard.layoutParams as ConstraintLayout.LayoutParams
            constraintLayout.endToEnd = pokemonNumber.id
            constraintLayout.endToStart = ConstraintLayout.LayoutParams.UNSET
            pokemonTypeOneCard.requestLayout()
        }
        pokemonNumber.text = item.num?.let { formatPokemonNumber(item.num) }
        item.frontSprite?.let { imageProcessor.loadImage(pokemonImg, it, itemView) }
        // remove all pre-existing callbacks
        handler.removeCallbacksAndMessages(null)
        animateSprite(item, pokemonImg)
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