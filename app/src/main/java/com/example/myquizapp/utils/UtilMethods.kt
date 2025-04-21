package com.example.myquizapp.utils

import android.content.Context
import android.text.Html
import android.text.Spanned
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.myquizapp.R
import com.google.android.material.snackbar.Snackbar

object UtilMethods {

    /*snack bar to show alert*/
    fun showSnackBar(context: Context, view: View?, msg: String?) {
        try {
            if (view != null) {
                val snackbar = Snackbar.make(view, msg!!, Snackbar.LENGTH_SHORT)
                val snackbarView: View = snackbar.view
                val textView: TextView =
                    snackbarView.findViewById(com.google.android.material.R.id.snackbar_text)
                textView.maxLines = 5
                textView.textSize = 15f
                val margin = 20
                textView.setTextColor(ContextCompat.getColor(context, R.color.white))
                snackbarView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorRed))
                val params = snackbar.view.layoutParams as FrameLayout.LayoutParams
                params.setMargins(margin, margin, margin, margin)
                params.gravity = Gravity.TOP
                snackbar.view.layoutParams = params

                snackbar.show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun fromHtml(html: String): Spanned {
        return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(html)
        }
    }

}