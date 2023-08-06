package com.example.mbm.order.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.MenuItem.OnMenuItemClickListener
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mbm.R
import com.example.mbm.common.toast
import com.example.mbm.databinding.FragmentProductsBinding
import com.example.mbm.home.DataSelectionInterface
import com.example.mbm.home.ResponseHome
import com.example.mbm.order.adapter.ProductsAdapter
import com.example.mbm.order.viewModel.OrderVM

class ProductsFragment : Fragment(), DataSelectionInterface {
    private lateinit var binding: FragmentProductsBinding
    private lateinit var viewModel : OrderVM
    private var list: MutableList<ResponseHome.ResponseItem> = ArrayList()
    private lateinit var adapter : ProductsAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentProductsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        viewModel = ViewModelProvider(requireActivity())[OrderVM::class.java]
        setupRv()
        viewModel.itemsLiveData.observe(viewLifecycleOwner) { items ->
            list.addAll(items)
            adapter.submitData(list)
        }
    }

    private fun setupRv() {
        binding.rv.layoutManager = LinearLayoutManager(requireContext())
        adapter = ProductsAdapter(this)
        binding.rv.adapter = adapter
    }

    override fun onSelect(obj: ResponseHome.ResponseItem) {
        viewModel.cartList.add(obj)
        "Successfully added to cart".toast()

    }

    override fun onRemove(obj: ResponseHome.ResponseItem) {
        val tempList = mutableListOf<ResponseHome.ResponseItem>()
        for (item in viewModel.cartList) {
            if(item.iTEMID != obj.iTEMID) {
                tempList.add(item)
            }
        }

        viewModel.cartList.clear()
        viewModel.cartList.addAll(tempList)
        "Removed from cart".toast()
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)

        val searchItem: MenuItem = menu.findItem(R.id.action_search)
        val cartItem: MenuItem = menu.findItem(R.id.action_cart)
        val searchView = searchItem.actionView as SearchView

        // Set up the SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                // Perform search here based on the query text
                // You can trigger your search logic here
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filterItems(newText)
                return true
            }
        })

        cartItem.setOnMenuItemClickListener {
            findNavController().navigate(R.id.action_productsFragment_to_cartFragment)
            false
        }

        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun filterItems(searchText: String) {
        val searchedList: MutableList<ResponseHome.ResponseItem> = ArrayList()
        for (item in list ) {
            if (item.iTEMNAME.lowercase().contains(searchText.lowercase()) || item.iTEMID.contains(searchText)) {
                searchedList.add(item)
            }
        }

        adapter.submitData(searchedList)

        if (searchText.isEmpty()) {
            adapter.submitData(list)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search -> {
                // Handle the search action here if needed
                return true
            }
            // Add more menu items and handle their actions here if needed
        }

        return super.onOptionsItemSelected(item)
    }

}