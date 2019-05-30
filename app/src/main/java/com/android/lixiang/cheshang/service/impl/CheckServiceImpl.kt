package com.android.lixiang.cheshang.service.impl

import com.android.lixiang.cheshang.presenter.data.bean.GetAllAttendanceByHrAccountBean
import com.android.lixiang.cheshang.presenter.data.repository.CheckRepository
import com.android.lixiang.cheshang.service.CheckService
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

class CheckServiceImpl @Inject constructor() : CheckService {
    override fun getAllAttendanceByHrAccount(s1: String): Observable<GetAllAttendanceByHrAccountBean> {
        return mCheckRepository.getAllAttendanceByHrAccount(s1).flatMap(Function<GetAllAttendanceByHrAccountBean, ObservableSource<GetAllAttendanceByHrAccountBean>> { t ->
            return@Function Observable.just(t)
        })
    }

    @Inject
    lateinit var mCheckRepository: CheckRepository
}