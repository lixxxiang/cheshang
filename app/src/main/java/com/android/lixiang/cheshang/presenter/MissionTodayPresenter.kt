package com.android.lixiang.cheshang.presenter

import com.android.lixiang.base.ext.execute
import com.android.lixiang.base.presenter.BasePresenter
import com.android.lixiang.base.rx.BaseObserver
import com.android.lixiang.cheshang.presenter.data.bean.GetMessageListBean
import com.android.lixiang.cheshang.presenter.data.bean.GetMissionListBean
import com.android.lixiang.cheshang.presenter.data.bean.SubmitMissionAnswer
import com.android.lixiang.cheshang.presenter.data.bean.SubmitMissionAnswerBean
import com.android.lixiang.cheshang.presenter.view.MissionListView
import com.android.lixiang.cheshang.presenter.view.MissionTodayView
import com.android.lixiang.cheshang.service.MissionListService
import com.android.lixiang.cheshang.service.MissionTodayService
import javax.inject.Inject

class MissionTodayPresenter @Inject constructor(): BasePresenter<MissionTodayView>(){
    @Inject
    lateinit var mMissionTodayService : MissionTodayService

    fun submitMissoonAnswer(submitMissionAnswer: Array<SubmitMissionAnswer>){
        mMissionTodayService.submitMissionAnswer(submitMissionAnswer).execute(object : BaseObserver<SubmitMissionAnswerBean>(){
            override fun onNext(t: SubmitMissionAnswerBean) {
                super.onNext(t)
                mView.returnSubmitMissionAnswer(t)
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                mView.returnSubmitMissionAnswerError()
            }
        },lifecycleProvider)
    }
}