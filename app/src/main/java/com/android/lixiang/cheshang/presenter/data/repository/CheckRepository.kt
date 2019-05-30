package com.android.lixiang.cheshang.presenter.data.repository

import com.android.lixiang.base.data.net.RetrofitFactory
import com.android.lixiang.cheshang.presenter.data.api.ApiService
import com.android.lixiang.cheshang.presenter.data.bean.AddAttendanceBean
import com.android.lixiang.cheshang.presenter.data.bean.GetAllAttendanceByHrAccountBean
import com.android.lixiang.cheshang.presenter.data.bean.LoginBean
import com.android.lixiang.cheshang.util.NetUtil
import io.reactivex.Observable
import javax.inject.Inject

class CheckRepository @Inject constructor() {
//    fun addAttendance(param: String, param2: String, param3: String, param4: String, param5: String, param6: String): Observable<AddAttendanceBean> {
//        return RetrofitFactory("http://10.10.90.2:8090/").create(ApiService::class.java).addAttendance(param, param2, param3, param4, param5, param6)
//    }

    fun getAllAttendanceByHrAccount(param: String): Observable<GetAllAttendanceByHrAccountBean> {
        return RetrofitFactory(NetUtil().urlPrefix).create(ApiService::class.java).getAllAttendanceByHrAccount(param)
    }
}