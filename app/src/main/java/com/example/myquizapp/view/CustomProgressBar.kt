package com.example.myquizapp.view

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import android.widget.ProgressBar
import androidx.core.graphics.drawable.toDrawable
import com.example.myquizapp.R

class CustomProgressBar(context: Context) : Dialog(context) {

    private var activity: Activity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.custom_progressbar)

        window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())

        val wlp = window?.attributes
        setCancelable(true)
        window?.attributes = wlp
        window?.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    fun setProgressBarColor(color: Int) {
        val progressBar = findViewById<ProgressBar>(R.id.progressbar_view)
        progressBar?.indeterminateDrawable?.setColorFilter(color, android.graphics.PorterDuff.Mode.SRC_IN)
    }

    fun adjustPositionForFragment() {
        activity?.let { act ->
            val window = window
            window?.let {
                val layoutParams = WindowManager.LayoutParams()
                layoutParams.copyFrom(it.attributes)
                layoutParams.gravity = Gravity.BOTTOM
                layoutParams.y = getNavigationBarHeight(act)
                it.attributes = layoutParams
            }
        }
    }

    private fun getNavigationBarHeight(activity: Activity): Int {
        val resourceId = activity.resources.getIdentifier("navigation_bar_height", "dimen", "android")
        return if (resourceId > 0) activity.resources.getDimensionPixelSize(resourceId) else 0
    }
}
