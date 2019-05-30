package com.android.lixiang.cheshang.presenter

import com.android.lixiang.base.ext.execute
import com.android.lixiang.base.presenter.BasePresenter
import com.android.lixiang.base.rx.BaseObserver
import com.android.lixiang.cheshang.presenter.data.bean.GetMessageListBean
import com.android.lixiang.cheshang.presenter.data.bean.GetMissionListBean
import com.android.lixiang.cheshang.presenter.view.MissionListView
import com.android.lixiang.cheshang.service.MissionListService
import com.orhanobut.logger.Logger
import javax.inject.Inject

class MissionListPresenter @Inject constructor() : BasePresenter<MissionListView>() {
    @Inject
    lateinit var mMissionListService: MissionListService

    fun getMessageList(param: String, param2: String) {
        mMissionListService.getMissionList(param, param2).execute(object : BaseObserver<GetMissionListBean>() {
            override fun onNext(t: GetMissionListBean) {
                super.onNext(t)
                mView.returnGetMissionList(t)
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                mView.returnGetMissionListError()
            }
        }, lifecycleProvider)
    }
}