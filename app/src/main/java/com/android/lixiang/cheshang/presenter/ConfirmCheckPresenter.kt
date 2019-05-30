package com.android.lixiang.cheshang.presenter

import com.android.lixiang.base.ext.execute
import com.android.lixiang.base.presenter.BasePresenter
import com.android.lixiang.base.rx.BaseObserver
import com.android.lixiang.cheshang.presenter.data.bean.*
import com.android.lixiang.cheshang.presenter.view.CheckView
import com.android.lixiang.cheshang.presenter.view.ConfirmCheckView
import com.android.lixiang.cheshang.service.CheckService
import com.android.lixiang.cheshang.service.ConfirmCheckService
import javax.inject.Inject

class ConfirmCheckPresenter @Inject constructor() : BasePresenter<ConfirmCheckView>() {
    @Inject
    lateinit var mConfirmCheckService: ConfirmCheckService

    fun addAttendance(param: String, param2: String, param3: String, param4: String, param5: String, param6: String) {
        mConfirmCheckService.addAttendance(param, param2, param3, param4, param5, param6).execute(object : BaseObserver<AddAttendanceBean>() {
            override fun onNext(t: AddAttendanceBean) {
                super.onNext(t)
                mView.returnAddAttendance(t)
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                mView.returnAddAttendanceError()
            }
        }, lifecycleProvider)
    }

    fun updateAttendanceByHrAccount(param: String, param2: String, param3: String, param4: String, param5: String, param6: String, param7: String) {
        mConfirmCheckService.updateAttendanceByHrAccount(param, param2, param3, param4, param5, param6, param7).execute(object : BaseObserver<UpdateAttendanceByHrAccountBean>() {
            override fun onNext(t: UpdateAttendanceByHrAccountBean) {
                super.onNext(t)
                mView.returnUpdateAttendanceByHrAccount(t)
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                mView.returnUpdateAttendanceByHrAccountError()
            }
        }, lifecycleProvider)
    }

    fun updateReasonByCreateTime(param: String, param2: String, param3: String) {
        mConfirmCheckService.updateReasonByCreateTimeBean(param, param2, param3).execute(object : BaseObserver<UpdateReasonByCreateTimeBean>() {
            override fun onNext(t: UpdateReasonByCreateTimeBean) {
                super.onNext(t)
                mView.returnUpdateReasonByCreateTime(t)
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                mView.returnUpdateReasonByCreateTimeError()
            }
        }, lifecycleProvider)
    }
}