package com.daking.lottery.model

import com.google.gson.annotations.SerializedName

data class Promotion(val title: String,
                     @SerializedName("imageurl") val imageUrl: String,
                     @SerializedName("weburl") val webUrl: String)