package com.daking.lottery.base

import com.daking.lottery.app.App
import com.daking.lottery.model.Root
import com.daking.lottery.util.Utils
import com.google.gson.JsonParseException
import io.reactivex.subscribers.DisposableSubscriber
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.UnknownHostException
import java.text.ParseException
import javax.net.ssl.SSLHandshakeException

abstract class NetSubscriber<T> : DisposableSubscriber<Root<T>>() {

    override fun onStart() {
        super.onStart()
        if (!Utils.isNetworkAvailable(App.instance)) {
            cancel()
            onFailure(9000, "网络异常，请检查网络连接后重试")
        }
    }

    override fun onNext(root: Root<T>) {
        with(root) {
            when (httpCode) {
                200 //请求成功
                        or 201 //更新成功
                        or 505//新增成功
                        or 507//删除成功
                        or 509//登出成功
                        or 515//登陆成功
                        or 526//投注成功
                        or 528//添加成功
                        or 530//提现成功
                        or 532//注册成功
                        or 534//账户可用
                -> onSuccess(httpCode, getMessage(), response)

                4001 -> {
                    onFailure(httpCode, getMessage())
                }

                else -> onFailure(httpCode, getMessage())
            }
        }
    }

    override fun onComplete() {

    }

    override fun onError(t: Throwable?) {
        when (t) {
            is HttpException -> when (t.code()) {
                401 -> onFailure(t.code(), "访问被服务器拒绝啦~")
                403 -> onFailure(t.code(), "服务器资源不可用")
                404 -> onFailure(t.code(), "我们好像迷路了，找不到服务器")
                408 -> onFailure(t.code(), "糟糕，请求超时了，请检查网络连接后重试")
                500 -> onFailure(t.code(), "服务器正在开小差，请稍后重试")
                502 -> onFailure(t.code(), "服务器正在开小差，请稍后重试")
                503 -> onFailure(t.code(), "服务器可能正在维护，请稍后重试")
                504 -> onFailure(t.code(), "糟糕，请求超时了，请检查网络连接后重试")
                else -> onFailure(t.code(), "网络异常，请检查网络连接后重试")
            }
            is JSONException -> onFailure(9001, "数据解析错误")
            is JsonParseException -> onFailure(9002, "数据解析错误")
            is ParseException -> onFailure(9003, "数据解析错误")
            is ConnectException -> onFailure(9004, "连接失败，网络连接可能存在异常，请检查网络后重试")
            is SSLHandshakeException -> onFailure(9005, "证书验证失败")
            is UnknownHostException -> onFailure(9006, "无法连接到服务器，请检查你的网络或稍后重试")
            else -> onFailure(9007, "出现了未知的错误")
        }
    }

    abstract fun onSuccess(code: Int, msg: String, data: List<T>?)

    abstract fun onFailure(code: Int, msg: String)
}