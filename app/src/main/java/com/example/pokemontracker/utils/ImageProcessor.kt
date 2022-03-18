package com.example.pokemontracker.utils

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.ImageView
import androidx.core.graphics.drawable.toBitmap
import androidx.core.net.toUri
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.transition.Transition
import com.example.pokemontracker.ui.snackbar.MessageProvider
import com.example.pokemontracker.ui.snackbar.Messaging
import org.koin.core.annotation.Single

@Single
class ImageProcessor() : Messaging {
    // the snackbar message provider will be different depending on what view the app is in
    private lateinit var messageProvider: MessageProvider
    private lateinit var coroutineScope: LifecycleCoroutineScope

    fun getUri(imgUrl : String?) = imgUrl?.toUri()?.buildUpon()?.scheme("https")?.build()

    override fun setMessageProvider(messageProvider: MessageProvider) {
        this.messageProvider = messageProvider
    }

    fun loadImage(imageView: ImageView, url: String, itemView: View? = null) {
        Glide.with(imageView.context)
            .load(url.toUri())
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .into(object : CustomViewTarget<ImageView, Drawable>(imageView) {
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    imageView.setImageDrawable(resource)
                    if (itemView != null) {
                        setGradientAsync(itemView, resource.toBitmap())
                    }
                }

                override fun onResourceCleared(placeholder: Drawable?) {
                }

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    messageProvider.showMessage("Couldn't load image.")
                }
            })
    }

    fun setGradientAsync(view: View, bitmap: Bitmap) {
        Palette.from(bitmap).generate {
            palette ->
            run {
                if (palette != null) {
                    view.background = GradientDrawable(
                        GradientDrawable.Orientation.LEFT_RIGHT, intArrayOf(
                            palette.getVibrantColor(Color.TRANSPARENT),
                            Color.TRANSPARENT
                        )
                    )
                }
            }
        }
    }
}