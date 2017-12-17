package com.daking.lottery.model

import com.google.gson.annotations.SerializedName

data class UserModel(var id: String,//id
                     var sessionId: String,//sessionId
                     var username: String,//用户名
                     var realName: String? = null,//真实姓名
                     @SerializedName("emailaddress") var email: String = "",//邮箱
                     var balance: Float = 0f,//余额
                     var isVisitor: Boolean = true//是否游客(试玩登录)
)