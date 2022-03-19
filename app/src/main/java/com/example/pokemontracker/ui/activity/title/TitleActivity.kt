package com.example.pokemontracker.ui.activity.title

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.transition.Fade
import android.view.MotionEvent
import androidx.core.content.ContextCompat
import com.example.pokemontracker.R
import com.example.pokemontracker.ui.activity.BaseActivity
import androidx.databinding.DataBindingUtil
import com.example.pokemontracker.databinding.ActivityTitleBinding
import com.example.pokemontracker.ui.activity.tracker.TrackerActivity
import org.koin.android.ext.android.inject


class TitleActivity : BaseActivity() {
    private val presenter: TitlePresenter by inject()
    private lateinit var binding: ActivityTitleBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // need to set up transitions before content view is set
        presenter.start(this, lifecycle)
        window.enterTransition = Fade()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_title)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        presenter.handleNavigate(event?.action == MotionEvent.ACTION_DOWN)
        return super.onTouchEvent(event)
    }

    fun launchActivity(target: Class<*>) {
        val intent = Intent(this, TrackerActivity::class.java)
        ContextCompat.startActivity(
            this,
            intent,
            ActivityOptions.makeSceneTransitionAnimation(this).toBundle()
        )
    }
}