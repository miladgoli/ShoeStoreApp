package com.example.shoesstore.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoesstore.model.entity.Shoe
import com.example.shoesstore.model.repository.ShoeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay

import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}

@HiltViewModel
class ShoeListViewModel @Inject constructor(
    private val repository: ShoeRepository
) : ViewModel() {

    private val _shoesState = MutableLiveData<UiState<List<Shoe>>>()
    val shoesState: LiveData<UiState<List<Shoe>>> = _shoesState

    private var originalShoeList: List<Shoe> = emptyList()

    init {
        loadShoes()
    }

    private fun loadShoes() {
        _shoesState.value = UiState.Loading
        viewModelScope.launch {
            delay(2000)
            try {
                val shoes = repository.getShoes()
                originalShoeList = shoes
                _shoesState.value = UiState.Success(shoes)
            } catch (e: Exception) {
                _shoesState.value = UiState.Error(e.message ?: "خطای ناشناخته")
            }
        }
    }

    fun setSearchQuery(query: String) {
        val filteredList = if (query.isBlank()) {
            originalShoeList
        } else {
            originalShoeList.filter { shoe ->
                shoe.name.contains(query, ignoreCase = true) ||
                        shoe.brand.contains(query, ignoreCase = true)
            }
        }
        _shoesState.value = UiState.Success(filteredList)
    }
}