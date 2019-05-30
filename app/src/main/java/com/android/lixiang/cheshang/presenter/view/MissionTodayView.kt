package com.android.lixiang.cheshang.presenter.view

import com.android.lixiang.base.presenter.view.BaseView
import com.android.lixiang.cheshang.presenter.data.bean.GetMissionListBean
import com.android.lixiang.cheshang.presenter.data.bean.SubmitMissionAnswerBean

interface MissionTodayView: BaseView {
    fun returnSubmitMissionAnswer(submitMissionAnswerBean: SubmitMissionAnswerBean)
    fun returnSubmitMissionAnswerError()

}