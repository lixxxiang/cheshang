package com.android.lixiang.cheshang.presenter.view

import com.android.lixiang.base.presenter.view.BaseView
import com.android.lixiang.cheshang.presenter.data.bean.AddInfomationBean
import com.android.lixiang.cheshang.presenter.data.bean.AddLicenseBean
import com.android.lixiang.cheshang.presenter.data.bean.UpdateLoginInfoBean

interface OCRResultView: BaseView {
    fun returnAddLicense(addLicenseBean: AddLicenseBean)
    fun returnAddLicenseError()

}