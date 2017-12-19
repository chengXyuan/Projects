package com.daking.lottery.ui.adapter

import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.daking.lottery.R
import com.daking.lottery.model.Promotion
import com.daking.lottery.util.RoundedCornersTransformation
import com.daking.lottery.util.Utils
import kotlinx.android.synthetic.main.item_promotion.view.*


class PromotionAdapter : BaseQuickAdapter<Promotion, BaseViewHolder>(R.layout.item_promotion) {

    override fun convert(helper: BaseViewHolder, item: Promotion) {
        Glide.with(mContext)
                .load(item.imageUrl)
                .apply(
                        RequestOptions()
                                .placeholder(R.drawable.img_empty)
                                .transform(RoundedCornersTransformation(Utils.dp2px(5), 0, RoundedCornersTransformation.CornerType.TOP))
                                .error(R.drawable.img_error)
                )
                .into(helper.itemView.ivPromotionImg)
        helper.itemView.tvTitle.text = item.title
    }
}