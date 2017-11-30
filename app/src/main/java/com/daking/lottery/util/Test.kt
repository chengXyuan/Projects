package com.daking.lottery.util

import com.daking.lottery.api.NetRepository
import io.reactivex.Flowable

/**
 * Created by XiaoXin on 2017/11/30.
 * 描述：
 */

class Test {

    fun test() {
        NetRepository.instance.refreshAccount()
                .flatMap { (_, models) -> Flowable.just(models!![0]) }
    }
}
