package com.example.pokemontracker.repositories

import android.net.Uri

data class PokemonData (
    val name: String?,
    val num: Int?,
    val generation: String?,
    val height: Int? = 0,
    val weight: Int? = 0,
    val entry: String? = "",
    val typeOne: String?,
    val typeTwo: String?,
    val frontSprite: Uri?,
    val backSprite: Uri?
)