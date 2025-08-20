package com.example.shoesstore.view.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import coil.imageLoader
import com.example.shoesstore.R
import com.example.shoesstore.databinding.FragmentHomeBinding
import com.example.shoesstore.view.adapter.ShoeAdapter
import com.example.shoesstore.viewmodel.ShoeListViewModel
import com.example.shoesstore.viewmodel.UiState
import com.google.android.material.transition.MaterialFadeThrough
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ShoeListViewModel by viewModels()
    private lateinit var shoeAdapter: ShoeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        exitTransition = MaterialFadeThrough()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        setupRecyclerView()

        viewModel.shoesState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.shimmerViewContainer.startShimmer()
                    binding.shimmerViewContainer.visibility = View.VISIBLE
                    binding.rvShoeList.visibility = View.GONE
                    binding.tvErrorMessage.visibility = View.GONE
                }

                is UiState.Success -> {
                    binding.shimmerViewContainer.stopShimmer()
                    binding.shimmerViewContainer.visibility = View.GONE
                    binding.rvShoeList.visibility = View.VISIBLE
                    binding.tvErrorMessage.visibility = View.GONE
                    shoeAdapter.submitList(state.data)
                }

                is UiState.Error -> {
                    binding.shimmerViewContainer.stopShimmer()
                    binding.shimmerViewContainer.visibility = View.GONE
                    binding.rvShoeList.visibility = View.GONE
                    binding.tvErrorMessage.visibility = View.VISIBLE
                    binding.tvErrorMessage.text = state.message
                }
            }
        }



        binding.rvShoeList.viewTreeObserver.addOnPreDrawListener {
            startPostponedEnterTransition()
            true
        }
    }

    private fun setupRecyclerView() {
        shoeAdapter = ShoeAdapter { shoe, imageView ->
            val imageLoader = requireContext().imageLoader
            val isCached = imageLoader.memoryCache?.keys?.any { it.key == shoe.imageUrl } == true

            val extras = if (isCached) {
                FragmentNavigatorExtras(imageView to "shoe_image_${shoe.id}")
            } else {
                FragmentNavigatorExtras()
            }

            val action =
                HomeFragmentDirections.actionHomeFragmentToShoeDetailFragment(shoeId = shoe.id)
            findNavController().navigate(action, extras)
        }
        binding.rvShoeList.adapter = shoeAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.home_menu, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as? SearchView

        searchItem.icon?.mutate()?.setTint(Color.WHITE)

        val searchIcon =
            searchView?.findViewById<ImageView>(androidx.appcompat.R.id.search_mag_icon)
        val searchPlate = searchView?.findViewById<View>(androidx.appcompat.R.id.search_plate)
        val searchText = searchView?.findViewById<TextView>(androidx.appcompat.R.id.search_src_text)
        val closeIcon =
            searchView?.findViewById<ImageView>(androidx.appcompat.R.id.search_close_btn)

        searchIcon?.setColorFilter(Color.WHITE)
        searchText?.setTextColor(Color.WHITE)
        searchText?.setHintTextColor(Color.LTGRAY)
        closeIcon?.setColorFilter(Color.WHITE)

        searchPlate?.setBackgroundColor(Color.TRANSPARENT)

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.setSearchQuery(newText.orEmpty())
                return true
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}