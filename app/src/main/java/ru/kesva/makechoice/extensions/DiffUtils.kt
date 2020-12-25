package ru.kesva.makechoice.extensions

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.runWhenReady(action: () -> Unit) {
    val globalLayoutListener = object: ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            action()
            viewTreeObserver.removeOnGlobalLayoutListener(this)
        }
    }
    viewTreeObserver.addOnGlobalLayoutListener(globalLayoutListener)
}

fun Activity.showKeyboard() {
    Log.d("key", "showKeyboard: ")
    if (window != null) {
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(window.currentFocus, 0)
    } else {
        throw UnsupportedOperationException("Can't show keyboard. Activity's window is null.")
    }
}