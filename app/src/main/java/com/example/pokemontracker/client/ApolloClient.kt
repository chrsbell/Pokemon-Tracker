package com.example.pokemontracker.client

import com.apollographql.apollo3.ApolloClient

val apolloClient = ApolloClient.Builder()
    .serverUrl("https://dex-server.herokuapp.com/graphql")
    .build()