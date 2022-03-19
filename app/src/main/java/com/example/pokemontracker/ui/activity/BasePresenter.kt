package com.example.pokemontracker.ui.activity

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver

abstract class BasePresenter<View: Any> : LifecycleObserver {
    protected lateinit var view: View
    protected lateinit var viewLifecycle: Lifecycle

    open fun start(view: View, viewLifecycle: Lifecycle) {
        this.view = view
        this.viewLifecycle = viewLifecycle

        viewLifecycle.addObserver(this)
    }
}