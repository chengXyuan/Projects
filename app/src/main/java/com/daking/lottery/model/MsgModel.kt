package com.daking.lottery.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MsgModel(val content: String,
                    @SerializedName("updatetime") var updateTime: Long = 0L,
                    @SerializedName("showupdatetime") var timeText: String = "") : Serializable