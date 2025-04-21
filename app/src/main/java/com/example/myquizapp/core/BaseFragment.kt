package com.example.myquizapp.core

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.example.myquizapp.view.CustomProgressBar
import dagger.hilt.android.AndroidEntryPoint

/**
 * This is an abstract base class for all fragments in the application.
 * It implements the OnClickListener interface and provides a layoutId parameter with a default value of 0.
 * The layoutId is used to inflate the layout for the fragment.
 *
 * @param layoutId The resource ID of the layout file to inflate. Defaults to 0.
 */
@AndroidEntryPoint
abstract class BaseFragment(@LayoutRes layoutId: Int = 0) : Fragment(), View.OnClickListener {

    // private var customProgressDialog: CustomProgressDialog? = null
    private var customProgressDialog: CustomProgressBar? = null
    lateinit var mContext: Context

    companion object {
        private const val MIN_CLICK_INTERVAL: Long = 1000
    }

    private var lastClickTime: Long = 0

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mContext = context
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
     * This is an abstract function that needs to be implemented in any class that extends BaseFragment.
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
            customProgressDialog?.adjustPositionForFragment()
            customProgressDialog?.setCancelable(false)
            customProgressDialog?.setCanceledOnTouchOutside(false)
            customProgressDialog?.show()

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

    protected fun hideKeyBoard(context: Activity) {

        val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = context.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(context)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)

    }

    fun showKeyboard(view: View) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }

    /**
     * show progress dialog
     * @param context context of activity
     */
    /*protected fun showProgress(context: Context) {
        try {
            if (customProgressDialog != null && customProgressDialog!!.isShowing) {
                return
            }else
            {
                customProgressDialog = CustomProgressDialog(context)
                customProgressDialog?.show()
            }


        } catch (e: Exception) {
            e.printStackTrace()
        }
    }*/

    /**
     * hide progress dialog
     */
    /*protected fun hideProgress() {
        try {
            if (customProgressDialog != null) {
                if (customProgressDialog!!.isShowing) {
                    customProgressDialog!!.dismiss()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }*/
}