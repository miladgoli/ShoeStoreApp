package com.example.shoesstore.model.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shoesstore.model.entity.FavoriteShoe
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addToFavorites(favoriteShoe: FavoriteShoe)

    @Query("DELETE FROM favorites_table WHERE shoeId = :shoeId")
    suspend fun removeFromFavorites(shoeId: Int)

    @Query("SELECT * FROM favorites_table")
    fun getAllFavorites(): Flow<List<FavoriteShoe>>

}