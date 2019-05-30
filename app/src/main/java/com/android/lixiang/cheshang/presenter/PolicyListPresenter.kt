package com.android.lixiang.cheshang.presenter

import com.android.lixiang.base.ext.execute
import com.android.lixiang.base.presenter.BasePresenter
import com.android.lixiang.base.rx.BaseObserver
import com.android.lixiang.cheshang.presenter.data.bean.GetShopByHrAccountAndPositionBean
import com.android.lixiang.cheshang.presenter.data.bean.GetShopsByHrAccountBean
import com.android.lixiang.cheshang.presenter.data.bean.PushDocumentBean
import com.android.lixiang.cheshang.presenter.view.CarStoreView
import com.android.lixiang.cheshang.presenter.view.PolicyListView
import com.android.lixiang.cheshang.presenter.view.StoreView
import com.android.lixiang.cheshang.presenter.view.WorkView
import com.android.lixiang.cheshang.service.CarStoreService
import com.android.lixiang.cheshang.service.PushDocumentService
import com.android.lixiang.cheshang.service.StoreService
import com.android.lixiang.cheshang.service.WorkService
import javax.inject.Inject

class PolicyListPresenter @Inject constructor() : BasePresenter<PolicyListView>() {
    @Inject
    lateinit var mPushDocumentService: PushDocumentService

    fun pushDocument(param: String) {
        mPushDocumentService.pushDocument(param).execute(object : BaseObserver<PushDocumentBean>() {
            override fun onNext(t: PushDocumentBean) {
                super.onNext(t)
                mView.returnPushDocument(t)
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                mView.returnPushDocumentError()
            }
        }, lifecycleProvider)
    }
}