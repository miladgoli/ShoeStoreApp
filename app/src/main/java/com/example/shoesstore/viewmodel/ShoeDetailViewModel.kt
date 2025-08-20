package com.example.shoesstore.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.shoesstore.model.entity.ShoeUiModel
import com.example.shoesstore.model.repository.ShoeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ShoeDetailViewModel @Inject constructor(
    private val repository: ShoeRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val shoeId: Int = savedStateHandle.get<Int>("shoeId")!!

    private val _showSnackbarEvent = MutableLiveData<Boolean>()
    val showSnackbarEvent: LiveData<Boolean> = _showSnackbarEvent

    val shoeDetailState: LiveData<UiState<ShoeUiModel>> =
        repository.favoriteShoes.map { favoriteList ->
            try {
                val allShoes = repository.getShoes()
                val shoe = allShoes.find { it.id == shoeId }

                if (shoe != null) {
                    val isFavorited = favoriteList.any { it.shoeId == shoeId }
                    val uiModel = ShoeUiModel(shoe = shoe, isFavorited = isFavorited)
                    UiState.Success(uiModel)
                } else {
                    UiState.Error("محصول پیدا نشد.")
                }
            } catch (e: Exception) {
                UiState.Error(e.message ?: "خطای ناشناخته")
            }
        }.asLiveData()


    fun toggleFavoriteStatus() {
        val currentState = shoeDetailState.value
        if (currentState is UiState.Success) {
            val shoeUiModel = currentState.data
            viewModelScope.launch {
                if (shoeUiModel.isFavorited) {
                    repository.removeFromFavorites(shoeUiModel.shoe.id)
                } else {
                    repository.addToFavorites(shoeUiModel.shoe.id)
                }
            }
        }
    }

    fun onSnackbarShown() {
        _showSnackbarEvent.value = false
    }

    fun addToCart() {
        viewModelScope.launch {
            repository.addToCart(shoeId)
            _showSnackbarEvent.value = true
        }
    }
}