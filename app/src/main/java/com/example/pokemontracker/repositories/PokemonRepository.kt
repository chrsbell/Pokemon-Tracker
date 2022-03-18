package com.example.pokemontracker.repositories

import android.content.Context
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.exception.ApolloException
import com.example.pokemontracker.client.PokemonDetailQuery
import com.example.pokemontracker.client.PokemonQuery
import com.example.pokemontracker.client.apolloClient
import com.example.pokemontracker.database.FavoriteDao
import com.example.pokemontracker.database.Pokemon
import com.example.pokemontracker.database.PokemonDao
import com.example.pokemontracker.database.PokemonDatabase
import com.example.pokemontracker.database.getDatabase
import com.example.pokemontracker.utils.ImageProcessor
import org.koin.core.annotation.Single

@Single
class PokemonRepository(private val imageProcessor: ImageProcessor) {
    private lateinit var db: PokemonDatabase
    private lateinit var pokemonDao: PokemonDao
    private lateinit var favoriteDao: FavoriteDao

    fun initDb(context: Context) {
        db = getDatabase(context)
        pokemonDao = db.pokemonDao()
        favoriteDao = db.favoriteDao()
    }

    suspend fun getAllPokemon(): List<Pokemon>? {
        var allPokemon: List<Pokemon>? = pokemonDao.getAllPokemon()
        if (allPokemon == null || allPokemon.isEmpty()) {
            // initial fetch and caching of data
            allPokemon = try {
                apolloClient.query(PokemonQuery()).execute()
                    .data?.allPokemon?.filterNotNull()
                    ?.map { info ->
                        var typeTwo: String? = null
                        if (info.types?.size == 2) {
                            typeTwo = info.types[1]?.name
                        }
                        Pokemon(
                            name = info.name,
                            num = info.nat_dex_num,
                            generation = info.generation,
                            height = info.height,
                            weight = info.weight,
                            typeOne = info.types?.get(0)?.name,
                            typeTwo = typeTwo,
                            frontSprite = info.sprites?.front_default,
                            backSprite = info.sprites?.back_default
                        )
                    }
            } catch (e: ApolloException) {
                null
            }
            if (allPokemon == null) return null
            allPokemon.toTypedArray().let { pokemonDao.insertAll(*it) }
        }
        return allPokemon
    }

    suspend fun getPokemonDetails(): ApolloResponse<PokemonDetailQuery.Data> {
        return apolloClient.query(PokemonDetailQuery()).execute()
    }
}