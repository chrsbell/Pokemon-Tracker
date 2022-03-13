package com.example.pokemontracker.ui

import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity<T : BasePresenter> : AppCompatActivity() {
    protected lateinit var presenter: T
}