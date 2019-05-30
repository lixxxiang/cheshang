package com.android.lixiang.cheshang.presenter.injection.module

import com.android.lixiang.cheshang.service.CarStoreInfoReportService
import com.android.lixiang.cheshang.service.OCRInsertService
import com.android.lixiang.cheshang.service.OCRResultService
import com.android.lixiang.cheshang.service.WorkService
import com.android.lixiang.cheshang.service.impl.CarStoreInfoReportServiceImpl
import com.android.lixiang.cheshang.service.impl.OCRInsertServiceImpl
import com.android.lixiang.cheshang.service.impl.OCRResultServiceImpl
import com.android.lixiang.cheshang.service.impl.WorkServiceImpl
import dagger.Module
import dagger.Provides
@Module
class OCRInsertModule {
    @Provides
    fun provideOCRInsertService(service: OCRInsertServiceImpl): OCRInsertService {
        return service
    }
}