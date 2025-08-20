package com.example.shoesstore.model.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shoesstore.model.entity.CartShoe
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToCart(cartItem: CartShoe)

    @Query("SELECT * FROM cart_table")
    fun getCartItems(): Flow<List<CartShoe>>

    @Query("DELETE FROM cart_table WHERE cartId = :itemId")
    suspend fun removeFromCart(itemId: Int)

    @Query("DELETE FROM cart_table")
    suspend fun clearCart()
}