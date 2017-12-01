package com.daking.lottery.widget

import android.support.annotation.DrawableRes
import com.flyco.tablayout.listener.CustomTabEntity


class TabEntity(val title: String,
                @DrawableRes private val selectedResId: Int,
                @DrawableRes private val unselectedResId: Int) : CustomTabEntity {

    override fun getTabTitle() = title

    override fun getTabSelectedIcon() = selectedResId

    override fun getTabUnselectedIcon() = unselectedResId
}