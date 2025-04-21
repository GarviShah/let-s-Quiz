package com.example.myquizapp.extension

import android.os.Handler
import android.os.Looper
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.example.myquizapp.R

// Extension function for Activity to implement double-tap-to-exit
fun AppCompatActivity.onDoubleTapToExit() {
    var doubleBackToExitPressedOnce = false

    // Add a callback to handle the back button press
    val backPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (doubleBackToExitPressedOnce) {
                finishAffinity() // Exit the activity
            } else {
                doubleBackToExitPressedOnce = true
                // Show a message to inform the user
                toast(getString(R.string.plz_back_again_to_exit)) // Change message as needed

                // Reset the flag after the delay
                Handler(Looper.getMainLooper()).postDelayed({
                    doubleBackToExitPressedOnce = false
                }, 2000) // Delay in milliseconds (2 seconds)
            }
        }
    }

    // Register the callback with the backPressedDispatcher
    this.onBackPressedDispatcher.addCallback(this, backPressedCallback)
}










