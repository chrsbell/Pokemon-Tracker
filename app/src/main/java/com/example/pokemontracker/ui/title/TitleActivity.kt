package com.example.pokemontracker.ui.title

import android.os.Bundle
import android.view.MotionEvent
import com.example.pokemontracker.R
import com.example.pokemontracker.databinding.ActivityTitleBinding
import com.example.pokemontracker.ui.BaseActivity
import androidx.databinding.DataBindingUtil

class TitleActivity : BaseActivity<TitlePresenter>() {
    private lateinit var binding: ActivityTitleBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        // potentially put this into base class
        super.onCreate(savedInstanceState)
        // presenter needs to set up transitions before content view is set
        presenter = TitlePresenter(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_title)
        presenter.setBinding(binding)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        presenter.startApp(event)
        return super.onTouchEvent(event)
    }
}