package com.daking.lottery.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.daking.lottery.R

class AmountGridAdapter(val context: Context, private val amounts: List<String>) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val viewHolder: ViewHolder
        var view = convertView
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_rechange_amount, null)
            viewHolder = ViewHolder()
            viewHolder.tvAmount = view!!.findViewById(R.id.tv_amount)
            view.tag = viewHolder
        } else {
            viewHolder = view.tag as ViewHolder
        }
        viewHolder.tvAmount.text = amounts[position]
        return view
    }

    override fun getItem(p0: Int) = amounts[p0]

    override fun getItemId(p0: Int) = p0.toLong()

    override fun getCount() = amounts.size

    internal inner class ViewHolder {
        lateinit var tvAmount: TextView
    }
}