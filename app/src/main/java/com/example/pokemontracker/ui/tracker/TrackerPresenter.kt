package com.example.pokemontracker.ui.tracker

import androidx.databinding.ViewDataBinding
import com.example.pokemontracker.databinding.ActivityTrackerBinding
import com.example.pokemontracker.ui.BasePresenter

class TrackerPresenter(private val view: TrackerActivity) : BasePresenter {
    private lateinit var binding: ActivityTrackerBinding

    init {

    }

    override fun setBinding(binding: ViewDataBinding) {
        this.binding = binding as ActivityTrackerBinding
    }
}