package com.android.lixiang.cheshang.presenter.data.repository

import com.android.lixiang.base.data.net.RetrofitFactory
import com.android.lixiang.cheshang.presenter.data.api.ApiService
import com.android.lixiang.cheshang.presenter.data.bean.*
import com.android.lixiang.cheshang.util.NetUtil
import io.reactivex.Observable
import javax.inject.Inject

class ConfirmCheckRepository @Inject constructor() {
    fun addAttendance(param: String, param2: String, param3: String, param4: String, param5: String, param6: String): Observable<AddAttendanceBean> {
        return RetrofitFactory(NetUtil().urlPrefix).create(ApiService::class.java).addAttendance(param, param2, param3, param4, param5, param6)
    }

    fun updateAttendanceByHrAccount(param: String, param2: String, param3: String, param4: String, param5: String, param6: String, param7: String): Observable<UpdateAttendanceByHrAccountBean> {
        return RetrofitFactory(NetUtil().urlPrefix).create(ApiService::class.java).updateAttendanceByHrAccount(param, param2, param3, param4, param5, param6, param7)
    }

    fun updateReasonByCreateTime(param: String, param2: String, param3: String): Observable<UpdateReasonByCreateTimeBean> {
        return RetrofitFactory(NetUtil().urlPrefix).create(ApiService::class.java).updateReasonByCreateTime(param, param2, param3)
    }
}