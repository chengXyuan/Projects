package com.daking.lottery.model

import com.chad.library.adapter.base.entity.MultiItemEntity

data class MultiBetItem(val gameCode: Int,
                        val typeCode: String,
                        private val itemType: Int,
                        val spanSize: Int,
                        val typeName: String,
                        var isSelected: Boolean = false,
                        val number: Int = 0,
                        var betItem: OddsItem? = null,
                        var id: Long = 0) : MultiItemEntity, Comparable<MultiBetItem> {

    override fun getItemType() = itemType

    override fun compareTo(other: MultiBetItem): Int {
        return if (betItem == null) {
            this.number - other.number
        } else {
            this.betItem!!.key.toInt() - other.betItem!!.key.toInt()
        }
    }
}
