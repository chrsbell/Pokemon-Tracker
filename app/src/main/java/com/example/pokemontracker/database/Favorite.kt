package com.example.pokemontracker.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorites")
data class Favorite (
    @PrimaryKey val num: Int?,
)