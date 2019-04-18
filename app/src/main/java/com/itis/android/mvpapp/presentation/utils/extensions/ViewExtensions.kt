package com.itis.android.mvpapp.presentation.utils.extensions

import android.content.Context
import android.os.Build
import android.support.design.widget.Snackbar
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast

fun View.showKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    this.requestFocus()
    imm.showSoftInput(this, 0)
}

fun View.hideKeyboard(): Boolean {
    try {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        return inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    } catch (ignored: RuntimeException) {
    }
    return false
}

fun View.showSnackbar(snackbarText: String, timeLength: Int) {
    Snackbar.make(this, snackbarText, timeLength).show()
}

fun View.show(): View {
    if (visibility != View.VISIBLE) {
        visibility = View.VISIBLE
    }
    return this
}

inline fun View.showIf(condition: () -> Boolean): View {
    if (visibility != View.VISIBLE && condition()) {
        visibility = View.VISIBLE
    }
    return this
}

fun View.hide(): View {
    if (visibility != View.INVISIBLE) {
        visibility = View.INVISIBLE
    }
    return this
}

inline fun View.hideIf(condition: () -> Boolean): View {
    if (visibility != View.INVISIBLE && condition()) {
        visibility = View.INVISIBLE
    }
    return this
}

fun TextView.setTextStyle(context: Context, resId: Int) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
        this.setTextAppearance(context, resId)
    } else {
        this.setTextAppearance(resId)
    }
}

fun Context?.toast(text: CharSequence, duration: Int = Toast.LENGTH_LONG) = this?.let { Toast.makeText(it, text, duration).show() }