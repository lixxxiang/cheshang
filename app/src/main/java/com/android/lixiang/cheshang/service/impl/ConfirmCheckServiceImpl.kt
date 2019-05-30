package com.android.lixiang.cheshang.service.impl

import com.android.lixiang.cheshang.presenter.data.bean.AddAttendanceBean
import com.android.lixiang.cheshang.presenter.data.bean.GetAllAttendanceByHrAccountBean
import com.android.lixiang.cheshang.presenter.data.bean.UpdateAttendanceByHrAccountBean
import com.android.lixiang.cheshang.presenter.data.bean.UpdateReasonByCreateTimeBean
import com.android.lixiang.cheshang.presenter.data.repository.CheckRepository
import com.android.lixiang.cheshang.presenter.data.repository.ConfirmCheckRepository
import com.android.lixiang.cheshang.service.CheckService
import com.android.lixiang.cheshang.service.ConfirmCheckService
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

class ConfirmCheckServiceImpl @Inject constructor() : ConfirmCheckService {
    override fun updateReasonByCreateTimeBean(param: String, param2: String, param3: String): Observable<UpdateReasonByCreateTimeBean> {
        return mConfirmCheckRepository.updateReasonByCreateTime(param, param2, param3).flatMap(Function<UpdateReasonByCreateTimeBean, ObservableSource<UpdateReasonByCreateTimeBean>> { t ->
            return@Function Observable.just(t)
        })
    }

    override fun updateAttendanceByHrAccount(param: String, param2: String, param3: String, param4: String, param5: String, param6: String, param7: String): Observable<UpdateAttendanceByHrAccountBean> {
        return mConfirmCheckRepository.updateAttendanceByHrAccount(param, param2, param3, param4, param5, param6, param7).flatMap(Function<UpdateAttendanceByHrAccountBean, ObservableSource<UpdateAttendanceByHrAccountBean>> { t ->
            return@Function Observable.just(t)
        })
    }

    override fun addAttendance(param: String, param2: String, param3: String, param4: String, param5: String, param6: String): Observable<AddAttendanceBean> {
        return mConfirmCheckRepository.addAttendance(param, param2, param3, param4, param5, param6).flatMap(Function<AddAttendanceBean, ObservableSource<AddAttendanceBean>> { t ->
            return@Function Observable.just(t)
        })
    }

    @Inject
    lateinit var mConfirmCheckRepository: ConfirmCheckRepository
}