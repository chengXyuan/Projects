package com.daking.lottery.model

import com.google.gson.annotations.SerializedName

data class FundingModel(val id: Int,
                        @SerializedName("billcode") val billCode: String,
                        val orderStatus: Int,
                        val money: Float,
                        val remark: String,
                        val createdTime: Long,
                        val type: Int,
                        val status: Int)