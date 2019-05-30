package com.android.lixiang.cheshang.service

import com.android.lixiang.cheshang.presenter.data.bean.GetMessageListBean
import io.reactivex.Observable
import okhttp3.MultipartBody
import java.io.File

interface MessageService {
    fun getMessageList(hrAccount: String, p: String, p2: String): Observable<GetMessageListBean>

}