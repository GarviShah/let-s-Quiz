package com.example.myquizapp.extension

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadGif(url: Int) {
    Glide.with(this.context)
        .asGif()
        .load(url)
        .into(this)
}
