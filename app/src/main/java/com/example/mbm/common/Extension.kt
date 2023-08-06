package com.example.mbm.common

import android.widget.Toast
import com.example.mbm.MyApp


fun String.toast() {
    Toast.makeText(MyApp.context, this,Toast.LENGTH_SHORT).show()
}
