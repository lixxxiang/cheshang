package com.android.lixiang.cheshang.service

import com.android.lixiang.cheshang.presenter.data.bean.GetUserByHrAccountBean
import io.reactivex.Observable
import okhttp3.MultipartBody
import java.io.File

interface CannotLoginService {
    fun getUserByHrAccount(s1: String):Observable<GetUserByHrAccountBean>
}