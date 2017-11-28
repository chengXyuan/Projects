package com.daking.lottery.api


class NetRepository private constructor() {

    private val mApiStore: ApiStore = ApiClient.instance.getApiStore()

    private object Holder {
        val Instance = NetRepository()
    }

    companion object {
        val instance = Holder.Instance
    }
}