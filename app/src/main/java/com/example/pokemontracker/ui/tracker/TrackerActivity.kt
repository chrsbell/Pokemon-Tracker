package com.example.pokemontracker.ui.tracker

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.pokemontracker.R
import com.example.pokemontracker.databinding.ActivityTrackerBinding
import com.example.pokemontracker.ui.BaseActivity
import org.koin.android.ext.android.inject

class TrackerActivity : TrackerView, BaseActivity() {
    private val presenter: TrackerPresenter by inject()
    private lateinit var binding: ActivityTrackerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.setView(this, lifecycle)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tracker)
        setSupportActionBar(binding.toolbar)
    }
}