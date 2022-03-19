package com.example.pokemontracker.repositories

import android.content.Context
import com.apollographql.apollo3.exception.ApolloException
import com.example.pokemontracker.client.PokemonDetailQuery
import com.example.pokemontracker.client.PokemonOverviewQuery
import com.example.pokemontracker.client.apolloClient
import com.example.pokemontracker.database.Pokemon
import com.example.pokemontracker.database.PokemonDao
import com.example.pokemontracker.database.PokemonDatabase
import com.example.pokemontracker.database.getDatabase
import com.example.pokemontracker.ui.image.ImageProcessor
import org.koin.core.annotation.Single

@Single
class PokemonRepository(private val imageProcessor: ImageProcessor) {
    private lateinit var db: PokemonDatabase
    private lateinit var pokemonDao: PokemonDao

    fun initDb(context: Context) {
        db = getDatabase(context)
        pokemonDao = db.pokemonDao()
    }

    suspend fun getAllPokemon(): List<Pokemon>? {
        var allPokemon: List<Pokemon>? = pokemonDao.getAllPokemon()
        if (allPokemon == null || allPokemon.isEmpty()) {
            // initial fetch and caching of pokemon overview data
            allPokemon = try {
                apolloClient.query(PokemonOverviewQuery()).execute()
                    .data?.allPokemon?.filterNotNull()
                    ?.mapIndexed{ index, info ->
                        var typeTwo: String? = null
                        if (info.types?.size == 2) {
                            typeTwo = info.types.last()?.name
                        }
                        Pokemon(
                            name = info.name!!,
                            // num is null on some pokemon from api
                            num = index + 1,
                            weight = info.weight!!,
                            typeOne = info.types?.get(0)?.name!!,
                            typeTwo = typeTwo,
                            frontSprite = info.sprites?.front_default!!,
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

    suspend fun findByPokedexNumber(num: Int): Pokemon {
        val pokemon = pokemonDao.findByPokedexNumber(num)
        if (!pokemon.isDetailed) {
            // supplemental call to get more detailed info
            val pokemonDetails = apolloClient.query(PokemonDetailQuery(num))
                .execute().data?.pokemon
            pokemon.height = pokemonDetails?.height!!
            pokemon.entry = pokemonDetails.pokedex_entries?.last()?.description!!
            pokemon.backSprite = pokemonDetails.sprites?.back_default
            pokemonDao.updatePokemon(pokemon)
        }
        return pokemon
    }

    suspend fun updatePokemon(pokemon: Pokemon) {
        pokemonDao.updatePokemon(pokemon)
    }
}