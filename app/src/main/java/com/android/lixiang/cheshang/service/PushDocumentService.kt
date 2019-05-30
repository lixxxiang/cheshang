package com.android.lixiang.cheshang.service

import com.android.lixiang.cheshang.presenter.data.bean.GetShopByHrAccountAndPositionBean
import com.android.lixiang.cheshang.presenter.data.bean.GetShopsByHrAccountBean
import com.android.lixiang.cheshang.presenter.data.bean.PushDocumentBean
import io.reactivex.Observable
import okhttp3.MultipartBody
import java.io.File

interface PushDocumentService {
    fun pushDocument(p1: String): Observable<PushDocumentBean>
}