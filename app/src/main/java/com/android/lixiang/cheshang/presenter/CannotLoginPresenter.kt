package com.android.lixiang.cheshang.presenter

import com.android.lixiang.base.ext.execute
import com.android.lixiang.base.presenter.BasePresenter
import com.android.lixiang.base.rx.BaseObserver
import com.android.lixiang.cheshang.presenter.data.bean.GetUserByHrAccountBean
import com.android.lixiang.cheshang.presenter.data.bean.LoginBean
import com.android.lixiang.cheshang.presenter.view.CannotLoginView
import com.android.lixiang.cheshang.presenter.view.UserProfileView
import com.android.lixiang.cheshang.service.CannotLoginService
import com.android.lixiang.cheshang.service.UserProfileService
import javax.inject.Inject

class CannotLoginPresenter @Inject constructor(): BasePresenter<CannotLoginView>(){
    @Inject
    lateinit var mCannotLoginService : CannotLoginService

    fun getUserByHrAccount(param: String){
        mCannotLoginService.getUserByHrAccount(param).execute(object : BaseObserver<GetUserByHrAccountBean>(){
            override fun onNext(t: GetUserByHrAccountBean) {
                super.onNext(t)
                mView.returnGetUserByHrAccount(t)
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                mView.returnGetUserByHrAccountError()
            }
        },lifecycleProvider)
    }
}