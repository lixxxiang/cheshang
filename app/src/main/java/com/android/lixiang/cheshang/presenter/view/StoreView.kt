package com.android.lixiang.cheshang.presenter.view

import com.android.lixiang.base.presenter.view.BaseView
import com.android.lixiang.cheshang.presenter.data.bean.GetShopByHrAccountAndPositionBean
import com.android.lixiang.cheshang.presenter.data.bean.GetShopsByHrAccountBean

interface StoreView : BaseView {
    fun returnGetShopByHrAccountAndPosition(getShopByHrAccountAndPositionBean: GetShopByHrAccountAndPositionBean)
    fun returnGetShopByHrAccountAndPositionError()

}