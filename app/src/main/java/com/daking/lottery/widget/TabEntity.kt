package com.daking.lottery.widget

import com.flyco.tablayout.listener.CustomTabEntity


class TabEntity(val title: String, val selectedResId: Int, val unselectedResId: Int) : CustomTabEntity {

    override fun getTabTitle() = title

    override fun getTabSelectedIcon() = selectedResId

    override fun getTabUnselectedIcon() = unselectedResId
}