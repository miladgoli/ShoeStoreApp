package com.example.shoesstore.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.example.shoesstore.databinding.FragmentFavoritesBinding
import com.example.shoesstore.view.adapter.ShoeAdapter
import com.example.shoesstore.viewmodel.FavoritesViewModel
import com.google.android.material.transition.MaterialFadeThrough // <<-- import جدید
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FavoritesViewModel by viewModels()
    private lateinit var shoeAdapter: ShoeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exitTransition = MaterialFadeThrough()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postponeEnterTransition()

        setupRecyclerView()
        observeFavorites()

        binding.rvFavoritesList.viewTreeObserver.addOnPreDrawListener {
            startPostponedEnterTransition()
            true
        }
    }

    private fun setupRecyclerView() {
        shoeAdapter = ShoeAdapter { shoe, imageView ->
            val extras = FragmentNavigatorExtras(imageView to "shoe_image_${shoe.id}")
            val action =
                FavoritesFragmentDirections.actionFavoritesFragmentToShoeDetailFragment(shoe.id)
            findNavController().navigate(action, extras)
        }
        binding.rvFavoritesList.adapter = shoeAdapter
    }

    private fun observeFavorites() {
        viewModel.favoriteShoesList.observe(viewLifecycleOwner) { favorites ->
            binding.tvEmptyFavorites.isVisible = favorites.isEmpty()
            binding.rvFavoritesList.isVisible = favorites.isNotEmpty()
            shoeAdapter.submitList(favorites)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}