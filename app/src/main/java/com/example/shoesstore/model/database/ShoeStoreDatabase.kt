package com.example.shoesstore.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.shoesstore.model.entity.CartShoe
import com.example.shoesstore.model.entity.FavoriteShoe

@Database(entities = [CartShoe::class, FavoriteShoe::class], version = 2, exportSchema = false)
abstract class ShoeStoreDatabase : RoomDatabase() {

    abstract fun cartDao(): CartDao
    abstract fun favoriteDao(): FavoriteDao

    companion object {

        @Volatile
        private var INSTANCE: ShoeStoreDatabase? = null

        fun getDatabase(context: Context): ShoeStoreDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ShoeStoreDatabase::class.java,
                    "shoe_store_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}