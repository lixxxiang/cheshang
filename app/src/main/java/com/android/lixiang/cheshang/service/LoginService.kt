package com.android.lixiang.cheshang.service

import com.android.lixiang.cheshang.presenter.data.bean.GetUserByHrAccountBean
import com.android.lixiang.cheshang.presenter.data.bean.LoginBean
import io.reactivex.Observable
import okhttp3.MultipartBody
import java.io.File

interface LoginService {
    fun login(s1: String, s2: String, s3: String):Observable<LoginBean>
    fun getUserByHrAccount(s1: String):Observable<GetUserByHrAccountBean>
}