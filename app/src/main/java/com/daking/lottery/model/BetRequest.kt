package com.daking.lottery.model

import com.daking.lottery.util.AccountHelper

class BetRequest(
        var round: String,
        var money: String,
        var countPay: Int,
        var plays: List<BetItem>,
        var usersId: String? = AccountHelper.instance.getUserId(),
        var sessionId: String? = AccountHelper.instance.getSessionId())

