package com.android.lixiang.cheshang.service.impl

import com.android.lixiang.cheshang.presenter.data.bean.GetMissionByHrAccountBean
import com.android.lixiang.cheshang.presenter.data.bean.GetShopByHrAccountAndPositionBean
import com.android.lixiang.cheshang.presenter.data.bean.GetShopsByHrAccountBean
import com.android.lixiang.cheshang.presenter.data.repository.WorkRepository
import com.android.lixiang.cheshang.service.WorkService
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.Function
import okhttp3.MultipartBody
import java.io.File
import java.util.*
import javax.inject.Inject

class WorkServiceImpl @Inject constructor() : WorkService {
    override fun getMissionByHrAccount(hrAccount: String): Observable<GetMissionByHrAccountBean> {
        return workRepository.getMissionByHrAccount(hrAccount).flatMap(Function<GetMissionByHrAccountBean, ObservableSource<GetMissionByHrAccountBean>> { t ->
            return@Function Observable.just(t)
        })
    }

    override fun getShopByHrAccountAndPosition(hrAccount: String, latidude: String, longitude: String): Observable<GetShopByHrAccountAndPositionBean> {
        return workRepository.getShopByHrAccountAndPosition(hrAccount, latidude, longitude).flatMap(Function<GetShopByHrAccountAndPositionBean, ObservableSource<GetShopByHrAccountAndPositionBean>> { t ->
            return@Function Observable.just(t)
        })
    }

    @Inject
    lateinit var workRepository: WorkRepository
}