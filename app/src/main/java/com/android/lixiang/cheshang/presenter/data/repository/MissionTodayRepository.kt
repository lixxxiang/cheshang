package com.android.lixiang.cheshang.presenter.data.repository

import com.android.lixiang.base.data.net.RetrofitFactory
import com.android.lixiang.cheshang.presenter.data.api.ApiService
import com.android.lixiang.cheshang.presenter.data.bean.GetMessageListBean
import com.android.lixiang.cheshang.presenter.data.bean.GetMissionListBean
import com.android.lixiang.cheshang.presenter.data.bean.SubmitMissionAnswer
import com.android.lixiang.cheshang.presenter.data.bean.SubmitMissionAnswerBean
import com.android.lixiang.cheshang.util.NetUtil
import io.reactivex.Observable
import javax.inject.Inject

class MissionTodayRepository @Inject constructor() {
    fun submitMissionAnswer(submitMissionAnswer: Array<SubmitMissionAnswer>): Observable<SubmitMissionAnswerBean> {
        return RetrofitFactory(
                NetUtil().urlPrefix ).create(ApiService::class.java).submitMissionAnswer(submitMissionAnswer)
    }
}