package com.daking.lottery.model

import com.google.gson.annotations.SerializedName

data class UserModel(val id: Int,
                     val sessionId: String,
                     val username: String,
                     @SerializedName("realname") val realName: String,
                     @SerializedName("emailaddress") val email: String = "",
                     val balance: Double = 0.toDouble())
