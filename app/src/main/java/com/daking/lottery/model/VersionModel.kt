package com.daking.lottery.model

import com.google.gson.annotations.SerializedName

data class VersionModel(@SerializedName("forcedupdate") val forceUpdate: Int,
                        @SerializedName("versionnum") val versionName: String,
                        @SerializedName("versionurl") val versionUrl: String)