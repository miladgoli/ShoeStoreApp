package com.example.shoesstore.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.shoesstore.model.entity.CartShoe
import com.example.shoesstore.model.entity.Shoe
import com.example.shoesstore.model.repository.ShoeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject


data class CartItemWithShoe(
    val cartItem: CartShoe,
    val shoe: Shoe
)

@HiltViewModel
class CartViewModel @Inject constructor(
    private val repository: ShoeRepository
) : ViewModel() {

    val cartState: LiveData<UiState<List<CartItemWithShoe>>> =
        repository.cartItems.map { cartItems ->
            try {
                val allShoes = repository.getShoes()
                val detailedCartItems = cartItems.mapNotNull { cartItem ->
                    allShoes.find { shoe -> shoe.id == cartItem.shoeId }
                        ?.let { shoe -> CartItemWithShoe(cartItem, shoe) }
                }
                UiState.Success(detailedCartItems)
            } catch (e: Exception) {
                UiState.Error(e.message ?: "خطای ناشناخته")
            }
        }.asLiveData()

    fun removeFromCart(cartItem: CartShoe) {
        viewModelScope.launch {
            repository.removeFromCart(cartItem.cartId)
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            repository.clearCart()
        }

    }
}