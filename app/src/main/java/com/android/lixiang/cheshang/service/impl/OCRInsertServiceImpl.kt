package com.android.lixiang.cheshang.service.impl

import com.android.lixiang.cheshang.presenter.data.bean.AddInfomationBean
import com.android.lixiang.cheshang.presenter.data.bean.AddLicenseBean
import com.android.lixiang.cheshang.presenter.data.repository.CarStoreInfoReportRepository
import com.android.lixiang.cheshang.presenter.data.repository.OCRInsertRepository
import com.android.lixiang.cheshang.presenter.data.repository.OCRResultRepository
import com.android.lixiang.cheshang.service.CarStoreInfoReportService
import com.android.lixiang.cheshang.service.OCRInsertService
import com.android.lixiang.cheshang.service.OCRResultService
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

class OCRInsertServiceImpl @Inject constructor() : OCRInsertService {
    override fun addLicense(hrAccount: String, p2: String, p3: String, hrAccount2: String, p4: String, p5: String, hrAccount3: String, p6: String, hrAccount4: String, p7: String, hrAccount5: String, param12: String, param13: String, param14: String): Observable<AddLicenseBean> {
        return mOCRInsertRepository.addLicense(hrAccount, p2, p3, hrAccount2, p4, p5, hrAccount3, p6, hrAccount4, p7, hrAccount5, param12, param13, param14).flatMap(Function<AddLicenseBean, ObservableSource<AddLicenseBean>> { t ->
            return@Function Observable.just(t)
        })
    }


    @Inject
    lateinit var mOCRInsertRepository: OCRInsertRepository
}