package com.kuanquan.universalcomponents.viewmodel

import com.kuanquan.universalcomponents.bean.SearchBean
import com.kuanquan.universalcomponents.bean.TagBean


class SearchViewModel : MainBaseViewModel() {

    fun default(): List<SearchBean> {
        val lists = ArrayList<SearchBean>()
        var mSearchBean: SearchBean

        mSearchBean = SearchBean()
        mSearchBean.title = "热门数据"
        mSearchBean.itemType = 0
        lists.add(mSearchBean)

        mSearchBean = SearchBean()
        mSearchBean.itemType = 1
        var mTagBean: TagBean
        val childs = ArrayList<TagBean>()
        for (j in 1..9) {
            mTagBean = TagBean()
            if (j == 1) {
                mTagBean.title = "新闻联播$j"
            } else {
                mTagBean.title = "焦耳$j"
            }
            childs.add(mTagBean)
        }
        mSearchBean.dataSource = childs
        lists.add(mSearchBean)



        mSearchBean = SearchBean()
        mSearchBean.title = "历史数据"
        mSearchBean.isDisplay = true
        mSearchBean.itemType = 0
        lists.add(mSearchBean)



        mSearchBean = SearchBean()
        mSearchBean.itemType = 1
        var mTagBean1: TagBean
        val childs1 = ArrayList<TagBean>()
        for (j in 1..9) {
            mTagBean1 = TagBean()
            if (j == 1) {
                mTagBean1.title = "新闻联播$j"
            } else {
                mTagBean1.title = "焦耳$j"
            }
            childs1.add(mTagBean1)
        }
        mSearchBean.dataSource = childs1
        lists.add(mSearchBean)
        return lists
    }
}