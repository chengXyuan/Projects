package com.daking.lottery.model

import com.chad.library.adapter.base.entity.MultiItemEntity
import io.objectbox.annotation.Convert
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class MultiBetItem(val gameCode: Int,
                        val typeCode: String,
                        private val itemType: Int,
                        val spanSize: Int,
                        val typeName: String,
                        var isSelected: Boolean = false,
                        val number: Int = 0,
                        @Convert(converter = OddsItemConverter::class, dbType = String::class) var betItem: OddsItem? = null,
                        @Id var id: Long = 0) : MultiItemEntity, Comparable<MultiBetItem> {

    override fun getItemType() = itemType

    override fun compareTo(other: MultiBetItem): Int {
        return if (betItem == null) {
            this.number - other.number
        } else {
            this.betItem!!.key.toInt() - other.betItem!!.key.toInt()
        }
    }
}
