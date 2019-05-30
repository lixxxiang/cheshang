package com.android.lixiang.cheshang.service.impl

import com.android.lixiang.cheshang.presenter.data.bean.GetShopByHrAccountAndPositionBean
import com.android.lixiang.cheshang.presenter.data.bean.GetShopsByHrAccountBean
import com.android.lixiang.cheshang.presenter.data.repository.StoreRepository
import com.android.lixiang.cheshang.presenter.data.repository.WorkRepository
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

class StoreServiceImpl @Inject constructor() : StoreService {
    override fun getShopByHrAccountAndPosition(hrAccount: String,p2: String, p3: String): Observable<GetShopByHrAccountAndPositionBean> {
        return storeRepository.getShopByHrAccountAndPosition(hrAccount,p2,p3).flatMap(Function<GetShopByHrAccountAndPositionBean, ObservableSource<GetShopByHrAccountAndPositionBean>> { t ->
            return@Function Observable.just(t)
        })
    }

    @Inject
    lateinit var storeRepository: StoreRepository
}