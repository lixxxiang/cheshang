package com.android.lixiang.cheshang.presenter.data.repository

import com.android.lixiang.base.data.net.RetrofitFactory
import com.android.lixiang.cheshang.presenter.data.api.ApiService
import com.android.lixiang.cheshang.presenter.data.bean.GetMissionByHrAccountBean
import com.android.lixiang.cheshang.presenter.data.bean.GetShopByHrAccountAndPositionBean
import com.android.lixiang.cheshang.presenter.data.bean.GetShopsByHrAccountBean
import com.android.lixiang.cheshang.util.NetUtil
import io.reactivex.Observable
import javax.inject.Inject

class WorkRepository @Inject constructor(){
    fun getShopByHrAccountAndPosition(param: String, param2: String, param3: String): Observable<GetShopByHrAccountAndPositionBean> {
        return RetrofitFactory(NetUtil().urlPrefix).create(ApiService::class.java).getShopByHrAccountAndPosition(param, param2, param3)
    }

    fun getMissionByHrAccount(param: String): Observable<GetMissionByHrAccountBean> {
        return RetrofitFactory(NetUtil().urlPrefix).create(ApiService::class.java).getMissionByHrAccount(param)
    }
}