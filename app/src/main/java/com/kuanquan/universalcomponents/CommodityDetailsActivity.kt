package com.kuanquan.universalcomponents

import android.content.Intent
import android.graphics.Typeface
import android.support.design.widget.TabItem
import android.support.design.widget.TabLayout
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.LinearLayoutManager
import android.text.Html
import android.view.View
import android.widget.TextView
import com.base.library.base.BaseViewModelActivity
import com.base.library.utils.CollectionsUtil
import com.base.library.utils.LogUtil
import com.base.library.utils.glide.invocation.ImageLoaderManager
import com.kuanquan.universalcomponents.adapter.UserEvaluationAdapter
import com.kuanquan.universalcomponents.bean.BannerBean
import com.kuanquan.universalcomponents.viewmodel.CommodityDetailsViewModel
import com.kuanquan.universalcomponents.widget.ViewPagerIndicator
import kotlinx.android.synthetic.main.category_list_commodity_details_activity.*

/**
 *  商品详情
 *  https://www.jianshu.com/p/88679fed9ecb   TabLayout 详解
 */
class CommodityDetailsActivity : BaseViewModelActivity<CommodityDetailsViewModel>(), ViewPagerIndicator.OnPageClickListener {
    override fun onPageClick(info: BannerBean?) {
        val intent = Intent(this,CommodityDetailsActivity::class.java)
        startActivity(intent)
    }

    lateinit var mUserEvaluationAdapter: UserEvaluationAdapter

    override fun getLayoutId(): Int {
        return R.layout.category_list_commodity_details_activity
    }

    override fun createViewModel(): CommodityDetailsViewModel {
        return createViewModel(this, CommodityDetailsViewModel::class.java)
    }

    override fun initView() {
        super.initView()
        addOnClickListeners(this, R.id.back_iv, R.id.shop_good, R.id.shop_detail, R.id.share_iv)
        // 头部渐变
        headGradient()

        tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {
                //再次选中tab的逻辑
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // 未选中tab的逻辑
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                // 选中了tab的逻辑
                when(tab?.position){
                    0 -> {
                        selectedHotGoods(0)
                    }
                    1 -> {
                        selectedHotGoods(1)
                    }
                }
            }
        })

//        val tab = tab_layout.newTab()
//        val textView = TextView(this)
//        textView.text = "大家都在看"
//        tab.customView = textView
//        (tab.customView as TextView).isSelected = true
//        tab.customView?.setOnClickListener {
//            LogUtil.e("点击1")
//        }
//        tab_layout.addTab(tab)
//
//        val tabItem = tab_layout.newTab()
//        val textViewItem = TextView(this)
//        textViewItem.text = "24小时热销"
//        tabItem.customView = textViewItem
//        (tabItem.customView as TextView).isSelected = false
//        tabItem.customView?.setOnClickListener {
//            LogUtil.e("点击2")
//        }
//        tab_layout.addTab(tabItem)
    }

    val height = 640 // 滑动开始变色的高
    private fun headGradient() {
        title_bar?.background?.alpha = 0
        back_iv?.background?.alpha = 255
        share_iv?.background?.alpha = 255
        shop_good?.alpha = 0f
        shop_detail?.alpha = 0f
        detail_scroll_view.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            when {
                scrollY <= 0 -> {
                    title_bar?.background?.alpha = 0
                    back_iv?.background?.alpha = 255
                    share_iv?.background?.alpha = 255
                    shop_good?.alpha = 0f
                    shop_detail?.alpha = 0f
                    back_iv?.setImageResource(R.mipmap.detail_back_white)
                }
                scrollY in 1..height -> { // 滑动距离小于 banner 图的高度时，设置背景和字体颜色颜色透明度渐变
                    val scale = scrollY.toFloat() / height
                    val alpha = 255 * scale
                    LogUtil.e("CommodityDetailsActivity", "alpha -> $alpha")
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

            if (scrollY >= 1650) {
                detailGood()
            } else {
                shopGood()
            }
        })
    }

    override fun initData() {
        details_top_ban.setData(mViewModel.topBannerData())
        CollectionsUtil.setTextView(details_price,"¥" + "")
        CollectionsUtil.setTextView(details_price,"返分价：¥" + "135.00")
        member_tv?.text = Html.fromHtml("尊享会员专享价" + "<font color= '#FF8300'>" + "¥42.90" + "</font>" + "立省" + "<font color= '#FF8300'>" + "¥36.00" + "</font>")
        CollectionsUtil.setTextView(title_tv, "威露士洗衣液多效3kg+精华内衣净300g")
        CollectionsUtil.setTextView(content_tv, "深层洁净，温和护肤")
        CollectionsUtil.setTextView(special_note, "特殊说明，特殊说明,特殊说明")
        CollectionsUtil.setTextView(user_evaluation, "用户评价（72）")
        CollectionsUtil.setTextView(user_praise, "98.6%好评")

        // 用户评价
        evaluation_recycler_view.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        mUserEvaluationAdapter = UserEvaluationAdapter(mViewModel.userList())
        evaluation_recycler_view.adapter = mUserEvaluationAdapter

        selectedHotGoods(0)

        val imageUrl = "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1056731044,2207586648&fm=26&gp=0.jpg"
        ImageLoaderManager.getInstance().displayImageNetUrl(this, imageUrl, R.mipmap.ic_launcher, long_picture)

    }

    fun selectedHotGoods(type: Int){
        if (type == 0) { // 大家都在看
            pager_indicator.setData(mViewModel.userHot(), this)
            indicator_view.bindWithViewPager(pager_indicator, mViewModel.userHot().size)
        } else if (type == 1) {  // 24小时热销
            pager_indicator.setData(mViewModel.userSell(), this)
            indicator_view.bindWithViewPager(pager_indicator, mViewModel.userSell().size)
        }
        indicator_view.currentPosition = 0
    }

    override fun isBindEventBusHere(): Boolean {
        return false
    }

    override fun dataObserver() {
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.back_iv -> finish()
            R.id.share_iv -> {  // 分享

            }
            R.id.shop_good -> {  // 商品
                shopGood()
                detail_scroll_view.fling(0)
                detail_scroll_view.smoothScrollTo(0, 0)
            }
            R.id.shop_detail -> {  // 详情
                detailGood()
                detail_scroll_view.fling(1650)
                detail_scroll_view.smoothScrollTo(0, 1650)
            }
        }
    }

    fun shopGood(){
        val drawable = resources.getDrawable(R.drawable.shape_indicator)
        //第一0是距左边距离，第二0是距上边距离，25分别是长宽
        drawable.setBounds(0, 0, 60, 5)
        //图片放在哪边（左边，上边，右边，下边）
        shop_good.setCompoundDrawables(null, null, null, drawable)
        shop_good.compoundDrawablePadding = 10

        val drawableDetail = resources.getDrawable(R.drawable.shape_indicator_empty)
        drawableDetail.setBounds(0, 0, 60, 5)
        shop_detail.setCompoundDrawables(null, null, null, drawableDetail)
        shop_detail.compoundDrawablePadding = 10

        shop_good.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
        shop_good.textSize = 18f
        shop_detail.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
        shop_detail.textSize = 16f
    }

    fun detailGood(){
        val drawable = resources.getDrawable(R.drawable.shape_indicator_empty)
        drawable.setBounds(0, 0, 60, 5)
        shop_good.setCompoundDrawables(null, null, null, drawable)
        shop_good.compoundDrawablePadding = 10

        val drawableDetail = resources.getDrawable(R.drawable.shape_indicator)
        drawableDetail.setBounds(0, 0, 60, 5)
        shop_detail.setCompoundDrawables(null, null, null, drawableDetail)
        shop_detail.compoundDrawablePadding = 10

        shop_detail.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
        shop_detail.textSize = 18f
        shop_good.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
        shop_good.textSize = 16f
    }

}
