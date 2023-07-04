package com.example.mbm.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mbm.SharedPreferences
import com.example.mbm.common.UploadDataWorker
import com.example.mbm.databinding.ActivityMainBinding
import com.example.mbm.home.HomeActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mySharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mySharedPreferences= SharedPreferences(context = this)



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