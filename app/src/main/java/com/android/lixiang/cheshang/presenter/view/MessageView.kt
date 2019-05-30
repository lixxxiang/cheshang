package com.android.lixiang.cheshang.presenter.view

import com.android.lixiang.base.presenter.view.BaseView
import com.android.lixiang.cheshang.presenter.data.bean.GetMessageListBean

interface MessageView: BaseView {
    fun returnGetMessageList(getMessageListBean: GetMessageListBean)
}