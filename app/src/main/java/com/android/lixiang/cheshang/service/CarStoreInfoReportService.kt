package com.android.lixiang.cheshang.service

import com.android.lixiang.cheshang.presenter.data.bean.AddInfomationBean
import io.reactivex.Observable
import okhttp3.MultipartBody
import java.io.File

interface CarStoreInfoReportService {
    fun addInformation(hrAccount: String, p2: String, p3: String,
                        hrAccount2: String, p4: String, p5: String,
                        hrAccount3: String, p6: String):Observable<AddInfomationBean>

}