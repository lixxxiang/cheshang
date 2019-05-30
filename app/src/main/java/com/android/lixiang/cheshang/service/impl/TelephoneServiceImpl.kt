package com.android.lixiang.cheshang.service.impl

import com.android.lixiang.cheshang.presenter.data.bean.GetShopsByHrAccountBean
import com.android.lixiang.cheshang.presenter.data.bean.LoginBean
import com.android.lixiang.cheshang.presenter.data.repository.LoginRepository
import com.android.lixiang.cheshang.presenter.data.repository.TelephoneRepository
import com.android.lixiang.cheshang.service.LoginService
import com.android.lixiang.cheshang.service.TelephoneService
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

class TelephoneServiceImpl @Inject constructor() : TelephoneService {

    override fun login(s1: String, s2: String, s3: String): Observable<LoginBean> {
        return mTelephoneRepository.login(s1, s2, s3).flatMap(Function<LoginBean, ObservableSource<LoginBean>> { t ->
            return@Function Observable.just(t)
        })
    }
    @Inject
    lateinit var mTelephoneRepository: TelephoneRepository
}