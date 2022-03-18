package com.example.pokemontracker.database

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon")
data class Pokemon (
    @PrimaryKey val num: Int,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "generation") var generation: String = "",
    @ColumnInfo(name = "height") var height: Int = 0,
    @ColumnInfo(name = "weight") var weight: Int = 0,
    @ColumnInfo(name = "entry") var entry: String = "",
    @ColumnInfo(name = "typeOne") var typeOne: String,
    @ColumnInfo(name = "typeTwo") var typeTwo: String? = null,
    @ColumnInfo(name = "frontSprite") var frontSprite: String,
    @ColumnInfo(name = "backSprite") var backSprite: String? = null,
    @ColumnInfo(name = "isDetailed") var isDetailed: Boolean = false,// whether data is complete
    @ColumnInfo(name = "isFavorite") var isFavorite: Boolean = false
)