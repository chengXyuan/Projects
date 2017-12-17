package com.daking.lottery.util

import android.util.Log
import android.widget.Toast
import com.daking.lottery.app.App
import com.daking.lottery.app.Constant
import io.reactivex.Flowable
import java.util.concurrent.TimeUnit
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

fun Float.format() = String.format("%.2f", this)

fun <T> Flowable<T>.retryWithDelay(maxCount: Int = 0, millisDelay: Long): Flowable<T> {
    return retryWithDelay { _, count -> if (maxCount > 0 && count == maxCount) null else millisDelay }
}

fun <T> Flowable<T>.retryWithDelay(timeMillisProvider: (Throwable, Int) -> Long?): Flowable<T> {
    return this.retryWhen { error ->
        var count = 0
        error.flatMap { th ->
            val millis = timeMillisProvider.invoke(th, ++count) ?: return@flatMap Flowable.error<T>(th)
            if (millis < 0) return@flatMap Flowable.error<T>(th)
            Flowable.timer(millis, TimeUnit.MILLISECONDS)
        }
    }
}