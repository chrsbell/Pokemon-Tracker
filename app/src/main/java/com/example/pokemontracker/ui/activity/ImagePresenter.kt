package com.example.pokemontracker.ui.activity

import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import com.example.pokemontracker.ui.image.ImageProcessor
import com.example.pokemontracker.ui.image.PaletteReadyCallback
import com.example.pokemontracker.ui.image.ResourceReadyCallback

abstract class ImagePresenter<PresenterView: Any>(val imageProcessor: ImageProcessor)
    : BasePresenter<PresenterView>() {
    fun loadImage(
        imageView: ImageView,
        imageUrl: String,
        resourceReadyCallback: ResourceReadyCallback? = null
    ) {
        imageProcessor.loadImage(imageView, imageUrl, resourceReadyCallback)
    }

    fun getBackgroundColor(view: View, bitmap: Bitmap,
                           paletteReadyCallback: PaletteReadyCallback
    ) =
        imageProcessor.getColorPalette(view, bitmap, paletteReadyCallback)
}

