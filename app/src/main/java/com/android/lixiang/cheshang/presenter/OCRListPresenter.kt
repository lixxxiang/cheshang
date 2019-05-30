package com.android.lixiang.cheshang.presenter

import com.android.lixiang.base.ext.execute
import com.android.lixiang.base.presenter.BasePresenter
import com.android.lixiang.base.rx.BaseObserver
import com.android.lixiang.cheshang.presenter.data.bean.AddInfomationBean
import com.android.lixiang.cheshang.presenter.data.bean.AddLicenseBean
import com.android.lixiang.cheshang.presenter.data.bean.GetLicenseByHrAccountBean
import com.android.lixiang.cheshang.presenter.data.bean.UpdateLoginInfoBean
import com.android.lixiang.cheshang.presenter.view.CarStoreInfoReportView
import com.android.lixiang.cheshang.presenter.view.OCRListView
import com.android.lixiang.cheshang.service.CarStoreInfoReportService
import com.android.lixiang.cheshang.service.OCRListService
import javax.inject.Inject

class OCRListPresenter @Inject constructor() : BasePresenter<OCRListView>() {
    @Inject
    lateinit var mOCRListService: OCRListService

    fun getLicenseByHrAccount(param: String) {
        mOCRListService.getLicenseByHrAccount(param).execute(object : BaseObserver<GetLicenseByHrAccountBean>() {
            override fun onNext(t: GetLicenseByHrAccountBean) {
                super.onNext(t)
                mView.returnGetLicenseByHrAccount(t)
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                mView.returnGetLicenseByHrAccountError()

            }
        }, lifecycleProvider)
    }
}