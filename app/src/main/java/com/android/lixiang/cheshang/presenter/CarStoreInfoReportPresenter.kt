package com.android.lixiang.cheshang.presenter

import com.android.lixiang.base.ext.execute
import com.android.lixiang.base.presenter.BasePresenter
import com.android.lixiang.base.rx.BaseObserver
import com.android.lixiang.cheshang.presenter.data.bean.AddInfomationBean
import com.android.lixiang.cheshang.presenter.data.bean.UpdateLoginInfoBean
import com.android.lixiang.cheshang.presenter.view.CarStoreInfoReportView
import com.android.lixiang.cheshang.service.CarStoreInfoReportService
import javax.inject.Inject

class CarStoreInfoReportPresenter @Inject constructor() : BasePresenter<CarStoreInfoReportView>() {
    @Inject
    lateinit var mCarStoreInfoReportService: CarStoreInfoReportService

    fun addInfomation(param: String, param2: String, param3: String, param4: String, param5: String, param6: String, param7: String, param8: String) {
        mCarStoreInfoReportService.addInformation(param, param2, param3, param4, param5, param6, param7, param8).execute(object : BaseObserver<AddInfomationBean>() {
            override fun onNext(t: AddInfomationBean) {
                super.onNext(t)
                mView.returnAddInformation(t)
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                mView.returnAddInformationError()
            }
        }, lifecycleProvider)
    }
}