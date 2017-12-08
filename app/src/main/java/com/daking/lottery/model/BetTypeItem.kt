package com.daking.lottery.model

import com.chad.library.adapter.base.entity.MultiItemEntity
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class BetTypeItem(val gameCode: Int,
                       private val itemType: Int,
                       val spanSize: Int,
                       var typeName: String,
                       var odds: String? = null,
                       var key: String? = null,
                       var backwater: String? = null,
                       var selected: Boolean = false,
                       @Id var id: Long = 0) : MultiItemEntity {

    override fun getItemType() = itemType
}