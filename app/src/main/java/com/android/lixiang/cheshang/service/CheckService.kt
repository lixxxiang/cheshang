package com.android.lixiang.cheshang.service

import com.android.lixiang.cheshang.presenter.data.bean.GetAllAttendanceByHrAccountBean
import io.reactivex.Observable
import okhttp3.MultipartBody
import java.io.File

interface CheckService {
    fun getAllAttendanceByHrAccount(s1: String):Observable<GetAllAttendanceByHrAccountBean>

}