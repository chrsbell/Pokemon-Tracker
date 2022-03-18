package com.example.pokemontracker.repositories

import androidx.lifecycle.lifecycleScope
import com.apollographql.apollo3.api.ApolloResponse
import com.example.pokemontracker.client.PokemonDetailQuery
import com.example.pokemontracker.client.PokemonQuery
import com.example.pokemontracker.client.apolloClient
import com.example.pokemontracker.utils.ImageProcessor
import org.koin.core.annotation.Single
import org.koin.java.KoinJavaComponent.inject

@Single
class PokemonRepository(private val imageProcessor: ImageProcessor) {
    // use a database cache later
    suspend fun getAllPokemon(): List<PokemonData>? {
        return apolloClient.query(PokemonQuery()).execute()
            .data?.allPokemon?.filterNotNull()
            ?.map { info ->
                var typeTwo: String? = null
                if (info.types?.size == 2) {
                    typeTwo = info.types[1]?.name
                }
                PokemonData(
                    name = info.name,
                    num = info.nat_dex_num,
                    generation = info.generation,
                    height = info.height,
                    weight = info.weight,
                    typeOne = info.types?.get(0)?.name,
                    typeTwo = typeTwo,
                    frontSprite = imageProcessor.getUri(info.sprites?.front_default),
                    backSprite = imageProcessor.getUri(info.sprites?.back_default)
                )
            }
    }

    suspend fun getPokemonDetails(): ApolloResponse<PokemonDetailQuery.Data> {
        return apolloClient.query(PokemonDetailQuery()).execute()
    }
}