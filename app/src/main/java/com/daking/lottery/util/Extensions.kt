package com.daking.lottery.util

import android.util.Log
import android.widget.Toast
import com.daking.lottery.app.App
import com.daking.lottery.app.Constant
import java.util.regex.Pattern

fun Any.toast(msg: String?, length: Int = Toast.LENGTH_SHORT) {
    msg?.let { Toast.makeText(App.instance, msg, length).show() }
}

fun Any.toast(msg: Int, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(App.instance, msg, length).show()
}

fun Any.log(msg: String) {
    Log.d(this.javaClass.simpleName, msg)
}

fun String.isUserName() = Pattern.matches(Constant.REGEX_USERNAME, this)

fun String.isPassword() = Pattern.matches(Constant.REGEX_PASSWORD, this)

fun String.isRealName() = Pattern.matches(Constant.REGEX_REAL_NAME, this)
