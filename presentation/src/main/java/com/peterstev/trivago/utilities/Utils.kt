package com.peterstev.trivago.utilities

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun String.formatNumber(): String {
    return try {
        "%,d".format(this.toInt())
    } catch (e: NumberFormatException) {
        ""
    }
}

fun <T> LiveData<T>.observeOnce(observer: (T) -> Unit) {
    observeForever(object : Observer<T> {
        override fun onChanged(value: T) {
            val resource = value as Resource<*>
            if (resource.data != null) {
                observer(value)
                removeObserver(this)
            }
        }
    })
}