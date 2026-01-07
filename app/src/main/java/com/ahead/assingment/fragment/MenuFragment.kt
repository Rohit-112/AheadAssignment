package com.ahead.assingment.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.ahead.assingment.adapter.MenuAdapter
import com.ahead.assingment.databinding.FragmentMenuBinding
import com.ahead.assingment.helper.Resource
import com.ahead.assingment.network.api.NetworkModule
import com.ahead.assingment.repository.NavigationRepository
import com.ahead.assingment.viewmodel.MainViewModel

class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: MenuAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = MenuAdapter(emptyList()) {
            viewModel.toggleApps()
        }

        val layoutManager = GridLayoutManager(requireContext(), 2)
        binding.featureRecyclerView.layoutManager = layoutManager
        binding.featureRecyclerView.adapter = adapter

        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (adapter.getItemViewType(position)) {
                    MenuAdapter.VIEW_TYPE_ITEM -> 1
                    MenuAdapter.VIEW_TYPE_HEADING,
                    MenuAdapter.VIEW_TYPE_TOGGLE,
                    MenuAdapter.VIEW_TYPE_FULL_WIDTH -> 2

                    else -> 1
                }
            }
        }

        val repository = NavigationRepository(NetworkModule.provideApiService())
        viewModel = ViewModelProvider(
            this,
            object : ViewModelProvider.Factory {
                override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                    return MainViewModel(repository) as T
                }
            }
        )[MainViewModel::class.java]

        viewModel.menuItems.observe(viewLifecycleOwner, Observer { resource ->
            when (resource) {
                is Resource.Success -> {
                    binding.featureRecyclerView.visibility = View.VISIBLE
                    binding.errorContainer.visibility = View.GONE
                    adapter.updateList(resource.data)
                }
                is Resource.Loading -> {
                    binding.featureRecyclerView.visibility = View.GONE
                    binding.errorContainer.visibility = View.GONE
                }
                is Resource.Error -> {
                    binding.featureRecyclerView.visibility = View.GONE
                    binding.errorContainer.visibility = View.VISIBLE

                    binding.errorText.text = resource.message

                    binding.retryButton.setOnClickListener {
                        viewModel.fetchNavigation()
                    }
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
