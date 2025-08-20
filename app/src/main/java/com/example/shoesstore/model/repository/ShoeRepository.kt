package com.example.shoesstore.model.repository

import android.content.Context
import com.example.shoesstore.model.database.CartDao
import com.example.shoesstore.model.database.FavoriteDao
import com.example.shoesstore.model.entity.CartShoe
import com.example.shoesstore.model.entity.FavoriteShoe
import com.example.shoesstore.model.entity.Shoe
import com.example.shoesstore.model.util.NetworkUtils
import com.example.shoesstore.model.util.ShoeDataSource
import kotlinx.coroutines.flow.Flow

class ShoeRepository(
    private val cartDao: CartDao,
    private val favoriteDao: FavoriteDao,
    private val context: Context
) {

    fun getShoes(): List<Shoe> {
        if (NetworkUtils.isInternetAvailable(context)) {
            return ShoeDataSource.getDefaultShoesList()
        } else {
            throw Exception("اتصال اینترنت برقرار نیست.")
        }
    }

    val cartItems: Flow<List<CartShoe>> = cartDao.getCartItems()

    suspend fun addToCart(shoeId: Int) {
        val cartItem = CartShoe(shoeId = shoeId)
        cartDao.addToCart(cartItem)
    }

    suspend fun removeFromCart(cartItemId: Int) {
        cartDao.removeFromCart(cartItemId)
    }

    suspend fun clearCart() {
        cartDao.clearCart()
    }

    val favoriteShoes: Flow<List<FavoriteShoe>> = favoriteDao.getAllFavorites()

    suspend fun addToFavorites(shoeId: Int) {
        favoriteDao.addToFavorites(FavoriteShoe(shoeId = shoeId))
    }

    suspend fun removeFromFavorites(shoeId: Int) {
        favoriteDao.removeFromFavorites(shoeId)
    }


}