package com.android.lixiang.cheshang.presenter.data.repository

import com.android.lixiang.base.data.net.RetrofitFactory
import com.android.lixiang.cheshang.presenter.data.api.ApiService
import com.android.lixiang.cheshang.presenter.data.bean.AddLicenseBean
import com.android.lixiang.cheshang.presenter.data.bean.GetShopsByHrAccountBean
import com.android.lixiang.cheshang.presenter.data.bean.LoginBean
import com.android.lixiang.cheshang.util.NetUtil
import io.reactivex.Observable
import javax.inject.Inject

class OCRResultRepository @Inject constructor() {
    fun addLicense(param: String, param2: String, param3: String, param4: String, param5: String, param6: String, para7: String, param8: String, param9: String, param10: String, param11: String, param12: String, param13: String, param14: String): Observable<AddLicenseBean> {
        return RetrofitFactory(NetUtil().urlPrefix).create(ApiService::class.java).addLicense(param, param2, param3, param4, param5, param6, para7, param8, param9, param10, param11, param12, param13, param14)
    }
}