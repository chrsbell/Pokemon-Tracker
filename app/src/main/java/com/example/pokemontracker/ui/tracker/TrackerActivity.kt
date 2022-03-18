package com.example.pokemontracker.ui.tracker

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.example.pokemontracker.R
import com.example.pokemontracker.databinding.ActivityTrackerBinding
import com.example.pokemontracker.ui.BaseActivity
import com.example.pokemontracker.utils.MessageProvider
import com.example.pokemontracker.utils.MessageView
import org.koin.android.ext.android.inject

class TrackerActivity : TrackerView, MessageView, BaseActivity() {
    private val presenter: TrackerPresenter by inject()
    private lateinit var binding: ActivityTrackerBinding

    companion object {
        const val LAST_THEME = "lastTheme"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tracker)
        presenter.setView(this, lifecycle)
        presenter.setThemeOnCreate(this)
        if (savedInstanceState != null) {
            val lastTheme = savedInstanceState.getBoolean(LAST_THEME)
            presenter.checkIfNewTheme(lastTheme)
        }
        setSupportActionBar(binding.toolbar)
    }

    fun restart() {
        this.recreate()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.overflow_menu, menu)
        return true;
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        presenter.onThemeSelect(item)
        return super.onOptionsItemSelected(item)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        presenter.themeOnCreate.let { outState.putBoolean(LAST_THEME, it) }
        super.onSaveInstanceState(outState)
    }

    override fun setSnackbarView(messageProvider: MessageProvider) {
        messageProvider.setView(binding.root, lifecycle)
    }
}