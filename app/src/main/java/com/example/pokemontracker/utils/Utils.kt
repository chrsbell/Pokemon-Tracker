package com.example.pokemontracker.utils

import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import java.util.*

fun formatPokemonName(name: String): String {
    return name.replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
    }
}

fun formatPokemonNumber(num: Int): String = "#${num.toString().padStart(3,'0')}"

val pokemonColorTypes = mapOf<String, Int>(
    "Normal" to Color.rgb(169,169,120),
    "Fire" to Color.rgb(240,129,49),
    "Water" to Color.rgb(105,145,240),
    "Grass" to Color.rgb(120,201,78),
    "Electric" to Color.rgb(248,208,48),
    "Ice" to Color.rgb(152,217,217),
    "Fighting" to Color.rgb(192,48,40),
    "Poison" to Color.rgb(160,64,160),
    "Ground" to Color.rgb(224,193,105),
    "Flying" to Color.rgb(169,144,241),
    "Psychic" to Color.rgb(248,88,135),
    "Bug" to Color.rgb(168,185,32),
    "Rock" to Color.rgb(185,161,56),
    "Ghost" to Color.rgb(113,89,152),
    "Dark" to Color.rgb(112,88,72),
    "Dragon" to Color.rgb(113,56,249),
    "Steel" to Color.rgb(184,185,209),
    "Fairy" to Color.rgb(235,178,184)
)

@RequiresApi(Build.VERSION_CODES.N)
fun getPokemonTypeColor(type: String): Int = pokemonColorTypes.getOrDefault(type, Color.rgb(169,169,120))