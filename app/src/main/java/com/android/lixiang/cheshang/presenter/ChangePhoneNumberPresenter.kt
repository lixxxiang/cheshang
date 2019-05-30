package com.android.lixiang.cheshang.presenter

import com.android.lixiang.base.ext.execute
import com.android.lixiang.base.presenter.BasePresenter
import com.android.lixiang.base.rx.BaseObserver
import com.android.lixiang.cheshang.presenter.data.bean.GetShopsByHrAccountBean
import com.android.lixiang.cheshang.presenter.data.bean.LoginBean
import com.android.lixiang.cheshang.presenter.data.bean.UpdateLoginInfoBean
import com.android.lixiang.cheshang.presenter.view.ChangePhoneNumberView
import com.android.lixiang.cheshang.presenter.view.LoginView
import com.android.lixiang.cheshang.service.ChangePhoneNumberService
import com.android.lixiang.cheshang.service.LoginService
import javax.inject.Inject

class ChangePhoneNumberPresenter @Inject constructor(): BasePresenter<ChangePhoneNumberView>(){
    @Inject
    lateinit var mChangePhoneNumberService : ChangePhoneNumberService

    fun updateLoginInfo(param: String, param2: String, param3: String){
        mChangePhoneNumberService.updateLoginInfo(param, param2, param3).execute(object : BaseObserver<UpdateLoginInfoBean>(){
            override fun onNext(t: UpdateLoginInfoBean) {
                super.onNext(t)
                mView.returnUpdateLoginInfo(t)
            }
        },lifecycleProvider)
    }
}