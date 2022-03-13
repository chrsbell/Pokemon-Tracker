package com.example.pokemontracker.ui.finder

import android.os.Bundle
import com.example.pokemontracker.R
import com.example.pokemontracker.ui.BaseActivity

class FinderActivity : BaseActivity<FinderPresenter>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = FinderPresenter()
        setContentView(R.layout.activity_finder)
    }
}