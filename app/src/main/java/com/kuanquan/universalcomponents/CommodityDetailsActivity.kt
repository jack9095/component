package com.kuanquan.universalcomponents

import android.support.v4.widget.NestedScrollView
import android.view.View
import com.base.library.base.BaseViewModelActivity
import com.base.library.utils.LogUtil
import com.kuanquan.universalcomponents.viewmodel.CommodityDetailsViewModel
import kotlinx.android.synthetic.main.category_list_commodity_details_activity.*

/**
 *  商品详情
 */
class CommodityDetailsActivity : BaseViewModelActivity<CommodityDetailsViewModel>() {



    override fun onClick(v: View?) {

    }

    override fun createViewModel(): CommodityDetailsViewModel {
        return createViewModel(this,CommodityDetailsViewModel::class.java)
    }

    override fun getLayoutId(): Int {
        return R.layout.category_list_commodity_details_activity
    }

    override fun initView() {
        super.initView()

        // 头部渐变
        headGradient()
    }

    var overallXScroll = 0
    val height = 640 // 滑动开始变色的高
    private fun headGradient() {
        title_bar?.background?.alpha = 0
        back_iv?.background?.alpha = 255
        share_iv?.background?.alpha = 255
        shop_good?.alpha = 0f
        shop_detail?.alpha = 0f
        detail_scroll_view.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            overallXScroll = scrollY // 累加y值 解决滑动一半y值为0
            when {
                overallXScroll <= 0 -> {  // 设置标题的背景颜色
                    title_bar?.background?.alpha = 0
                    back_iv?.background?.alpha = 255
                    share_iv?.background?.alpha = 255
                    shop_good?.alpha = 0f
                    shop_detail?.alpha = 0f
                    back_iv?.setImageResource(R.mipmap.detail_back_white)
                }
                overallXScroll in 1..height -> { // 滑动距离小于 banner 图的高度时，设置背景和字体颜色颜色透明度渐变
                    val scale = overallXScroll.toFloat() / height
                    val alpha = 255 * scale
                    LogUtil.e("CommodityDetailsActivity","alpha -> $alpha")
                    title_bar?.background?.alpha = alpha.toInt()
                    back_iv?.background?.alpha = 255 - alpha.toInt()
                    share_iv?.background?.alpha = 255 - alpha.toInt()
                    shop_good?.alpha = alpha / 500
                    shop_detail?.alpha = alpha / 500

                    if (alpha.toInt() < 100) {
                        back_iv?.setImageResource(R.mipmap.detail_back_white)
                    } else {
                        back_iv?.setImageResource(R.mipmap.detail_back_back)
                    }
                }
                else -> {
                    back_iv?.setImageResource(R.mipmap.detail_back_back)
                    title_bar?.background?.alpha = 255
                    back_iv?.background?.alpha = 0
                    share_iv?.background?.alpha = 0
                    shop_good?.alpha = 1f
                    shop_detail?.alpha = 1f
                }
            }
        })

    }

    override fun initData() {
    }

    override fun isBindEventBusHere(): Boolean {
        return false
    }

    override fun dataObserver() {
    }

}
