package com.android.lixiang.cheshang.presenter.data.repository

import com.android.lixiang.base.data.net.RetrofitFactory
import com.android.lixiang.cheshang.presenter.data.api.ApiService
import com.android.lixiang.cheshang.presenter.data.bean.GetUserByHrAccountBean
import com.android.lixiang.cheshang.presenter.data.bean.LoginBean
import com.android.lixiang.cheshang.util.NetUtil
import io.reactivex.Observable
import javax.inject.Inject

class UserProfileRepository @Inject constructor(){
    fun getUserByHrAccount(param: String): Observable<GetUserByHrAccountBean> {
        return RetrofitFactory(NetUtil().urlPrefix).create(ApiService::class.java).getUserByHrAccount(param)
    }
}