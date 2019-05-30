package com.android.lixiang.cheshang.service.impl

import com.android.lixiang.cheshang.presenter.data.bean.GetShopByHrAccountAndPositionBean
import com.android.lixiang.cheshang.presenter.data.bean.GetShopsByHrAccountBean
import com.android.lixiang.cheshang.presenter.data.bean.PushDocumentBean
import com.android.lixiang.cheshang.presenter.data.repository.CarStoreRepository
import com.android.lixiang.cheshang.presenter.data.repository.PushDocumentRepository
import com.android.lixiang.cheshang.presenter.data.repository.StoreRepository
import com.android.lixiang.cheshang.presenter.data.repository.WorkRepository
import com.android.lixiang.cheshang.service.CarStoreService
import com.android.lixiang.cheshang.service.PushDocumentService
import com.android.lixiang.cheshang.service.StoreService
import com.android.lixiang.cheshang.service.WorkService
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.Function
import okhttp3.MultipartBody
import java.io.File
import java.util.*
import javax.inject.Inject

class PushDocumentServiceImpl @Inject constructor() : PushDocumentService {
    override fun pushDocument(p1: String): Observable<PushDocumentBean> {
        return pushDocumentRepository.pushDocument(p1).flatMap(Function<PushDocumentBean, ObservableSource<PushDocumentBean>> { t ->
            return@Function Observable.just(t)
        })
    }

    @Inject
    lateinit var pushDocumentRepository: PushDocumentRepository
}