package com.daking.lottery.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.util.SparseArray
import android.view.ViewGroup
import com.daking.lottery.ui.fragment.RecordDetailFragment
import com.daking.lottery.ui.fragment.RecordSummaryFragment

class BetRecordAdapter(fm: FragmentManager, private val titles: Array<String>) : FragmentPagerAdapter(fm) {

    private val mFragments: SparseArray<Fragment?> = SparseArray()

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = mFragments.get(position)
        if (fragment == null) {
            when (position) {
                0 -> fragment = RecordSummaryFragment()
                1 -> fragment = RecordDetailFragment.newInstance(RecordDetailFragment.TYPE_UNSETTLED)
                2 -> fragment = RecordDetailFragment.newInstance(RecordDetailFragment.TYPE_SETTLED)
            }
            mFragments.put(position, fragment)
        }
        return fragment!!
    }

    override fun getCount() = titles.size

    override fun getPageTitle(position: Int): CharSequence {
        return titles[position]
    }

    override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
        //super.destroyItem(container, position, `object`)
    }
}