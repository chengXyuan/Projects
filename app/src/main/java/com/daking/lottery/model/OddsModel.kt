package com.daking.lottery.model

data class OddsModel(val name: String,
                     var duplex: String?,
                     val list: List<OddsItem>)