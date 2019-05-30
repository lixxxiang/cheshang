package com.android.lixiang.cheshang.presenter

import com.android.lixiang.base.ext.execute
import com.android.lixiang.base.presenter.BasePresenter
import com.android.lixiang.base.rx.BaseObserver
import com.android.lixiang.cheshang.presenter.data.bean.GetAllAttendanceByHrAccountBean
import com.android.lixiang.cheshang.presenter.data.bean.GetShopByHrAccountAndPositionBean
import com.android.lixiang.cheshang.presenter.view.CheckView
import com.android.lixiang.cheshang.service.CheckService
import javax.inject.Inject

class CheckPresenter @Inject constructor(): BasePresenter<CheckView>(){
    @Inject
    lateinit var mCheckService : CheckService
    fun getAllAttendanceByHrAccount(param: String) {
        mCheckService.getAllAttendanceByHrAccount(param).execute(object : BaseObserver<GetAllAttendanceByHrAccountBean>() {
            override fun onNext(t: GetAllAttendanceByHrAccountBean) {
                super.onNext(t)
                mView.returnGetAllAttendanceByHrAccount(t)
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                mView.returnGetAllAttendanceByHrAccountError()
            }
        }, lifecycleProvider)
    }


}