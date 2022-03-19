package com.example.pokemontracker.ui.image

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.transition.Transition
import com.example.pokemontracker.ui.snackbar.MessageProvider
import com.example.pokemontracker.ui.snackbar.Messaging
import org.koin.core.annotation.Single

@Single
class ImageProcessor() : Messaging {
    // the snackbar message provider will be different depending on what view the app is in
    private lateinit var messageProvider: MessageProvider

    override fun setMessageProvider(messageProvider: MessageProvider) {
        this.messageProvider = messageProvider
    }

    fun loadImage(
        imageView: ImageView, url: String,
        resourceReadyCallback: ResourceReadyCallback? = null
    ) {
        Glide.with(imageView.context)
            .load(url.toUri())
            .into(object : CustomViewTarget<ImageView, Drawable>(imageView) {
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    imageView.setImageDrawable(resource)
                    resourceReadyCallback?.run(resource)
                }

                override fun onResourceCleared(placeholder: Drawable?) {}

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    messageProvider.showMessage("Couldn't load image.")
                }
            })
    }

    fun getColorPalette(
        view: View, bitmap: Bitmap,
        paletteReadyCallback: PaletteReadyCallback? = null
    ) {
        Palette.from(bitmap).generate {
            if (it != null) {
                paletteReadyCallback?.run(it, view)
            }
        }
    }

    fun setGradientAsync() = PaletteReadyCallback { palette, view ->
        view.background = GradientDrawable(
            GradientDrawable.Orientation.LEFT_RIGHT, intArrayOf(
                palette.getVibrantColor(Color.TRANSPARENT),
                Color.TRANSPARENT
            )
        )
    }
}