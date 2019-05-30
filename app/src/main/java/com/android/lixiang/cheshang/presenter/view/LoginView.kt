package com.android.lixiang.cheshang.presenter.view

import com.android.lixiang.base.presenter.view.BaseView
import com.android.lixiang.cheshang.presenter.data.bean.GetUserByHrAccountBean
import com.android.lixiang.cheshang.presenter.data.bean.LoginBean

interface LoginView: BaseView {
    fun returnLogin(loginBean: LoginBean)
    fun returnGetUserByHrAccount(loginBean: GetUserByHrAccountBean)
    fun returnGetUserByHrAccountError(e: Throwable)

}