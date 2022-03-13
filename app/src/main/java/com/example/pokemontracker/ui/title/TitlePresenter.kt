package com.example.pokemontracker.ui.title

import android.app.ActivityOptions
import android.content.Intent
import android.transition.Explode
import android.transition.Fade
import android.transition.Transition
import android.view.MotionEvent
import android.view.Window
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.ViewDataBinding
import com.example.pokemontracker.databinding.ActivityTitleBinding
import com.example.pokemontracker.ui.BasePresenter
import com.example.pokemontracker.ui.finder.FinderActivity

class TitlePresenter(private val view: TitleActivity) : BasePresenter {
    private lateinit var binding: ActivityTitleBinding
    init {
        view.apply {
            window.enterTransition = Fade()
            supportActionBar?.hide()
        }
    }

    fun startApp(event: MotionEvent?) {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            view.apply {
                val intent = Intent(this, FinderActivity::class.java)
                startActivity(this, intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
            }
        }
    }

    override fun setBinding(binding: ViewDataBinding) {
        this.binding = binding as ActivityTitleBinding
    }
}