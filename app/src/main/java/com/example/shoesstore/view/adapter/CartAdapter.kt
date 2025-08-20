package com.example.shoesstore.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.shoesstore.R
import com.example.shoesstore.databinding.ItemRecCartBinding
import com.example.shoesstore.model.util.toFormattedPrice
import com.example.shoesstore.viewmodel.CartItemWithShoe

class CartAdapter(
    private val onDeleteClicked: (CartItemWithShoe) -> Unit,
    private val onItemClicked: (CartItemWithShoe, View) -> Unit
) : ListAdapter<CartItemWithShoe, CartAdapter.CartViewHolder>(CartDiffCallback()) {

    class CartViewHolder(val binding: ItemRecCartBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cartItemWithShoe: CartItemWithShoe) {
            binding.tvCartShoeName.text = cartItemWithShoe.shoe.name
            binding.tvCartShoePrice.text = "${cartItemWithShoe.shoe.price.toFormattedPrice()} تومان"
            binding.ivCartShoeImage.load(cartItemWithShoe.shoe.imageUrl) {
                crossfade(true)
                crossfade(500)
                placeholder(R.drawable.loading_placeholder)
                error(R.drawable.error_placeholder)
                allowHardware(false)
            }

            binding.ivCartShoeImage.transitionName = "shoe_image_${cartItemWithShoe.shoe.id}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemRecCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cartItemWithShoe = getItem(position)
        holder.bind(cartItemWithShoe)

        holder.binding.ivDeleteFromCart.setOnClickListener {
            onDeleteClicked(cartItemWithShoe)
        }

        holder.itemView.setOnClickListener {
            onItemClicked(cartItemWithShoe, holder.binding.ivCartShoeImage)
        }
    }
}

class CartDiffCallback : DiffUtil.ItemCallback<CartItemWithShoe>() {
    override fun areItemsTheSame(oldItem: CartItemWithShoe, newItem: CartItemWithShoe): Boolean {
        return oldItem.cartItem.cartId == newItem.cartItem.cartId
    }

    override fun areContentsTheSame(oldItem: CartItemWithShoe, newItem: CartItemWithShoe): Boolean {
        return oldItem == newItem
    }
}