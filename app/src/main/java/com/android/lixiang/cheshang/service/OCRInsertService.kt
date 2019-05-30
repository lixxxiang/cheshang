package com.android.lixiang.cheshang.service

import com.android.lixiang.cheshang.presenter.data.bean.AddInfomationBean
import com.android.lixiang.cheshang.presenter.data.bean.AddLicenseBean
import io.reactivex.Observable
import okhttp3.MultipartBody
import java.io.File

interface OCRInsertService {
    fun addLicense(hrAccount: String, p2: String, p3: String,
                       hrAccount2: String, p4: String, p5: String,
                       hrAccount3: String, p6: String, hrAccount4: String, p7: String, hrAccount5: String, param12: String, param13: String, param14: String): Observable<AddLicenseBean>

}