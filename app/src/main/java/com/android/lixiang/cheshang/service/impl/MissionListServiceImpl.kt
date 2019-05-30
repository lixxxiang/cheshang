package com.android.lixiang.cheshang.service.impl

import com.android.lixiang.cheshang.presenter.data.bean.GetMessageListBean
import com.android.lixiang.cheshang.presenter.data.bean.GetMissionListBean
import com.android.lixiang.cheshang.presenter.data.repository.MissionListRepository
import com.android.lixiang.cheshang.service.MissionListService
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

class MissionListServiceImpl @Inject constructor() : MissionListService {
    override fun getMissionList(hrAccount: String, shopId: String): Observable<GetMissionListBean> {
        return missionListRepository.getMissionList(hrAccount, shopId).flatMap(Function<GetMissionListBean, ObservableSource<GetMissionListBean>> { t ->
            return@Function Observable.just(t)
        })
    }

    @Inject
    lateinit var missionListRepository: MissionListRepository


}