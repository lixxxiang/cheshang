package com.android.lixiang.cheshang.service.impl

import com.android.lixiang.cheshang.presenter.data.bean.GetLicenseByHrAccountBean
import com.android.lixiang.cheshang.presenter.data.repository.OCRListRepository
import com.android.lixiang.cheshang.service.OCRListService
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.Function
import javax.inject.Inject

class OCRListServiceImpl @Inject constructor() : OCRListService {

    override fun getLicenseByHrAccount(hrAccount: String): Observable<GetLicenseByHrAccountBean> {
        return mOCRListRepository.getLicenseByHrAccount(hrAccount).flatMap(Function<GetLicenseByHrAccountBean, ObservableSource<GetLicenseByHrAccountBean>> { t ->
            return@Function Observable.just(t)
        })
    }


    @Inject
    lateinit var mOCRListRepository: OCRListRepository
}