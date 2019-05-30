package com.android.lixiang.cheshang.service.impl

import com.android.lixiang.cheshang.presenter.data.bean.GetMessageListBean
import com.android.lixiang.cheshang.presenter.data.bean.GetMissionListBean
import com.android.lixiang.cheshang.presenter.data.bean.SubmitMissionAnswer
import com.android.lixiang.cheshang.presenter.data.bean.SubmitMissionAnswerBean
import com.android.lixiang.cheshang.presenter.data.repository.MissionListRepository
import com.android.lixiang.cheshang.presenter.data.repository.MissionTodayRepository
import com.android.lixiang.cheshang.service.MissionListService
import com.android.lixiang.cheshang.service.MissionTodayService
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

class MissionTodayServiceImpl @Inject constructor() : MissionTodayService {
    override fun submitMissionAnswer(submitMissionAnswer:  Array<SubmitMissionAnswer>): Observable<SubmitMissionAnswerBean> {
        return missionTodayRepository.submitMissionAnswer(submitMissionAnswer).flatMap(Function<SubmitMissionAnswerBean, ObservableSource<SubmitMissionAnswerBean>> { t ->
            return@Function Observable.just(t)
        })
    }

    @Inject
    lateinit var missionTodayRepository: MissionTodayRepository


}