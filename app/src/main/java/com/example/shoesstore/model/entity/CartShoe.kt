package com.example.shoesstore.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_table")
data class CartShoe(
    @PrimaryKey(autoGenerate = true)
    val cartId: Int = 0,
    val shoeId: Int,
    val quantity: Int = 1
)
