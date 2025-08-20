package com.example.shoesstore.view.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.shoesstore.R
import com.example.shoesstore.databinding.FragmentShoeDetailBinding
import com.example.shoesstore.model.util.showSnackbar
import com.example.shoesstore.viewmodel.ShoeDetailViewModel
import com.example.shoesstore.viewmodel.UiState
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.MaterialContainerTransform
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShoeDetailFragment : Fragment() {

    private var _binding: FragmentShoeDetailBinding? = null
    private val binding get() = _binding!!

    private var cartSnackbar: Snackbar? = null

    private val viewModel: ShoeDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.mainNavHostFragment
            duration = 500L
            scrimColor = Color.TRANSPARENT
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShoeDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postponeEnterTransition()

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.shoeDetailState.observe(viewLifecycleOwner) { state ->
            binding.progressBarDetail.isVisible = state is UiState.Loading
            binding.contentScrollView.isVisible = state is UiState.Success
            binding.tvErrorDetail.isVisible = state is UiState.Error

            when (state) {
                is UiState.Success -> {
                    val shoeUiModel = state.data

                    binding.shoe = shoeUiModel.shoe

                    val favoriteIcon =
                        if (shoeUiModel.isFavorited) R.drawable.ic_like else R.drawable.ic_like_outline
                    binding.ivDetailFavoriteIcon.setImageResource(favoriteIcon)

                    binding.ivDetailFavoriteIcon.setOnClickListener {
                        viewModel.toggleFavoriteStatus()
                    }

                    binding.ivDetailShoeImage.transitionName = "shoe_image_${shoeUiModel.shoe.id}"
                    binding.ivDetailShoeImage.load(shoeUiModel.shoe.imageUrl) {
                        crossfade(true)
                        crossfade(500)
                        placeholder(R.drawable.loading_placeholder)
                        error(R.drawable.error_placeholder)
                        allowHardware(false)
                        listener(
                            onSuccess = { _, _ -> startPostponedEnterTransition() },
                            onError = { _, _ -> startPostponedEnterTransition() }
                        )
                    }
                }

                is UiState.Error -> {
                    binding.tvErrorDetail.text = state.message
                    startPostponedEnterTransition()
                }

                is UiState.Loading -> {

                }
            }
        }

        viewModel.showSnackbarEvent.observe(viewLifecycleOwner) { shouldShow ->
            if (shouldShow == true) {
                cartSnackbar = binding.root.showSnackbar(
                    message = "محصول به سبد خرید اضافه شد",
                    actionText = "مشاهده..."
                ) {
                    if (findNavController().currentDestination?.id == R.id.shoeDetailFragment) {
                        findNavController().navigate(R.id.action_shoeDetailFragment_to_cartFragment)
                    }
                }
                viewModel.onSnackbarShown()
            }
        }
        binding.btnAddToCart.setOnClickListener {
            viewModel.addToCart()
        }
    }

    override fun onDestroyView() {
        cartSnackbar?.dismiss()
        super.onDestroyView()
        _binding = null
    }
}