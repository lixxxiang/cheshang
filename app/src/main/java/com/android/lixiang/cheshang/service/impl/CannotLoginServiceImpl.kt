package com.android.lixiang.cheshang.service.impl

import com.android.lixiang.cheshang.presenter.data.bean.GetUserByHrAccountBean
import com.android.lixiang.cheshang.presenter.data.repository.CannotLoginRepository
import com.android.lixiang.cheshang.service.CannotLoginService
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.Function
import javax.inject.Inject

class CannotLoginServiceImpl @Inject constructor() : CannotLoginService {
    override fun getUserByHrAccount(s1: String): Observable<GetUserByHrAccountBean> {
        return mCannotLoginRepository.getUserByHrAccount(s1).flatMap(Function<GetUserByHrAccountBean, ObservableSource<GetUserByHrAccountBean>> { t ->
            return@Function Observable.just(t)
        })
    }

    @Inject
    lateinit var mCannotLoginRepository: CannotLoginRepository


}