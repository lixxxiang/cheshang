package com.android.lixiang.cheshang.presenter

import com.android.lixiang.base.ext.execute
import com.android.lixiang.base.presenter.BasePresenter
import com.android.lixiang.base.rx.BaseObserver
import com.android.lixiang.cheshang.presenter.data.bean.GetShopByHrAccountAndPositionBean
import com.android.lixiang.cheshang.presenter.data.bean.GetShopsByHrAccountBean
import com.android.lixiang.cheshang.presenter.view.StoreView
import com.android.lixiang.cheshang.presenter.view.WorkView
import com.android.lixiang.cheshang.service.StoreService
import com.android.lixiang.cheshang.service.WorkService
import javax.inject.Inject

class StorePresenter @Inject constructor() : BasePresenter<StoreView>() {
    @Inject
    lateinit var mStoreService: StoreService

    fun getShopByHrAccountAndPosition(param: String, param2: String, param3: String) {
        mStoreService.getShopByHrAccountAndPosition(param, param2, param3).execute(object : BaseObserver<GetShopByHrAccountAndPositionBean>() {
            override fun onNext(t: GetShopByHrAccountAndPositionBean) {
                super.onNext(t)
                mView.returnGetShopByHrAccountAndPosition(t)
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                mView.returnGetShopByHrAccountAndPositionError()
            }
        }, lifecycleProvider)
    }
}