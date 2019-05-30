package com.android.lixiang.cheshang.service

import com.android.lixiang.cheshang.presenter.data.bean.GetShopByHrAccountAndPositionBean
import com.android.lixiang.cheshang.presenter.data.bean.GetShopsByHrAccountBean
import io.reactivex.Observable
import okhttp3.MultipartBody
import java.io.File

interface StoreService {
    fun getShopByHrAccountAndPosition(p1: String, p2: String, p3: String): Observable<GetShopByHrAccountAndPositionBean>
}