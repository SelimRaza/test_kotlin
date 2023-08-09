package com.example.mbm.order.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mbm.common.ItemTouchHelperVanSalesCallback
import com.example.mbm.common.toast
import com.example.mbm.databinding.FragmentCartBinding
import com.example.mbm.home.DataSelectionInterface
import com.example.mbm.home.ResponseHome
import com.example.mbm.order.adapter.CartAdapter
import com.example.mbm.order.viewModel.OrderVM


class CartFragment : Fragment(), DataSelectionInterface {
    private lateinit var binding: FragmentCartBinding
    private lateinit var viewModel : OrderVM
    private lateinit var adapter : CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[OrderVM::class.java]
        setupRv()
        adapter.submitData(viewModel.cartList)

    }


    private fun setupRv() {
        binding.rv.layoutManager = LinearLayoutManager(requireContext())
        adapter = CartAdapter(this, viewModel)
        val callback: ItemTouchHelper.Callback = ItemTouchHelperVanSalesCallback(adapter)
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(binding.rv)
        binding.rv.adapter = adapter


    }

    override fun onSelect(obj: ResponseHome.ResponseItem) {
        viewModel.cartList.add(obj)
        "Successfully added to cart".toast()

    }

    override fun onRemove(obj: ResponseHome.ResponseItem) {
        remove(obj)
    }

    private fun remove(obj: ResponseHome.ResponseItem) {
        val tempList = mutableListOf<ResponseHome.ResponseItem>()
        for (item in viewModel.cartList) {
            if (item.iTEMID != obj.iTEMID) {
                tempList.add(item)
            }
        }

        viewModel.cartList.clear()
        viewModel.cartList.addAll(tempList)
        "Removed from cart".toast()
        viewModel.updateItemsList(tempList)


    }

}