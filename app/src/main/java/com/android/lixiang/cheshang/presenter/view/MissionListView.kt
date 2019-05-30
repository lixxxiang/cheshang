package com.android.lixiang.cheshang.presenter.view

import com.android.lixiang.base.presenter.view.BaseView
import com.android.lixiang.cheshang.presenter.data.bean.GetMissionListBean

interface MissionListView: BaseView {
    fun returnGetMissionList(getMissionListBean: GetMissionListBean)
    fun returnGetMissionListError()

}