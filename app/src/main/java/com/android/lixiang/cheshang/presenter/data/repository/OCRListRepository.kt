package com.android.lixiang.cheshang.presenter.data.repository

import com.android.lixiang.base.data.net.RetrofitFactory
import com.android.lixiang.cheshang.presenter.data.api.ApiService
import com.android.lixiang.cheshang.presenter.data.bean.AddLicenseBean
import com.android.lixiang.cheshang.presenter.data.bean.GetLicenseByHrAccountBean
import com.android.lixiang.cheshang.presenter.data.bean.GetShopsByHrAccountBean
import com.android.lixiang.cheshang.presenter.data.bean.LoginBean
import com.android.lixiang.cheshang.util.NetUtil
import io.reactivex.Observable
import javax.inject.Inject

class OCRListRepository @Inject constructor() {
    fun getLicenseByHrAccount(param: String): Observable<GetLicenseByHrAccountBean> {
        return RetrofitFactory(
                NetUtil().urlPrefix ).create(ApiService::class.java).getLicenseByHrAccount(param)
    }
}