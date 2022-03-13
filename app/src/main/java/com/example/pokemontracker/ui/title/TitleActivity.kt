package com.example.pokemontracker.ui.title

import android.os.Bundle
import com.example.pokemontracker.R
import com.example.pokemontracker.ui.BaseActivity

class TitleActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // potentially put this into base class
        super.onCreate(savedInstanceState)
        presenter = TitlePresenter(this)
        setContentView(R.layout.activity_main)
    }

}