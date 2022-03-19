package com.example.pokemontracker.ui.fragment.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import com.example.pokemontracker.R
import com.example.pokemontracker.databinding.FragmentFavoritesBinding
import com.example.pokemontracker.ui.fragment.DetailFragment
import org.koin.android.ext.android.inject

class FavoritesFragment : DetailFragment() {
    private val presenter: FavoritesPresenter by inject()
    private lateinit var binding: FragmentFavoritesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorites, container,
            false)
        presenter.start(this, lifecycle)
        return binding.root
    }

    fun addImagesToGrid(imageUrls: List<String>, ids: List<Int>) {
        val cellWidth = binding.favoritesGrid.width / binding.favoritesGrid.columnCount
        val layoutParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
            cellWidth, cellWidth)
        imageUrls.forEachIndexed{ index, imageUrl ->
            val imageView = ImageView(view?.context)
            imageView.layoutParams = layoutParams
            presenter.loadImage(imageView, imageUrl)
            imageView.setOnClickListener {
                presenter.onCellClick(ids[index])
            }
            binding.favoritesGrid.addView(imageView)
        }
    }
}