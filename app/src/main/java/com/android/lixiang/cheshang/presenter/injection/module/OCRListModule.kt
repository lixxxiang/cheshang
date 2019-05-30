package com.android.lixiang.cheshang.presenter.injection.module

import com.android.lixiang.cheshang.service.CarStoreInfoReportService
import com.android.lixiang.cheshang.service.OCRListService
import com.android.lixiang.cheshang.service.OCRResultService
import com.android.lixiang.cheshang.service.WorkService
import com.android.lixiang.cheshang.service.impl.CarStoreInfoReportServiceImpl
import com.android.lixiang.cheshang.service.impl.OCRListServiceImpl
import com.android.lixiang.cheshang.service.impl.OCRResultServiceImpl
import com.android.lixiang.cheshang.service.impl.WorkServiceImpl
import dagger.Module
import dagger.Provides
@Module
class OCRListModule {
    @Provides
    fun provideOCRListService(service: OCRListServiceImpl): OCRListService {
        return service
    }
}