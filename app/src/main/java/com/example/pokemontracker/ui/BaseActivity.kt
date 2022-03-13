package com.example.pokemontracker.ui

import androidx.appcompat.app.AppCompatActivity
import com.example.pokemontracker.ui.title.TitleActivity

open class BaseActivity : AppCompatActivity() {
    protected lateinit var presenter: BasePresenter<TitleActivity>
}