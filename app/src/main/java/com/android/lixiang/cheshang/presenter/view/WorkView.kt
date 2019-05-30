package com.android.lixiang.cheshang.presenter.view

import com.android.lixiang.base.presenter.view.BaseView
import com.android.lixiang.cheshang.presenter.data.bean.GetMissionByHrAccountBean
import com.android.lixiang.cheshang.presenter.data.bean.GetShopByHrAccountAndPositionBean
import com.android.lixiang.cheshang.presenter.data.bean.GetShopsByHrAccountBean

interface WorkView : BaseView {
    //    fun returnGetShopsByHrAccount(getShopsByHrAccountBean: GetShopsByHrAccountBean)
    fun returnGetShopByHrAccountAndPosition(getShopByHrAccountAndPositionBean: GetShopByHrAccountAndPositionBean)
    fun returnGetMissionByHrAccount(getMissionByHrAccount: GetMissionByHrAccountBean)

}