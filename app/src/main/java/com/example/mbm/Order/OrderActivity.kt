package com.example.mbm.Order

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mbm.R
import com.example.mbm.databinding.ActivityOrderBinding

class OrderActivity : AppCompatActivity() {

    private lateinit var binding:ActivityOrderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}