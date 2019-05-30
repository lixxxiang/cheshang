package com.android.lixiang.cheshang.presenter.data.repository

import com.android.lixiang.base.data.net.RetrofitFactory
import com.android.lixiang.cheshang.presenter.data.api.ApiService
import com.android.lixiang.cheshang.presenter.data.bean.AddInfomationBean
import com.android.lixiang.cheshang.presenter.data.bean.UpdateLoginInfoBean
import com.android.lixiang.cheshang.util.NetUtil
import io.reactivex.Observable
import javax.inject.Inject

class CarStoreInfoReportRepository @Inject constructor(){
    fun addInfomation(param: String, param2: String, param3: String,param4: String, param5: String, param6: String,para7: String, param8: String): Observable<AddInfomationBean> {
        return RetrofitFactory(NetUtil().urlPrefix).create(ApiService::class.java).addInformation(param, param2, param3, param4, param5, param6, para7, param8)
    }
}