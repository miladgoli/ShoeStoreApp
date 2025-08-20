package com.example.shoesstore.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.shoesstore.R
import com.example.shoesstore.databinding.RecShoesItemMainBinding
import com.example.shoesstore.model.entity.Shoe
import com.example.shoesstore.model.util.toFormattedPrice

class ShoeAdapter(
    private val onShoeClicked: (Shoe, View) -> Unit
) : ListAdapter<Shoe, ShoeAdapter.ShoeViewHolder>(ShoeDiffCallback()) {

    class ShoeViewHolder(val binding: RecShoesItemMainBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(shoe: Shoe) {
            binding.tvShoeName.text = shoe.name
            binding.tvShoePrice.text = "${shoe.price.toFormattedPrice()} تومان"
            binding.imgShoeImage.transitionName = "shoe_image_${shoe.id}"
            binding.imgShoeImage.load(shoe.imageUrl) {
                crossfade(true)
                crossfade(500)
                placeholder(R.drawable.loading_placeholder)
                error(R.drawable.error_placeholder)
                allowHardware(false)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoeViewHolder {
        val binding =
            RecShoesItemMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShoeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShoeViewHolder, position: Int) {
        val shoe = getItem(position)
        holder.bind(shoe)
        holder.itemView.setOnClickListener {
            onShoeClicked(shoe, holder.binding.imgShoeImage)
        }
    }
}

class ShoeDiffCallback : DiffUtil.ItemCallback<Shoe>() {
    override fun areItemsTheSame(oldItem: Shoe, newItem: Shoe): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Shoe, newItem: Shoe): Boolean {
        return oldItem == newItem
    }
}