package com.android.lixiang.cheshang.presenter.view

import com.android.lixiang.base.presenter.view.BaseView
import com.android.lixiang.cheshang.presenter.data.bean.GetAllAttendanceByHrAccountBean
import com.android.lixiang.cheshang.presenter.data.bean.LoginBean

interface TelephoneView: BaseView {
    fun returnLogin(loginBean: LoginBean)
    fun returnLoginError()
}