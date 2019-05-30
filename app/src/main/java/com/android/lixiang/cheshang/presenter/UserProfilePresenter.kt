package com.android.lixiang.cheshang.presenter

import com.android.lixiang.base.ext.execute
import com.android.lixiang.base.presenter.BasePresenter
import com.android.lixiang.base.rx.BaseObserver
import com.android.lixiang.cheshang.presenter.data.bean.GetUserByHrAccountBean
import com.android.lixiang.cheshang.presenter.data.bean.LoginBean
import com.android.lixiang.cheshang.presenter.view.UserProfileView
import com.android.lixiang.cheshang.service.UserProfileService
import javax.inject.Inject

class UserProfilePresenter @Inject constructor(): BasePresenter<UserProfileView>(){
    @Inject
    lateinit var mUserProfileService : UserProfileService

    fun getUserByHrAccount(param: String){
        mUserProfileService.getUserByHrAccount(param).execute(object : BaseObserver<GetUserByHrAccountBean>(){
            override fun onNext(t: GetUserByHrAccountBean) {
                super.onNext(t)
                mView.returnGetUserByHrAccount(t)
            }
        },lifecycleProvider)
    }
}