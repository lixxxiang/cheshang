package com.android.lixiang.cheshang.service.impl

import com.android.lixiang.cheshang.presenter.data.bean.GetMessageListBean
import com.android.lixiang.cheshang.presenter.data.repository.MessageRepository
import com.android.lixiang.cheshang.presenter.data.repository.WorkRepository
import com.android.lixiang.cheshang.service.MessageService
import com.android.lixiang.cheshang.service.WorkService
import com.google.gson.Gson
import com.orhanobut.logger.Logger
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.Function
import okhttp3.MultipartBody
import java.io.File
import java.util.*
import javax.inject.Inject

class MessageServiceImpl @Inject constructor() : MessageService {
    override fun getMessageList(hrAccount: String, p: String, p2: String): Observable<GetMessageListBean> {
        return mMessageRepository.getMessageList(hrAccount, p, p2).flatMap(Function<GetMessageListBean, ObservableSource<GetMessageListBean>> { t ->
            return@Function Observable.just(t)
        })
    }
    @Inject
    lateinit var mMessageRepository: MessageRepository
}