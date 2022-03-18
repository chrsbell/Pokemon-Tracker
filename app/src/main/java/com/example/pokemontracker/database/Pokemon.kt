package com.example.pokemontracker.database

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon")
data class Pokemon (
    @PrimaryKey val num: Int?,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "generation")val generation: String?,
    @ColumnInfo(name = "height")val height: Int? = 0,
    @ColumnInfo(name = "weight")val weight: Int? = 0,
    @ColumnInfo(name = "entry")val entry: String? = "",
    @ColumnInfo(name = "typeOne")val typeOne: String?,
    @ColumnInfo(name = "typeTwo")val typeTwo: String?,
    @ColumnInfo(name = "frontSprite")val frontSprite: String?,
    @ColumnInfo(name = "backSprite")val backSprite: String?
)