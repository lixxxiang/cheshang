package com.android.lixiang.cheshang.presenter.data.repository

import com.android.lixiang.base.data.net.RetrofitFactory
import com.android.lixiang.cheshang.presenter.data.api.ApiService
import com.android.lixiang.cheshang.presenter.data.bean.GetMessageListBean
import com.android.lixiang.cheshang.presenter.data.bean.GetMissionListBean
import com.android.lixiang.cheshang.util.NetUtil
import io.reactivex.Observable
import javax.inject.Inject

class MissionListRepository @Inject constructor() {
    fun getMissionList(param: String, param2: String): Observable<GetMissionListBean> {
        return RetrofitFactory(NetUtil().urlPrefix).create(ApiService::class.java).getMissionList(param, param2)
    }
}