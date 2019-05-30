package com.android.lixiang.cheshang.service

import com.android.lixiang.cheshang.presenter.data.bean.GetMissionListBean
import com.android.lixiang.cheshang.presenter.data.bean.SubmitMissionAnswer
import com.android.lixiang.cheshang.presenter.data.bean.SubmitMissionAnswerBean
import io.reactivex.Observable
import okhttp3.MultipartBody
import java.io.File

interface MissionTodayService {
    fun submitMissionAnswer(submitMissionAnswer:  Array<SubmitMissionAnswer>): Observable<SubmitMissionAnswerBean>

}