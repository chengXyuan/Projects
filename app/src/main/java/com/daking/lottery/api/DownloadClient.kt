package com.daking.lottery.api

import com.daking.lottery.app.App
import com.daking.lottery.app.Constant
import com.daking.lottery.util.Utils
import com.international.wtw.lottery.api.ProgressInterceptor
import com.international.wtw.lottery.event.ProgressListener
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class DownloadClient(private val listener: ProgressListener) {
    private var mRetrofit: Retrofit

    companion object {
       private const val TIMEOUT = 20L
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
        return OkHttpClient.Builder()
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(ProgressInterceptor(listener))
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
