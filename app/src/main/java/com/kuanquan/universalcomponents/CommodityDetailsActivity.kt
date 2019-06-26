package com.kuanquan.universalcomponents

import android.graphics.Typeface
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.LinearLayoutManager
import android.text.Html
import android.view.View
import com.base.library.base.BaseViewModelActivity
import com.base.library.utils.CollectionsUtil
import com.base.library.utils.LogUtil
import com.base.library.utils.glide.invocation.ImageLoaderManager
import com.kuanquan.universalcomponents.adapter.UserEvaluationAdapter
import com.kuanquan.universalcomponents.viewmodel.CommodityDetailsViewModel
import kotlinx.android.synthetic.main.category_list_commodity_details_activity.*

/**
 *  商品详情
 */
class CommodityDetailsActivity : BaseViewModelActivity<CommodityDetailsViewModel>() {

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

//        tab_item_one.setOnClickListener(object : View.OnClickListener{
//            override fun onClick(v: View?) {
//                LogUtil.e("点击1")
//            }
//        })

        // 拦截tablayout点击事件
//        val tabOnClickListener = object : View.OnTouchListener{
//            @SuppressLint("ClickableViewAccessibility")
//            override fun onTouch(view: View, event: MotionEvent): Boolean {
//                val pos = view.tag as Int
//                if (pos==0) {
//                    // 拦截第一个item点击添加自定义逻辑
//                    LogUtil.e("点击了1")
//                    return true
//                }
//                if (pos==1) {
//                    // 拦截第二个item点击
//                    LogUtil.e("点击了2")
//                    return true
//                }
//                return false
//            }
//        }
//        TabLayoutAddOnClickHelper.AddOnClick(tab_layout,tabOnClickListener)
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

         pager_hot.setData(mViewModel.userHot())

        val imageUrl = "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1056731044,2207586648&fm=26&gp=0.jpg"
        ImageLoaderManager.getInstance().displayImageNetUrl(this, imageUrl, R.mipmap.ic_launcher, long_picture)

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
            R.id.shop_detail -> {  // 详情
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
            R.id.tab_item_one ->{
                LogUtil.e("点击了1")
            }
            R.id.tab_item_two ->{
                LogUtil.e("点击了2")
            }
        }
    }

}
