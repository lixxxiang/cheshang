package com.android.lixiang.cheshang.presenter

import com.android.lixiang.base.ext.execute
import com.android.lixiang.base.presenter.BasePresenter
import com.android.lixiang.base.rx.BaseObserver
import com.android.lixiang.cheshang.presenter.data.bean.GetShopsByHrAccountBean
import com.android.lixiang.cheshang.presenter.data.bean.GetUserByHrAccountBean
import com.android.lixiang.cheshang.presenter.data.bean.LoginBean
import com.android.lixiang.cheshang.presenter.view.LoginView
import com.android.lixiang.cheshang.service.LoginService
import javax.inject.Inject

class LoginPresenter @Inject constructor(): BasePresenter<LoginView>(){
    @Inject
    lateinit var mLoginService : LoginService

    fun login(param: String, param2: String, param3: String){
        mLoginService.login(param, param2, param3).execute(object : BaseObserver<LoginBean>(){
            override fun onNext(t: LoginBean) {
                super.onNext(t)
                mView.returnLogin(t)
            }
        },lifecycleProvider)
    }

    fun getUserByHrAccount(param: String){
        mLoginService.getUserByHrAccount(param).execute(object : BaseObserver<GetUserByHrAccountBean>(){
            override fun onNext(t: GetUserByHrAccountBean) {
                super.onNext(t)
                mView.returnGetUserByHrAccount(t)
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                mView.returnGetUserByHrAccountError(e)
            }
        },lifecycleProvider)
    }
}