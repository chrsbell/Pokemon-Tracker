package com.example.pokemontracker.ui.tracker

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.pokemontracker.R
import com.example.pokemontracker.databinding.ActivityTrackerBinding
import com.example.pokemontracker.ui.BaseActivity
import com.example.pokemontracker.ui.favorites.FavoritesFragment
import com.example.pokemontracker.ui.snackbar.MessageProvider
import com.example.pokemontracker.ui.snackbar.MessageView
import com.example.pokemontracker.ui.tracker.search.SearchFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.android.ext.android.inject

class TrackerActivity : MessageView, BaseActivity() {
    private val presenter: TrackerPresenter by inject()
    private lateinit var binding: ActivityTrackerBinding

    companion object {
        const val LAST_THEME = "lastTheme"
        val TABS = arrayOf(R.string.search, )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tracker)


        val adapter = TrackerPagingAdapter(supportFragmentManager, lifecycle)
        val tabViewPager = binding.tabViewPager
        tabViewPager.adapter = adapter
        val tabTitles = arrayOf(resources.getString(R.string.search),
            resources.getString(R.string.favorites))
        TabLayoutMediator(binding.tabNavigationLayout, tabViewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()

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

class TrackerPagingAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle)
    : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun createFragment(position: Int): Fragment {
        if (position == 0) {
            return SearchFragment()
        }
        return FavoritesFragment()
    }

    override fun getItemCount(): Int = 2
}