package com.daking.lottery.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.daking.lottery.R
import com.daking.lottery.model.OnlinePayChannel

class ChannelGridAdapter(val context: Context, private val channels: List<OnlinePayChannel>) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val viewHolder: ViewHolder
        var view = convertView
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_pay_channel, null)
            viewHolder = ViewHolder()
            viewHolder.tvChannel = view!!.findViewById(R.id.tv_pay_channel)
            view.tag = viewHolder
        } else {
            viewHolder = view.tag as ViewHolder
        }
        viewHolder.tvChannel.text = channels[position].name
        return view
    }

    override fun getItem(p0: Int) = channels[p0]

    override fun getItemId(p0: Int) = p0.toLong()

    override fun getCount() = channels.size

    internal inner class ViewHolder {
        lateinit var tvChannel: TextView
    }
}
