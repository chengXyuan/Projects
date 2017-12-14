package com.daking.lottery.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class BankModel(val id: Int,
                     @SerializedName("bankname") val bankName: String,
                     @SerializedName("bankaccount") val bankAccount: String,
                     val balance: Double) : Serializable
