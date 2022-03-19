package com.example.pokemontracker.ui.image

import android.view.View
import androidx.palette.graphics.Palette

class PaletteReadyCallback(
    private val callback: ((palette: Palette, view: View) -> Unit)? = null
) {
    fun run(palette: Palette, view: View) = callback?.invoke(palette, view)
}