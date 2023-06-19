package com.example.mbm.home

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mbm.ApiInterface
import com.example.mbm.databinding.ActivityHome2Binding
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback


class HomeActivity : AppCompatActivity() {
    private val menuList: MutableList<MenuResponse> by lazy { ArrayList() }
    private val adapter by lazy { MyAdapter() }

    private lateinit var binding: ActivityHome2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHome2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRv()
        getMenuData()

        getData()

    }

    private fun setupRv() {
        binding.rv.layoutManager = GridLayoutManager(this, 3)
        binding.rv.adapter = adapter
    }

    private fun getMenuData() {
        menuList.add(MenuResponse(1,"Sync"))
        menuList.add(MenuResponse(2,"Order"))
        menuList.add(MenuResponse(3,"Attendance"))
//        menuList.add(MenuResponse(4,"Work Note"))
//        menuList.add(MenuResponse(5,"Collection"))

        adapter.updateItems(menuList)
    }

    private fun getData() {
        ApiInterface.create()
            .getData2(
                "API",
                "193.123.66.243",
                "1521",
                "IKRAM",
                "q",
                "UAE",
                "Select ITEM_ID,'https://images.sihirbox.com/bdp/Master/item/624ebf5d3a0b8.jpg' ITEM_NAME,DIST_SALE_PRICE from ppl1.item_master where item_name like '%2500%'"
            )
            .enqueue(object : Callback<ResponseHome> {
                override fun onResponse(
                    call: Call<ResponseHome>,
                    response: retrofit2.Response<ResponseHome>
                ) {
//                    list.clear()
//                    if (response.body() != null) {
//                        list.addAll(response.body()!!)
//                        val adapter = MyAdapter2(list, this@HomeActivity)
//                        binding.rv1.adapter = adapter
//                    }
                    Log.e("data221", "onResponse: $response")
                    Log.e("data558", "onResponse: ${response.body()}" )
                    val gson = Gson()
                    val json = gson.toJson(response.body())
                    Log.e("data558", "onResponse: $json")
                }

                override fun onFailure(call: Call<ResponseHome>, t: Throwable) {

                }
            })
    }


    private fun getMenuData1() {
        ApiInterface.create()
            .getData2(
                "API",
                "193.123.66.243",
                "1521",
                "IKRAM",
                "q",
                "UAE",
                "Select ITEM_ID,'https://images.sihirbox.com/bdp/Master/item/624ebf5d3a0b8.jpg' ITEM_NAME,DIST_SALE_PRICE from ppl1.item_master where item_name like '%2500%'"
            )
            .enqueue(object : Callback<ResponseHome> {
                override fun onResponse(
                    call: Call<ResponseHome>,
                    response: retrofit2.Response<ResponseHome>
                ) {
//                    list.clear()
//                    if (response.body() != null) {
//                        list.addAll(response.body()!!)
//                        val adapter = MyAdapter2(list, this@HomeActivity)
//                        binding.rv1.adapter = adapter
//                    }
                    Log.e("data221", "onResponse: $response")
                    Log.e("data558", "onResponse: ${response.body()}" )
                    val gson = Gson()
                    val json = gson.toJson(response.body())
                    Log.e("data558", "onResponse: $json")
                }

                override fun onFailure(call: Call<ResponseHome>, t: Throwable) {

                }
            })
    }



}