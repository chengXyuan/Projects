package com.daking.lottery.model

import com.google.gson.annotations.SerializedName

data class VersionModel(@SerializedName("forcedupdate") val forceUpdate: Int,//是否强制更新 0 强制更新，1否
                        @SerializedName("versionnum") val versionName: String,
                        @SerializedName("versionurl") val versionUrl: String)