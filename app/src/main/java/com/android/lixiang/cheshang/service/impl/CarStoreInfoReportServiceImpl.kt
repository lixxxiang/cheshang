package com.android.lixiang.cheshang.service.impl

import com.android.lixiang.cheshang.presenter.data.bean.AddInfomationBean
import com.android.lixiang.cheshang.presenter.data.repository.CarStoreInfoReportRepository
import com.android.lixiang.cheshang.service.CarStoreInfoReportService
import com.android.lixiang.cheshang.service.WorkService
import com.google.gson.Gson
import com.orhanobut.logger.Logger
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.Function
import okhttp3.MultipartBody
import java.io.File
import java.util.*
import javax.inject.Inject

class CarStoreInfoReportServiceImpl @Inject constructor() : CarStoreInfoReportService {
    override fun addInformation(hrAccount: String, p2: String, p3: String, hrAccount2: String, p4: String, p5: String, hrAccount3: String, p6: String): Observable<AddInfomationBean> {
        return mCarStoreInfoReportRepository.addInfomation(hrAccount, p2, p3, hrAccount2, p4, p5, hrAccount3, p6).flatMap(Function<AddInfomationBean, ObservableSource<AddInfomationBean>> { t ->
            return@Function Observable.just(t)
        })
    }

    @Inject
    lateinit var mCarStoreInfoReportRepository: CarStoreInfoReportRepository
}