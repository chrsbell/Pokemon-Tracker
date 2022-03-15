package com.example.pokemontracker.ui.title

import android.app.ActivityOptions
import android.content.Intent
import android.transition.Fade
import android.view.MotionEvent
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.ViewDataBinding
import com.example.pokemontracker.databinding.ActivityTitleBinding
import com.example.pokemontracker.ui.BasePresenter
import com.example.pokemontracker.ui.tracker.TrackerActivity

class TitlePresenter(private val view: TitleActivity) : BasePresenter {
    private lateinit var binding: ActivityTitleBinding
    init {
        view.apply {
            window.enterTransition = Fade()
        }
    }

    fun startApp(event: MotionEvent?) {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            view.apply {
                val intent = Intent(this, TrackerActivity::class.java)
                startActivity(this, intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
            }
        }
    }

    override fun setBinding(binding: ViewDataBinding) {
        this.binding = binding as ActivityTitleBinding
    }
}