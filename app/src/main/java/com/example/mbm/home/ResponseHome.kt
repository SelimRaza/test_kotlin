package com.example.mbm.home
import com.google.gson.annotations.SerializedName

class ResponseHome : ArrayList<ResponseHome.ResponseItem>() {
    data class ResponseItem(
        @SerializedName("DIST_SALE_PRICE")
        val dISTSALEPRICE: String,
        @SerializedName("ITEM_ID")
        val iTEMID: String,
        @SerializedName("ITEM_NAME")
        val iTEMNAME: String,
        var isChecked: Boolean = false
//        var focData: FocData
    )
}
