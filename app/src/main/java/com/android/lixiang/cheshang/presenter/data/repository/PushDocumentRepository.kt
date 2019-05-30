package com.android.lixiang.cheshang.presenter.data.repository

import com.android.lixiang.base.data.net.RetrofitFactory
import com.android.lixiang.cheshang.presenter.data.api.ApiService
import com.android.lixiang.cheshang.presenter.data.bean.GetUserByHrAccountBean
import com.android.lixiang.cheshang.presenter.data.bean.LoginBean
import com.android.lixiang.cheshang.presenter.data.bean.PushDocumentBean
import com.android.lixiang.cheshang.util.NetUtil
import io.reactivex.Observable
import javax.inject.Inject

class PushDocumentRepository @Inject constructor() {
    fun pushDocument(param: String): Observable<PushDocumentBean> {
        return RetrofitFactory(
                NetUtil().urlPrefix ).create(ApiService::class.java).pushDocument(param)
    }
}