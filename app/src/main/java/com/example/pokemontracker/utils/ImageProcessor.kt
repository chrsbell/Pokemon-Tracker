package com.example.pokemontracker.utils

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.core.graphics.drawable.toBitmap
import androidx.core.net.toUri
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.transition.Transition
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

    fun loadImage(imageView: ImageView, url: String, itemView: View) {
        Glide.with(imageView.context)
            .load(url.toUri())
            .into(object : CustomViewTarget<ImageView, Drawable>(imageView) {
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    imageView.setImageDrawable(resource)
                    setGradientAsync(itemView, resource.toBitmap())
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