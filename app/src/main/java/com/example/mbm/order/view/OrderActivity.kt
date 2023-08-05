package com.example.mbm.order.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.mbm.R
import com.example.mbm.databinding.ActivityOrderBinding
import com.example.mbm.order.viewModel.OrderVM

class OrderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderBinding
    private val viewModel by lazy { ViewModelProvider(this)[OrderVM::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.init()
        viewModel.getData()

    }
}