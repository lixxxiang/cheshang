package com.android.lixiang.cheshang.presenter.data.repository

import com.android.lixiang.base.data.net.RetrofitFactory
import com.android.lixiang.cheshang.presenter.data.api.ApiService
import com.android.lixiang.cheshang.presenter.data.bean.GetAllAttendanceByHrAccountBean
import com.android.lixiang.cheshang.presenter.data.bean.GetMessageListBean
import com.android.lixiang.cheshang.util.NetUtil
import io.reactivex.Observable
import javax.inject.Inject

class MessageRepository @Inject constructor() {
    fun getMessageList(param: String, param2: String, param3: String): Observable<GetMessageListBean> {
        return RetrofitFactory(NetUtil().urlPrefix).create(ApiService::class.java).getMessageList(param, param2, param3)
    }
}