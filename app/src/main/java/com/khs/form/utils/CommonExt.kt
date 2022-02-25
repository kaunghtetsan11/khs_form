package com.khs.form.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.map

//View
fun View.hideSoftKeyboard() {
    post {
        requestFocus()
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}

//Livedata
inline fun <X, Y> LiveData<X>.mapDistinct(
    crossinline transform: (X) -> Y
): LiveData<Y> = distinctUntilChanged().map(transform)