package com.example.pokemontracker.ui.snackbar

import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import com.google.android.material.snackbar.Snackbar
import org.koin.core.annotation.Factory
import timber.log.Timber

@Factory
class MessageProvider() : LifecycleObserver {
    var view: View? = null
    private var viewLifecycle: Lifecycle? = null

    companion object {
        const val CONFIRM_TEXT = "CONFIRM"
    }

    fun setView(view: View, viewLifecycle: Lifecycle) {
        this.view = view
        this.viewLifecycle = viewLifecycle

        viewLifecycle.addObserver(this)
    }

    fun showMessage(text: String) {
        if (view != null) {
            Snackbar.make(view!!, text, Snackbar.LENGTH_SHORT).show()
        } else {
            Timber.d(text)
        }
    }

    fun showError(text: String) {
        if (view != null) {
            Snackbar.make(view!!, text, Snackbar.LENGTH_SHORT)
                .setAction(CONFIRM_TEXT, View.OnClickListener {
                })
                .show()
        } else {
            Timber.d(text)
        }
    }
}