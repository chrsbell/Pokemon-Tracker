package com.example.pokemontracker.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.pokemontracker.R
import com.example.pokemontracker.databinding.FragmentPokemonDetailBinding
import org.koin.android.ext.android.inject
import timber.log.Timber

class DetailDialogFragment() : DetailDialogView, DialogFragment() {
    private val presenter: DetailDialogPresenter by inject()
    private lateinit var binding: FragmentPokemonDetailBinding
    var num: Int? = null
    private var entry: String? = null
    private var typeOne: String? = null
    private var typeTwo: String? = null
    private var imageUrl: String? = null

    companion object {
        const val TAG = "DetailDialog"
        const val BUNDLE_NUM_KEY = "id"
        const val BUNDLE_ENTRY_KEY = "entry"
        const val BUNDLE_TYPE_ONE_KEY = "typeOne"
        const val BUNDLE_TYPE_TWO_KEY = "typeTwo"
        const val BUNDLE_IMAGE_KEY = "image"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        num = arguments?.getString(BUNDLE_NUM_KEY)?.toInt()
        entry = arguments?.getString(BUNDLE_ENTRY_KEY)
        typeOne = arguments?.getString(BUNDLE_TYPE_ONE_KEY)
        typeTwo = arguments?.getString(BUNDLE_TYPE_TWO_KEY)
        imageUrl = arguments?.getString(BUNDLE_IMAGE_KEY)
        presenter.start(this, lifecycle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pokemon_detail, container,
            false)
        binding.pokemonDetailDescription.text = entry
        binding.pokemonDetailTypeOne.text = typeOne
        binding.pokemonDetailTypeTwo.text = typeTwo
        imageUrl?.let { presenter.loadImage(binding.pokemonDetailImage, it) }
        return binding.root
    }
}