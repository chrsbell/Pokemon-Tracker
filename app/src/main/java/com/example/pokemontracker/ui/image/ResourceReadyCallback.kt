package com.example.pokemontracker.ui.image

import android.graphics.drawable.Drawable

class ResourceReadyCallback(private val callback: ((resource: Drawable) -> Unit)? = null) {
    fun run(resource: Drawable) = callback?.invoke(resource)
}