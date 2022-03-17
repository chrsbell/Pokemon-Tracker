package com.example.pokemontracker.utils

import java.util.*

fun formatPokemonName(name: String): String {
    return name.replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
    }
}

fun formatPokemonNumber(num: Int): String = "#${num.toString().padStart(3,'0')}"