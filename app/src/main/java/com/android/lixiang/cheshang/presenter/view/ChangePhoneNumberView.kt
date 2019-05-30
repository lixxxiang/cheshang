package com.android.lixiang.cheshang.presenter.view

import com.android.lixiang.base.presenter.view.BaseView
import com.android.lixiang.cheshang.presenter.data.bean.LoginBean
import com.android.lixiang.cheshang.presenter.data.bean.UpdateLoginInfoBean

interface ChangePhoneNumberView: BaseView {
    fun returnUpdateLoginInfo(loginBean: UpdateLoginInfoBean)
}