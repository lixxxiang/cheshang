package com.android.lixiang.cheshang.presenter.view

import com.android.lixiang.base.presenter.view.BaseView
import com.android.lixiang.cheshang.presenter.data.bean.AddInfomationBean
import com.android.lixiang.cheshang.presenter.data.bean.AddLicenseBean
import com.android.lixiang.cheshang.presenter.data.bean.GetLicenseByHrAccountBean
import com.android.lixiang.cheshang.presenter.data.bean.UpdateLoginInfoBean

interface OCRListView: BaseView {
    fun returnGetLicenseByHrAccount(getLicenseByHrAccountBean: GetLicenseByHrAccountBean)
    fun returnGetLicenseByHrAccountError()

}