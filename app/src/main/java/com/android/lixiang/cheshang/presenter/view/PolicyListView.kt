package com.android.lixiang.cheshang.presenter.view

import com.android.lixiang.base.presenter.view.BaseView
import com.android.lixiang.cheshang.presenter.data.bean.GetShopByHrAccountAndPositionBean
import com.android.lixiang.cheshang.presenter.data.bean.GetShopsByHrAccountBean
import com.android.lixiang.cheshang.presenter.data.bean.PushDocumentBean

interface PolicyListView : BaseView {
    fun returnPushDocument(pushDocumentBean: PushDocumentBean)
    fun returnPushDocumentError()
}