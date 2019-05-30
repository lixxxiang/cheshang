package com.android.lixiang.cheshang.presenter.injection.module

import com.android.lixiang.cheshang.service.CarStoreInfoReportService
import com.android.lixiang.cheshang.service.WorkService
import com.android.lixiang.cheshang.service.impl.CarStoreInfoReportServiceImpl
import com.android.lixiang.cheshang.service.impl.WorkServiceImpl
import dagger.Module
import dagger.Provides
@Module
class CarStoreInfoReportModule {
    @Provides
    fun provideCarStoreInfoReportService(service: CarStoreInfoReportServiceImpl): CarStoreInfoReportService {
        return service
    }
}