package com.example.pokemontracker.ui.fragment.detail

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.drawable.toBitmap
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.pokemontracker.R
import com.example.pokemontracker.databinding.FragmentPokemonDetailBinding
import com.example.pokemontracker.ui.image.PaletteReadyCallback
import com.example.pokemontracker.ui.image.ResourceReadyCallback
import com.example.pokemontracker.utils.getPokemonTypeColor
import org.koin.android.ext.android.inject

class DetailDialogFragment() : DialogFragment() {
    private val presenter: DetailDialogPresenter by inject()
    private lateinit var binding: FragmentPokemonDetailBinding
    private var pokemonIndex: Int? = null
    private var entry: String? = null
    private var typeOne: String? = null
    private var typeTwo: String? = null
    private var imageUrl: String? = null
    private var isFavorite: Boolean? = null

    companion object {
        const val TAG = "DetailDialog"
        const val BUNDLE_NUM_KEY = "id"
        const val BUNDLE_ENTRY_KEY = "entry"
        const val BUNDLE_TYPE_ONE_KEY = "typeOne"
        const val BUNDLE_TYPE_TWO_KEY = "typeTwo"
        const val BUNDLE_IMAGE_KEY = "image"
        const val BUNDLE_FAVORITE_KEY = "favorite"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pokemonIndex = arguments?.getString(BUNDLE_NUM_KEY)?.toInt()
        entry = arguments?.getString(BUNDLE_ENTRY_KEY)
        typeOne = arguments?.getString(BUNDLE_TYPE_ONE_KEY)
        typeTwo = arguments?.getString(BUNDLE_TYPE_TWO_KEY)
        imageUrl = arguments?.getString(BUNDLE_IMAGE_KEY)
        isFavorite = arguments?.getString(BUNDLE_FAVORITE_KEY).toBoolean()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pokemon_detail, container,
                false)
        binding.pokemonDetailDescription.text = entry
        if (typeOne != null) {
            binding.pokemonDetailTypeOne.text = typeOne
            binding.pokemonDetailTypeCardOne.setCardBackgroundColor(getPokemonTypeColor(typeOne!!))
        }
        // center type here
        if (typeTwo != null) {
            binding.pokemonDetailTypeTwo.text = typeTwo
            binding.pokemonDetailTypeCardTwo.setCardBackgroundColor(getPokemonTypeColor(typeTwo!!))
        } else {
            binding.pokemonDetailTypeCardTwo.visibility = View.INVISIBLE
            val constraintLayout = binding.pokemonDetailTypeCardOne.layoutParams
                    as ConstraintLayout.LayoutParams
            constraintLayout.endToEnd = binding.cardConstraintLayout.id
            constraintLayout.endToStart = ConstraintLayout.LayoutParams.UNSET
            binding.pokemonDetailTypeCardOne.requestLayout()
        }
        binding.favoriteButton.setOnClickListener(View.OnClickListener {
            pokemonIndex?.let { num -> presenter.onFavorite(num) }
        })
        this.isFavorite?.let { updateFavoriteIcon(it) }
        if (imageUrl != null) {
            presenter.loadImage(binding.pokemonDetailImage, imageUrl!!,
                ResourceReadyCallback {
                        presenter.getBackgroundColor(binding.cardConstraintLayout, it.toBitmap(),
                            PaletteReadyCallback { palette, view ->
                                view.setBackgroundColor(palette.getVibrantColor(Color.TRANSPARENT))
                        })
                })
        }

        presenter.start(this, lifecycle)

        return binding.root
    }

    fun updateFavoriteIcon(state: Boolean) {
        if (state) {
            binding.favoriteButton.setColorFilter(Color.RED)
        } else {
            binding.favoriteButton.clearColorFilter()
        }
    }
}