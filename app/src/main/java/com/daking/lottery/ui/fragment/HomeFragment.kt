package com.daking.lottery.ui.fragment

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.daking.lottery.R
import com.daking.lottery.base.BaseMVPFragment
import com.daking.lottery.dialog.MainMenuPopupWindow
import com.daking.lottery.dialog.easy.HorizontalGravity
import com.daking.lottery.dialog.easy.VerticalGravity
import com.daking.lottery.model.BannerModel
import com.daking.lottery.model.UserModel
import com.daking.lottery.ui.activity.RegisterActivity
import com.daking.lottery.ui.adapter.HomeGameAdapter
import com.daking.lottery.ui.iview.IHomeView
import com.daking.lottery.ui.presenter.HomePresenter
import com.daking.lottery.widget.banner.BannerView
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.startActivity

class HomeFragment : BaseMVPFragment<HomePresenter>(), IHomeView {

    private lateinit var mBannerView: BannerView<BannerModel>
    private var menuPopup: MainMenuPopupWindow? = null

    override fun getLayoutResId() = R.layout.fragment_home

    override fun initData(savedInstanceState: Bundle?) {
        mBannerView = mRootView!!.findViewById(R.id.banner_view)
        refresh_layout.setOnRefreshListener {
            refresh_layout.isRefreshing = true
            mPresenter.requestData()
        }
        tv_to_register.setOnClickListener { context.startActivity<RegisterActivity>() }
        iv_menu.setOnClickListener { showMenuPopup() }
    }

    override fun refreshUser(user: UserModel?) {
        if (user == null) {
            tv_user_name.visibility = View.GONE
            tv_to_register.visibility = View.VISIBLE
        } else user.let {
            tv_user_name.visibility = View.VISIBLE
            tv_user_name.text = user.username
            if (user.isVisitor) {
                tv_to_register.visibility = View.VISIBLE
            } else {
                tv_to_register.visibility = View.GONE
            }
        }
    }

    override fun showGames(gameAdapter: HomeGameAdapter) {
        rv_lottery.setHasFixedSize(true)
        rv_lottery.isNestedScrollingEnabled = false
        rv_lottery.layoutManager = GridLayoutManager(context, 3)
        rv_lottery.adapter = gameAdapter
    }

    override fun showBanner(data: List<BannerModel>) {
        mBannerView.setViewFactory { item, _, container ->
            val realItem = item as BannerModel
            val imageView = ImageView(container.context)
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            Glide.with(context)
                    .load(realItem.imageUrl)
                    .into(imageView)
            imageView
        }
        mBannerView.setDataList(data)
        mBannerView.start()
    }

    override fun onComplete() {
        if (refresh_layout != null && refresh_layout.isRefreshing) {
            refresh_layout.isRefreshing = false
        }
    }

    private fun showMenuPopup() {
        if (menuPopup == null) {
            menuPopup = MainMenuPopupWindow(activity).createPopup()
        }
        menuPopup!!.showAtAnchorView(fl_title_bar, VerticalGravity.BELOW, HorizontalGravity.ALIGN_RIGHT)
    }
}
