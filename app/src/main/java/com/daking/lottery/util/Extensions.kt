package com.daking.lottery.util

import android.util.Log
import android.widget.Toast
import com.daking.lottery.app.App

fun Any.toast(msg: String?, length: Int = Toast.LENGTH_SHORT) {
    msg?.let { Toast.makeText(App.instance, msg, length).show() }
}

fun Any.toast(msg: Int, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(App.instance, msg, length).show()
}

fun Any.log(msg: String) {
    Log.d(this.javaClass.simpleName, msg)
}