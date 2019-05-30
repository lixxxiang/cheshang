package com.android.lixiang.cheshang.service.impl

import com.android.lixiang.cheshang.presenter.data.bean.GetUserByHrAccountBean
import com.android.lixiang.cheshang.presenter.data.bean.LoginBean
import com.android.lixiang.cheshang.presenter.data.repository.MissionListRepository
import com.android.lixiang.cheshang.presenter.data.repository.UserProfileRepository
import com.android.lixiang.cheshang.service.MissionListService
import com.android.lixiang.cheshang.service.UserProfileService
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

class UserProfileServiceImpl @Inject constructor() : UserProfileService {
    override fun getUserByHrAccount(s1: String): Observable<GetUserByHrAccountBean> {
        return mUserProfileRepository.getUserByHrAccount(s1).flatMap(Function<GetUserByHrAccountBean, ObservableSource<GetUserByHrAccountBean>> { t ->
            return@Function Observable.just(t)
        })
    }

    @Inject
    lateinit var mUserProfileRepository: UserProfileRepository


}