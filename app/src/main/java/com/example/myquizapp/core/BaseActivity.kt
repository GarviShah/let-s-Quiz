package com.example.myquizapp.core

import android.app.Activity
import android.app.Dialog
import android.content.ComponentCallbacks2
import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.util.LruCache
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.myquizapp.R
import com.example.myquizapp.data.viewmodels.CommonViewModel
import com.example.myquizapp.view.CustomProgressBar
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Tops on 4/27/2018.notifyItemRemoved
 */

@AndroidEntryPoint
abstract class BaseActivity : AppCompatActivity(), View.OnClickListener {
    private var customProgressDialog: CustomProgressBar? = null
    val commonViewModel: CommonViewModel by viewModels()


    companion object {
        private const val MIN_CLICK_INTERVAL: Long = 600
    }

    private var lastClickTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    override fun onClick(v: View) {
        if (isSafeClick()) {
            onSafeClick(v)
        }
    }

    private fun isSafeClick(): Boolean {
        val currentClickTime = SystemClock.uptimeMillis()
        val elapsedTime = currentClickTime - lastClickTime
        lastClickTime = currentClickTime

        return elapsedTime > MIN_CLICK_INTERVAL
    }

    /**
     * This is an abstract function that needs to be implemented in any class that extends BaseActivity.
     * It is called when a view is clicked and the click is considered "safe" (i.e., not a rapid, accidental double click).
     *
     * @param v The view that was clicked.
     */
    abstract fun onSafeClick(v: View)

    /**
     * show progress dialog
     */
    fun showProgress(context: Context) {
        try {
            if (customProgressDialog != null && customProgressDialog!!.isShowing) {
                return
            }
            customProgressDialog = CustomProgressBar(context)
            customProgressDialog?.setCancelable(false)
            customProgressDialog?.setCanceledOnTouchOutside(false)
            customProgressDialog?.show()
            customProgressDialog?.setProgressBarColor(
                ContextCompat.getColor(
                    this,
                    R.color.white
                )
            )

            Handler(Looper.getMainLooper()).postDelayed(
                {
                    hideProgress()
                }, 30000
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * hide progress dialog
     */
    fun hideProgress() {
        try {
            if (customProgressDialog != null && customProgressDialog!!.isShowing) {
                customProgressDialog!!.dismiss()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}