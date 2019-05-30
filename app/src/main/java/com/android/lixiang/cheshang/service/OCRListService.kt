package com.android.lixiang.cheshang.service

import com.android.lixiang.cheshang.presenter.data.bean.AddInfomationBean
import com.android.lixiang.cheshang.presenter.data.bean.AddLicenseBean
import com.android.lixiang.cheshang.presenter.data.bean.GetLicenseByHrAccountBean
import io.reactivex.Observable
import okhttp3.MultipartBody
import java.io.File

interface OCRListService {
    fun getLicenseByHrAccount(hrAccount: String): Observable<GetLicenseByHrAccountBean>
}