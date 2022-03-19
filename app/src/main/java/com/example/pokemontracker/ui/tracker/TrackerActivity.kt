package com.example.pokemontracker.ui.tracker

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.pokemontracker.R
import com.example.pokemontracker.databinding.ActivityTrackerBinding
import com.example.pokemontracker.ui.BaseActivity
import com.example.pokemontracker.ui.snackbar.MessageProvider
import com.example.pokemontracker.ui.snackbar.MessageView
import org.koin.android.ext.android.inject

class TrackerActivity : MessageView, BaseActivity() {
    private val presenter: TrackerPresenter by inject()
    private lateinit var binding: ActivityTrackerBinding

    companion object {
        const val LAST_THEME = "lastTheme"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tracker)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.searchNavContainer) as NavHostFragment
        val navController = navHostFragment.navController
//        NavigationUI.setupActionBarWithNavController(this, navController, binding.toolbar)
        NavigationUI.setupWithNavController(binding.bottomMenu, findNavController(R.id.searchNavContainer))
//        binding.bottomMenu.setupWithNavController(navController)
        setSupportActionBar(binding.toolbar)
        presenter.start(this, lifecycle)
        // used to check if theme changed between start of last activity
        // and start of current activity
        presenter.setThemeOnCreate(this)
        if (savedInstanceState != null) {
            val lastTheme = savedInstanceState.getBoolean(LAST_THEME)
            presenter.checkIfNewTheme(lastTheme)
        }
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