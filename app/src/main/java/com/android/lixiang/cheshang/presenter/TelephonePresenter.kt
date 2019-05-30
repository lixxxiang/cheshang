package com.android.lixiang.cheshang.presenter

import com.android.lixiang.base.ext.execute
import com.android.lixiang.base.presenter.BasePresenter
import com.android.lixiang.base.rx.BaseObserver
import com.android.lixiang.cheshang.presenter.data.bean.GetShopsByHrAccountBean
import com.android.lixiang.cheshang.presenter.data.bean.LoginBean
import com.android.lixiang.cheshang.presenter.view.LoginView
import com.android.lixiang.cheshang.presenter.view.TelephoneView
import com.android.lixiang.cheshang.service.LoginService
import com.android.lixiang.cheshang.service.TelephoneService
import javax.inject.Inject

class TelephonePresenter @Inject constructor(): BasePresenter<TelephoneView>(){
    @Inject
    lateinit var mTelephoneService : TelephoneService

    fun login(param: String, param2: String, param3: String){
        mTelephoneService.login(param, param2, param3).execute(object : BaseObserver<LoginBean>(){
            override fun onNext(t: LoginBean) {
                super.onNext(t)
                mView.returnLogin(t)
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                mView.returnLoginError()
            }
        },lifecycleProvider)
    }
}