package com.example.shoesstore.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites_table")
data class FavoriteShoe(
    @PrimaryKey
    val shoeId: Int
)
