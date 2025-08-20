package com.example.shoesstore.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.shoesstore.model.entity.Shoe
import com.example.shoesstore.model.repository.ShoeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repository: ShoeRepository
) : ViewModel() {

    val favoriteShoesList: LiveData<List<Shoe>> = repository.favoriteShoes.map { favoriteList ->
        val allShoes = repository.getShoes()
        val favoriteIds = favoriteList.map { it.shoeId }.toSet()
        allShoes.filter { shoe -> shoe.id in favoriteIds }
    }.asLiveData()
}