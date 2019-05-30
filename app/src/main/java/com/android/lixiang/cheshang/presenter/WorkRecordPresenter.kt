package com.android.lixiang.cheshang.presenter

import com.android.lixiang.base.ext.execute
import com.android.lixiang.base.presenter.BasePresenter
import com.android.lixiang.base.rx.BaseObserver
import com.android.lixiang.cheshang.presenter.data.bean.GetShopByHrAccountAndPositionBean
import com.android.lixiang.cheshang.presenter.data.bean.GetShopsByHrAccountBean
import com.android.lixiang.cheshang.presenter.view.WorkRecordView
import com.android.lixiang.cheshang.presenter.view.WorkView
import com.android.lixiang.cheshang.service.WorkRecordService
import com.android.lixiang.cheshang.service.WorkService
import javax.inject.Inject

class WorkRecordPresenter @Inject constructor(): BasePresenter<WorkRecordView>(){
    @Inject
    lateinit var mWorkService : WorkRecordService

//    fun getShopByHrAccountAndPosition(param: String, param2: String, param3: String) {
//        mWorkService.getShopByHrAccountAndPosition(param, param2, param3).execute(object : BaseObserver<GetShopByHrAccountAndPositionBean>() {
//            override fun onNext(t: GetShopByHrAccountAndPositionBean) {
//                super.onNext(t)
////                mView.returnGetShopByHrAccountAndPosition(t)
//            }
//        }, lifecycleProvider)
//    }
}