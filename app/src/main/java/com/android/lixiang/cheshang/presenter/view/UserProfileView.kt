package com.android.lixiang.cheshang.presenter.view

import com.android.lixiang.base.presenter.view.BaseView
import com.android.lixiang.cheshang.presenter.data.bean.GetUserByHrAccountBean

interface UserProfileView: BaseView {
    fun returnGetUserByHrAccount(loginBean: GetUserByHrAccountBean)
}