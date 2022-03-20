package com.example.pokemontracker.ui.fragment.detail

import android.os.Bundle
import androidx.fragment.app.Fragment

abstract class DetailFragment : Fragment() {
    fun showDialog(options: Map<String, String>) {
        val bundle = Bundle()
        options.forEach {
            bundle.putString(it.key, it.value)
        }
        val detailView = DetailDialogFragment()
        detailView.arguments = bundle
        detailView.show(
            childFragmentManager, DetailDialogFragment.TAG
        )
    }
}