package com.daking.lottery.ui.activity

import android.os.Bundle
import com.daking.lottery.R
import com.daking.lottery.base.BaseActivity
import com.daking.lottery.ui.fragment.RecordDetailFragment
import com.daking.lottery.util.FormatUtils
import kotlinx.android.synthetic.main.activity_record_of_date.*

class RecordOfDateActivity : BaseActivity() {

    companion object {
        val EXTRA_STATUS = "extra_type"
        val EXTRA_TIMESTAMP = "extra_timestamp"
    }

    override fun getLayoutResId() = R.layout.activity_record_of_date

    override fun initData(savedInstanceState: Bundle?) {
        val status = intent.getIntExtra(EXTRA_STATUS, 0)
        val timestamp = intent.getLongExtra(EXTRA_TIMESTAMP, 0L)
        val type = if (status == 0) RecordDetailFragment.TYPE_UNSETTLED else RecordDetailFragment.TYPE_SETTLED
        val fragment = RecordDetailFragment.newInstance(type, timestamp)
        tvDate.text = FormatUtils.instance.convertDate(timestamp * 1000)
        supportFragmentManager.beginTransaction()
                .replace(R.id.flContainer, fragment).commit()
    }
}
