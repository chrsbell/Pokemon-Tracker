package com.example.pokemontracker.ui.tracker.search

import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemontracker.R
import com.example.pokemontracker.database.Pokemon
import com.example.pokemontracker.utils.ImageProcessor
import com.example.pokemontracker.utils.formatPokemonName
import com.example.pokemontracker.utils.formatPokemonNumber

class PokemonListItemViewHolder(itemView: View, private val imageProcessor: ImageProcessor)
    : RecyclerView.ViewHolder(itemView) {
    // look into data binding here
    private val pokemonName: TextView = itemView.findViewById(R.id.pokemon_overview_name)
    private val pokemonImg: ImageView = itemView.findViewById(R.id.pokemon_overview_image)
    private val pokemonTypeOne: TextView = itemView.findViewById(R.id.pokemon_overview_type_text_one)
    private val pokemonTypeOneCard: CardView = itemView.findViewById(R.id.pokemon_overview_type_card_one)
    private val pokemonTypeTwo: TextView = itemView.findViewById(R.id.pokemon_overview_type_text_two)
    private val pokemonTypeTwoCard: CardView = itemView.findViewById(R.id.pokemon_overview_type_card_two)
    private val pokemonNumber: TextView = itemView.findViewById(R.id.pokemon_overview_number)

    private var animateUp = true;
    // look into alternative for this
    private var handler = Handler()
    private var defaultAnimationTime = 1000L
    private val yAnimationDistance = 25;

    // Animate a pokemon sprite using the pokemon weight (move into separate class)
    private fun animateSprite(pokemonData: Pokemon, imageView: ImageView) {
        var animationTime = pokemonData.weight.toLong().div(3);
        if (animationTime == 0L) {
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

    fun bind(item: Pokemon) {
        // bind sprite, etc
        pokemonName.text = item.name.let { formatPokemonName(it) }
        pokemonTypeOne.text = item.typeOne
        if (item.typeTwo != null) {
            // make sure both type cards are centered
            pokemonTypeTwo.text = item.typeTwo
            pokemonTypeTwoCard.visibility = View.VISIBLE
            val constraintLayout = pokemonTypeOneCard.layoutParams as ConstraintLayout.LayoutParams
            constraintLayout.endToEnd = pokemonNumber.id
            constraintLayout.endToStart = pokemonTypeTwoCard.id
            pokemonTypeOneCard.requestLayout()
        } else {
            // center type card if there is only one type
            pokemonTypeTwoCard.visibility = View.INVISIBLE
            val constraintLayout = pokemonTypeOneCard.layoutParams as ConstraintLayout.LayoutParams
            constraintLayout.endToEnd = pokemonNumber.id
            constraintLayout.endToStart = ConstraintLayout.LayoutParams.UNSET
            pokemonTypeOneCard.requestLayout()
        }
        pokemonNumber.text = formatPokemonNumber(item.num)
        imageProcessor.loadImage(pokemonImg, item.frontSprite, itemView)

        // remove all pre-existing callbacks
        handler.removeCallbacksAndMessages(null)
        animateSprite(item, pokemonImg)
    }
}