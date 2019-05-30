package com.android.lixiang.cheshang.service

import com.android.lixiang.cheshang.presenter.data.bean.GetMissionListBean
import io.reactivex.Observable
import okhttp3.MultipartBody
import java.io.File

interface MissionListService {
    fun getMissionList(hrAccount: String, shopId: String): Observable<GetMissionListBean>

}