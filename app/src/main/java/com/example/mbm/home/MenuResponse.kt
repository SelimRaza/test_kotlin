package com.example.mbm.home

import com.google.gson.annotations.SerializedName

data class MenuResponse(
    @SerializedName("ID")
    var id: Int,
    @SerializedName("MENUNAME")
    var menuName: String
)
