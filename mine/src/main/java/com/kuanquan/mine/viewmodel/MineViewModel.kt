package com.kuanquan.mine.viewmodel

import com.kuanquan.mine.bean.MyReceivingAddressBean

class MineViewModel: MineBaseViewModel() {

    fun getData():List<MyReceivingAddressBean>{
        val lists = ArrayList<MyReceivingAddressBean>()
        var bean: MyReceivingAddressBean
        for (i in 0..5) {
            bean = MyReceivingAddressBean()
            if (i == 0) {
                bean.itemType = 0
            } else {
                bean.itemType = 1
            }

            bean.content = "北京 北京市 密云区和平东街11号新楼A座B1-D1号（地下一层），啦啦啦啦啦啦啦阿里"
            bean.name = "杰克"
            bean.phone = "1826801xxxx"
            bean.title = "选择全家门店自提"

            lists.add(bean)
        }
        return lists
    }
}