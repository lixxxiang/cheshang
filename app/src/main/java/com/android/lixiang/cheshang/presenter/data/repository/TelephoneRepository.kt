package com.android.lixiang.cheshang.presenter.data.repository

import com.android.lixiang.base.data.net.RetrofitFactory
import com.android.lixiang.cheshang.presenter.data.api.ApiService
import com.android.lixiang.cheshang.presenter.data.bean.GetShopsByHrAccountBean
import com.android.lixiang.cheshang.presenter.data.bean.LoginBean
import com.android.lixiang.cheshang.util.NetUtil
import io.reactivex.Observable
import javax.inject.Inject

class TelephoneRepository @Inject constructor() {
    fun login(param: String, param2: String, param3: String): Observable<LoginBean> {
        return RetrofitFactory(
                NetUtil().urlPrefix ).create(ApiService::class.java).login(param, param2, param3)
    }
}