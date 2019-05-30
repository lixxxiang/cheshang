package com.android.lixiang.cheshang.presenter

import com.android.lixiang.base.ext.execute
import com.android.lixiang.base.presenter.BasePresenter
import com.android.lixiang.base.rx.BaseObserver
import com.android.lixiang.cheshang.presenter.data.bean.AddInfomationBean
import com.android.lixiang.cheshang.presenter.data.bean.AddLicenseBean
import com.android.lixiang.cheshang.presenter.data.bean.UpdateLoginInfoBean
import com.android.lixiang.cheshang.presenter.view.CarStoreInfoReportView
import com.android.lixiang.cheshang.presenter.view.OCRInsertView
import com.android.lixiang.cheshang.presenter.view.OCRResultView
import com.android.lixiang.cheshang.service.CarStoreInfoReportService
import com.android.lixiang.cheshang.service.OCRInsertService
import com.android.lixiang.cheshang.service.OCRResultService
import javax.inject.Inject

class OCRInsertPresenter @Inject constructor() : BasePresenter<OCRInsertView>() {
    @Inject
    lateinit var mOCRInsertService: OCRInsertService

    fun addLicense(param: String, param2: String, param3: String, param4: String, param5: String, param6: String, param7: String, param8: String, param9: String, param10: String, param11: String, param12: String, param13: String, param14: String) {
        mOCRInsertService.addLicense(param, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11, param12, param13, param14).execute(object : BaseObserver<AddLicenseBean>() {
            override fun onNext(t: AddLicenseBean) {
                super.onNext(t)
                mView.returnAddLicense(t)
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                mView.returnAddLicenseError()
            }
        }, lifecycleProvider)
    }
}