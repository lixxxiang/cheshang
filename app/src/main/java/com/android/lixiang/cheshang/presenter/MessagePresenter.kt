package com.android.lixiang.cheshang.presenter

import com.android.lixiang.base.ext.execute
import com.android.lixiang.base.presenter.BasePresenter
import com.android.lixiang.base.rx.BaseObserver
import com.android.lixiang.cheshang.presenter.data.bean.GetMessageListBean
import com.android.lixiang.cheshang.presenter.data.bean.LoginBean
import com.android.lixiang.cheshang.presenter.view.MessageView
import com.android.lixiang.cheshang.service.MessageService
import javax.inject.Inject

class MessagePresenter @Inject constructor(): BasePresenter<MessageView>(){
    @Inject
    lateinit var mMessageService : MessageService

    fun getMessageList(param: String, param2: String, param3: String){
        mMessageService.getMessageList(param, param2, param3).execute(object : BaseObserver<GetMessageListBean>(){
            override fun onNext(t: GetMessageListBean) {
                super.onNext(t)
                mView.returnGetMessageList(t)
            }
        },lifecycleProvider)
    }
}