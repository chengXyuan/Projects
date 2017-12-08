package com.daking.lottery.util

import android.util.LruCache

class MemoryCacheManager {

    private var mMemoryCache: LruCache<String, Any>

    private object Holder {
        val INSTANCE = MemoryCacheManager()
    }

    companion object {
        val instance = Holder.INSTANCE
    }

    init {
        val size = Runtime.getRuntime().maxMemory() / 1024 / 8
        mMemoryCache = LruCache(size.toInt())
    }

    fun put(key: String, any: Any) {
        mMemoryCache.put(key, any)
    }

    fun get(key: String): Any? = mMemoryCache.get(key)
}
