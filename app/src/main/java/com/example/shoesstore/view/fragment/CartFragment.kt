package com.example.shoesstore.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.example.shoesstore.adapter.CartAdapter
import com.example.shoesstore.databinding.FragmentCartBinding
import com.example.shoesstore.viewmodel.CartViewModel
import com.example.shoesstore.viewmodel.UiState
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.transition.MaterialFadeThrough
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CartViewModel by viewModels()
    private lateinit var cartAdapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exitTransition = MaterialFadeThrough()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postponeEnterTransition()

        setupRecyclerView()
        observeCartItems()

        binding.rvCartItems.viewTreeObserver.addOnPreDrawListener {
            startPostponedEnterTransition()
            true
        }

        binding.btnClearAll.setOnClickListener {
            showClearCartConfirmationDialog()
        }
    }

    private fun setupRecyclerView() {
        cartAdapter = CartAdapter(
            onDeleteClicked = { cartItemWithShoe ->
                viewModel.removeFromCart(cartItemWithShoe.cartItem)
            },
            onItemClicked = { cartItemWithShoe, imageView ->
                val shoe = cartItemWithShoe.shoe
                val extras = FragmentNavigatorExtras(imageView to "shoe_image_${shoe.id}")
                val action = CartFragmentDirections.actionCartFragmentToShoeDetailFragment(shoe.id)
                findNavController().navigate(action, extras)
            }
        )
        binding.rvCartItems.adapter = cartAdapter
    }

    private fun observeCartItems() {
        viewModel.cartState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                }

                is UiState.Success -> {
                    val cartItems = state.data

                    if (cartItems.isEmpty()) {
                        binding.tvErrorMessage.visibility = View.GONE
                        binding.rvCartItems.animate().alpha(0f).withEndAction {
                            binding.rvCartItems.visibility = View.GONE
                        }
                        binding.btnClearAll.visibility = View.GONE
                        binding.tvEmptyCart.alpha = 0f
                        binding.tvEmptyCart.visibility = View.VISIBLE
                        binding.tvEmptyCart.animate().alpha(1f)
                    } else {
                        binding.tvErrorMessage.visibility = View.GONE
                        binding.rvCartItems.visibility = View.VISIBLE
                        binding.rvCartItems.alpha = 1f
                        binding.btnClearAll.visibility = View.VISIBLE
                        binding.tvEmptyCart.visibility = View.GONE
                    }

                    cartAdapter.submitList(cartItems)
                }

                is UiState.Error -> {
                    binding.rvCartItems.visibility = View.GONE
                    binding.tvEmptyCart.visibility = View.GONE
                    binding.tvErrorMessage.visibility = View.VISIBLE
                    binding.tvErrorMessage.text = state.message
                }
            }
        }
    }

    private fun showClearCartConfirmationDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("تایید حذف")
            .setMessage("آیا از حذف تمام آیتم‌ها از سبد خرید مطمئنید؟")
            .setNegativeButton("خیر") { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton("بله") { dialog, _ ->
                viewModel.clearCart()
                dialog.dismiss()
            }
            .show()
    }


    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}