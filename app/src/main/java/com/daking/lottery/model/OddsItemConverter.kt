package com.daking.lottery.model

import com.daking.lottery.util.JsonUtil
import io.objectbox.converter.PropertyConverter


class OddsItemConverter : PropertyConverter<OddsItem, String> {

    override fun convertToDatabaseValue(entityProperty: OddsItem?): String? {
        return JsonUtil.toJson(entityProperty)
    }

    override fun convertToEntityProperty(databaseValue: String?): OddsItem? {
        return JsonUtil.fromJson(databaseValue)
    }
}