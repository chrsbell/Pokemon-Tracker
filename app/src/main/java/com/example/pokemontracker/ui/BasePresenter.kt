package com.example.pokemontracker.ui

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver

abstract class BasePresenter<View> : LifecycleObserver {
    protected var view: View? = null
    protected var viewLifecycle: Lifecycle? = null

    open fun setView(view: View, viewLifecycle: Lifecycle) {
        this.view = view
        this.viewLifecycle = viewLifecycle

        viewLifecycle.addObserver(this)
    }
}