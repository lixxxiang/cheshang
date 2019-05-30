package com.android.lixiang.cheshang.presenter

import com.android.lixiang.base.ext.execute
import com.android.lixiang.base.presenter.BasePresenter
import com.android.lixiang.base.rx.BaseObserver
import com.android.lixiang.cheshang.presenter.data.bean.GetShopByHrAccountAndPositionBean
import com.android.lixiang.cheshang.presenter.data.bean.GetShopsByHrAccountBean
import com.android.lixiang.cheshang.presenter.view.CarStoreView
import com.android.lixiang.cheshang.presenter.view.StoreView
import com.android.lixiang.cheshang.presenter.view.WorkView
import com.android.lixiang.cheshang.service.CarStoreService
import com.android.lixiang.cheshang.service.StoreService
import com.android.lixiang.cheshang.service.WorkService
import javax.inject.Inject

class CarStorePresenter @Inject constructor() : BasePresenter<CarStoreView>() {
    @Inject
    lateinit var mCarStoreService: CarStoreService

    fun getShopByHrAccountAndPosition(param: String, param2: String, param3: String) {
        mCarStoreService.getShopByHrAccountAndPosition(param, param2, param3).execute(object : BaseObserver<GetShopByHrAccountAndPositionBean>() {
            override fun onNext(t: GetShopByHrAccountAndPositionBean) {
                super.onNext(t)
                mView.returnGetShopByHrAccountAndPosition(t)
            }
        }, lifecycleProvider)
    }
}