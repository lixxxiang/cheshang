package com.android.lixiang.cheshang.service

import com.android.lixiang.cheshang.presenter.data.bean.GetShopsByHrAccountBean
import com.android.lixiang.cheshang.presenter.data.bean.LoginBean
import com.android.lixiang.cheshang.presenter.data.bean.UpdateLoginInfoBean
import io.reactivex.Observable
import okhttp3.MultipartBody
import java.io.File

interface ChangePhoneModelService {
    fun updateLoginInfo(hrAccount: String, p2: String, p3: String):Observable<UpdateLoginInfoBean>
}