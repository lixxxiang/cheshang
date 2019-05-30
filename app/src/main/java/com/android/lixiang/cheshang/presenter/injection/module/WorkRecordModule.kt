package com.android.lixiang.cheshang.presenter.injection.module


import com.android.lixiang.cheshang.service.WorkRecordService
import com.android.lixiang.cheshang.service.impl.WorkRecordServiceImpl
import dagger.Module
import dagger.Provides
@Module
class WorkRecordModule {
    @Provides
    fun provideWorkRecordService(service: WorkRecordServiceImpl): WorkRecordService {
        return service
    }
}