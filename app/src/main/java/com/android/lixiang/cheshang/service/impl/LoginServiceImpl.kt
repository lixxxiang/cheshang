package com.android.lixiang.cheshang.service.impl

import com.android.lixiang.cheshang.presenter.data.bean.GetShopsByHrAccountBean
import com.android.lixiang.cheshang.presenter.data.bean.GetUserByHrAccountBean
import com.android.lixiang.cheshang.presenter.data.bean.LoginBean
import com.android.lixiang.cheshang.presenter.data.repository.LoginRepository
import com.android.lixiang.cheshang.service.LoginService
import com.android.lixiang.cheshang.service.WorkService
import com.google.gson.Gson
import com.orhanobut.logger.Logger
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.Function
import okhttp3.MultipartBody
import java.io.File
import java.util.*
import javax.inject.Inject

class LoginServiceImpl @Inject constructor() : LoginService {
    override fun getUserByHrAccount(s1: String): Observable<GetUserByHrAccountBean> {
        return mLoginRepository.getUserByHrAccount(s1).flatMap(Function<GetUserByHrAccountBean, ObservableSource<GetUserByHrAccountBean>> { t ->
            return@Function Observable.just(t)
        })
    }

    override fun login(s1: String, s2: String, s3: String): Observable<LoginBean> {
        return mLoginRepository.login(s1, s2, s3).flatMap(Function<LoginBean, ObservableSource<LoginBean>> { t ->
            return@Function Observable.just(t)
        })
    }

    @Inject
    lateinit var mLoginRepository: LoginRepository
}