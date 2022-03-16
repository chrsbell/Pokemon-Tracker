package com.example.pokemontracker.repositories

import androidx.lifecycle.lifecycleScope
import com.apollographql.apollo3.api.ApolloResponse
import com.example.pokemontracker.client.PokemonQuery
import com.example.pokemontracker.client.apolloClient
import timber.log.Timber

class PokemonRepository {
    // use a database cache later
    suspend fun getAllPokemon(): ApolloResponse<PokemonQuery.Data> {
        return apolloClient.query(PokemonQuery()).execute()
    }
}