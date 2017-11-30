package com.daking.lottery.model

import com.google.gson.annotations.SerializedName

data class UserModel(var id: String,//id
                     var sessionId: String,//sessionId
                     var username: String,//用户名
                     @SerializedName("realname") var realName: String,//真实姓名
                     @SerializedName("emailaddress") var email: String? = null,//邮箱
                     var balance: Double = 0.toDouble(),//余额
                     var isVisitor: Boolean = true//是否游客(试玩登录)
)
