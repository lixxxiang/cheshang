package com.android.lixiang.cheshang.service

import com.android.lixiang.cheshang.presenter.data.bean.GetMissionByHrAccountBean
import com.android.lixiang.cheshang.presenter.data.bean.GetShopByHrAccountAndPositionBean
import com.android.lixiang.cheshang.presenter.data.bean.GetShopsByHrAccountBean
import io.reactivex.Observable
import okhttp3.MultipartBody
import java.io.File

interface WorkService {
    fun getShopByHrAccountAndPosition(hrAccount: String, latidude: String, longitude: String):Observable<GetShopByHrAccountAndPositionBean>
    fun getMissionByHrAccount(hrAccount: String):Observable<GetMissionByHrAccountBean>

}