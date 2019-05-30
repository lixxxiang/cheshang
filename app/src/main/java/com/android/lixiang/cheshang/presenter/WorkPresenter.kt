package com.android.lixiang.cheshang.presenter

import com.android.lixiang.base.ext.execute
import com.android.lixiang.base.presenter.BasePresenter
import com.android.lixiang.base.rx.BaseObserver
import com.android.lixiang.cheshang.presenter.data.bean.GetMissionByHrAccountBean
import com.android.lixiang.cheshang.presenter.data.bean.GetShopByHrAccountAndPositionBean
import com.android.lixiang.cheshang.presenter.data.bean.GetShopsByHrAccountBean
import com.android.lixiang.cheshang.presenter.view.WorkView
import com.android.lixiang.cheshang.service.WorkService
import javax.inject.Inject

class WorkPresenter @Inject constructor(): BasePresenter<WorkView>(){
    @Inject
    lateinit var mWorkService : WorkService

    fun getShopByHrAccountAndPosition(param: String, param2: String, param3: String) {
        mWorkService.getShopByHrAccountAndPosition(param, param2, param3).execute(object : BaseObserver<GetShopByHrAccountAndPositionBean>() {
            override fun onNext(t: GetShopByHrAccountAndPositionBean) {
                super.onNext(t)
                mView.returnGetShopByHrAccountAndPosition(t)
            }
        }, lifecycleProvider)
    }

    fun getMissionByHrAccount(param: String) {
        mWorkService.getMissionByHrAccount(param).execute(object : BaseObserver<GetMissionByHrAccountBean>() {
            override fun onNext(t: GetMissionByHrAccountBean) {
                super.onNext(t)
                mView.returnGetMissionByHrAccount(t)
            }
        }, lifecycleProvider)
    }
}