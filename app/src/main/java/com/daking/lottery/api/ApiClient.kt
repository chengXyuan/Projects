package com.daking.lottery.api

import com.daking.lottery.BuildConfig
import com.daking.lottery.app.App
import com.daking.lottery.app.Constant
import com.daking.lottery.util.Utils
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient private constructor() {

    companion object {
        const val TIMEOUT = 20L
        val instance = Holder.Instance
    }

    private var mRetrofit: Retrofit

    private object Holder {
        val Instance = ApiClient()
    }

    init {
        mRetrofit = Retrofit.Builder()
                .client(constructClient())
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    private fun constructClient(): OkHttpClient {

        val loggerInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) loggerInterceptor.level = HttpLoggingInterceptor.Level.BODY
        else loggerInterceptor.level = HttpLoggingInterceptor.Level.NONE

        return OkHttpClient.Builder()
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(loggerInterceptor)
                .addInterceptor(getInterceptor())
                .addNetworkInterceptor(CacheInterceptor())
                .retryOnConnectionFailure(true)
                .build()
    }

    private fun getInterceptor(): Interceptor {
        return Interceptor { chain ->
            var request = chain.request()
            if (!Utils.isNetworkAvailable(App.instance)) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build()
            }
            chain.proceed(request)
        }
    }

    fun getApiStore(): ApiStore = mRetrofit.create(ApiStore::class.java)!!
}