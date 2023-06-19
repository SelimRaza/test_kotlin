package com.example.mbm
import android.content.Context
import android.content.SharedPreferences


class SharedPreferences (context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

    fun saveBaseUrl(baseUrl: String) {
        val editor = sharedPreferences.edit()
        editor.putString("base_url", baseUrl)
        editor.apply()
    }

    fun getBaseUrl(): String? {
        return sharedPreferences.getString("base_url", null)
    }
}