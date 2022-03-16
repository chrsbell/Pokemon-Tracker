package com.example.pokemontracker.ui.title

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.transition.Fade
import android.view.MotionEvent
import androidx.core.content.ContextCompat
import com.example.pokemontracker.R
import com.example.pokemontracker.databinding.ActivityTitleBinding
import com.example.pokemontracker.ui.BaseActivity
import androidx.databinding.DataBindingUtil
import com.example.pokemontracker.ui.tracker.TrackerActivity

class TitleActivity : TitleView, BaseActivity() {
    private lateinit var presenter: TitlePresenter
    private lateinit var binding: ActivityTitleBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        // potentially put this into base class
        super.onCreate(savedInstanceState)
        // presenter needs to set up transitions before content view is set
        presenter = TitlePresenter()
        presenter.setView(this, lifecycle)
        window.enterTransition = Fade()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_title)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        presenter.handleNavigate(event?.action == MotionEvent.ACTION_DOWN)
        return super.onTouchEvent(event)
    }

    override fun launchActivity(target: Class<*>) {
        val intent = Intent(this, TrackerActivity::class.java)
        ContextCompat.startActivity(
            this,
            intent,
            ActivityOptions.makeSceneTransitionAnimation(this).toBundle()
        )
    }
}