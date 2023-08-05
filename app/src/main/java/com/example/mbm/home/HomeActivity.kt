package com.example.mbm.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mbm.ApiInterface
import com.example.mbm.Constants
import com.example.mbm.speedMeter.SpeedMeterActivity
import com.example.mbm.databinding.ActivityHome2Binding
import com.example.mbm.order.view.OrderActivity
import com.example.mbm.outletEntry.view.OutletNavActivity
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
        //getMenuData()
        getMenuData1()
       // adapter.updateItems(menuList)
        //getData()






    }

    private fun setupRv() {
        binding.rv.layoutManager = GridLayoutManager(this, 3)
        binding.rv.adapter = adapter
        adapter.onItemClickListener = {
            Log.e("onItemClickListener", "setupRv: "+ it.id)
            if (it.id == Constants.MENU_ORDER_ID) {
                val i = Intent(this, OrderActivity::class.java)
                startActivity(i)
            }
            else if (it.id == Constants.MENU_DELIVERY) {
                val i = Intent(this, OutletNavActivity::class.java)
                startActivity(i)
            }
        }
    }



    private fun getMenuData1() {
        ApiInterface.create()
            .getMenu(
                "API",
                "193.123.66.243",
                "1521",
                "IKRAM",
                "q",
                "UAE",
                "SELECT FC1.T_SCRT.SCRT_MENU id ,FC1.T_OBJT.OBJT_DESC menuName FROM FC1.T_SCRT,FC1.T_OBJT WHERE  FC1.T_SCRT.SCRT_MENU= FC1.T_OBJT.OBJT_TEXT  AND SCRT_USER='IKRAM' and SCRT_ACVT='0' AND OBJT_OWNR=1"
            )
            .enqueue(object : Callback<List<MenuResponse>> {
                override fun onResponse(
                    call: Call<List<MenuResponse>>,
                    response: retrofit2.Response<List<MenuResponse>>
                ) {
//                    menuList.clear()
                    if (response.body() != null) {
                        adapter.updateItems(response.body()!!)

                    }

                    Log.e("wdata221", "onResponse: $response")
                    Log.e("wdata558", "onResponse: ${response.body()}" )
                    val gson = Gson()
                    val json = gson.toJson(response.body())
                    Log.e("wdata558", "onResponse: $json")
                }

                override fun onFailure(call: Call<List<MenuResponse>>, t: Throwable) {
                    Log.e("wdata558", "onResponse: ${t.localizedMessage}")
                }
            })
    }



}