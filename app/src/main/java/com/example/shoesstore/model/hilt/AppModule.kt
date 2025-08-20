package com.example.shoesstore.model.hilt

import android.content.Context
import com.example.shoesstore.model.database.CartDao
import com.example.shoesstore.model.database.FavoriteDao
import com.example.shoesstore.model.database.ShoeStoreDatabase
import com.example.shoesstore.model.repository.ShoeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    fun provideFavoriteDao(database: ShoeStoreDatabase): FavoriteDao {
        return database.favoriteDao()
    }

    @Provides
    @Singleton
    fun provideShoeDatabase(@ApplicationContext context: Context): ShoeStoreDatabase {
        return ShoeStoreDatabase.getDatabase(context)
    }

    @Provides
    fun provideCartDao(database: ShoeStoreDatabase): CartDao {
        return database.cartDao()
    }

    @Provides
    @Singleton
    fun provideShoeRepository(
        cartDao: CartDao,
        favoriteDao: FavoriteDao,
        @ApplicationContext context: Context
    ): ShoeRepository {
        return ShoeRepository(cartDao, favoriteDao, context)
    }

}