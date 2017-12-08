package com.daking.lottery.model

/*
* {
*     "backwater": 0.01,
*     "name": "大",
*     "odds": 1.99,
*     "key": "666"
* }*/
data class OddsItem(val name: String,
                    val odds: String,
                    val backwater: String,
                    val key: String)