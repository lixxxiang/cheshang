package com.android.lixiang.cheshang.service.impl

import com.android.lixiang.cheshang.presenter.data.bean.GetShopsByHrAccountBean
import com.android.lixiang.cheshang.presenter.data.bean.LoginBean
import com.android.lixiang.cheshang.presenter.data.bean.UpdateLoginInfoBean
import com.android.lixiang.cheshang.presenter.data.repository.ChangePhoneNumberRepository
import com.android.lixiang.cheshang.presenter.data.repository.LoginRepository
import com.android.lixiang.cheshang.service.ChangePhoneNumberService
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

class ChangePhoneNumberServiceImpl @Inject constructor() : ChangePhoneNumberService {
    override fun updateLoginInfo(s1: String, s2: String, s3: String): Observable<UpdateLoginInfoBean> {
        return mChangePhoneNumberRepository.updateLoginInfo(s1, s2, s3).flatMap(Function<UpdateLoginInfoBean, ObservableSource<UpdateLoginInfoBean>> { t ->
            return@Function Observable.just(t)
        })
    }

    @Inject
    lateinit var mChangePhoneNumberRepository: ChangePhoneNumberRepository
}