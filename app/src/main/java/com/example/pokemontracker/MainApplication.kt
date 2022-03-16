package com.example.pokemontracker

import android.app.Application
import com.example.pokemontracker.di.PokemonTrackerModule
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.startKoin
import org.koin.ksp.generated.module

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MainApplication)
            fragmentFactory()
            modules(PokemonTrackerModule().module)
        }
    }
}