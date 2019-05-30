package com.android.lixiang.cheshang.service

import com.android.lixiang.cheshang.presenter.data.bean.AddAttendanceBean
import com.android.lixiang.cheshang.presenter.data.bean.GetAllAttendanceByHrAccountBean
import com.android.lixiang.cheshang.presenter.data.bean.UpdateAttendanceByHrAccountBean
import com.android.lixiang.cheshang.presenter.data.bean.UpdateReasonByCreateTimeBean
import io.reactivex.Observable
import okhttp3.MultipartBody
import java.io.File

interface ConfirmCheckService {
    fun addAttendance(param: String, param2: String, param3: String, param4: String, param5: String, param6: String):Observable<AddAttendanceBean>
    fun updateAttendanceByHrAccount(param: String, param2: String, param3: String, param4: String, param5: String, param6: String,param7: String):Observable<UpdateAttendanceByHrAccountBean>
    fun updateReasonByCreateTimeBean(param: String, param2: String,param3: String):Observable<UpdateReasonByCreateTimeBean>
}