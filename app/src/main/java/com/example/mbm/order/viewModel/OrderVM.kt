package com.example.mbm.order.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mbm.ApiInterface
import com.example.mbm.home.ResponseHome
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback

class OrderVM : ViewModel() {
    lateinit var itemsLiveData: MutableLiveData<List<ResponseHome.ResponseItem>>
    lateinit var cartList: MutableList<ResponseHome.ResponseItem>
    fun init() {
        itemsLiveData = MutableLiveData()
        cartList = ArrayList()
    }

    fun getData() {
        ApiInterface.create()
            .getData2(
                "API",
                "193.123.66.243",
                "1521",
                "IKRAM",
                "q",
                "UAE",
                "Select ITEM_ID,'https://images.sihirbox.com/bdp/Master/item/624ebf5d3a0b8.jpg' ITEM_IMAGE, ITEM_NAME,DIST_SALE_PRICE from ppl1.item_master where item_name like '%2500%'"
            )
            .enqueue(object : Callback<ResponseHome> {
                override fun onResponse(
                    call: Call<ResponseHome>,
                    response: retrofit2.Response<ResponseHome>
                ) {
                    itemsLiveData.postValue(response.body())
                }

                override fun onFailure(call: Call<ResponseHome>, t: Throwable) {

                }
            })
    }

}