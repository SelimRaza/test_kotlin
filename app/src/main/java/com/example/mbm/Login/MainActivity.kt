package com.example.mbm.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.mbm.SharedPreferences
import com.example.mbm.common.UploadDataWorker
import com.example.mbm.databinding.ActivityMainBinding
import com.example.mbm.home.HomeActivity
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mySharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mySharedPreferences= SharedPreferences(context = this)

        Log.e("TAG", "onCreate: " )

        Timber.e("Testing Timber: check")

        binding.emailSignInButton.setOnClickListener {
            mySharedPreferences.saveBaseUrl("http://193.123.66.243:5000/")
            val i = Intent(this, HomeActivity::class.java)
            startActivity(i)
        }



    }

    override fun onResume() {
        super.onResume()
        UploadDataWorker.start(this)
    }
}