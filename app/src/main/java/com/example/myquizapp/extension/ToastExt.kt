package com.example.myquizapp.extension

import android.app.Activity
import android.content.Context
import android.widget.Toast


fun Context.toast(message: String?) {
    if (!message.isNullOrEmpty() && message.isNotBlank()) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

fun Activity.toast(message: String?) {
    if (!message.isNullOrEmpty() && message.isNotBlank()) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}





