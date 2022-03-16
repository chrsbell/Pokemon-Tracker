package com.example.pokemontracker.ui.tracker

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.pokemontracker.R
import com.example.pokemontracker.databinding.ActivityTrackerBinding
import com.example.pokemontracker.ui.BaseActivity

class TrackerActivity : TrackerView, BaseActivity() {
    private lateinit var presenter: TrackerPresenter
    private lateinit var binding: ActivityTrackerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = TrackerPresenter()
        presenter.setView(this, lifecycle)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tracker)
    }
}