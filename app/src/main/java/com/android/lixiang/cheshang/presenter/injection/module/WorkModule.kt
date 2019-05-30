package com.android.lixiang.cheshang.presenter.injection.module

import com.android.lixiang.cheshang.service.WorkService
import com.android.lixiang.cheshang.service.impl.WorkServiceImpl
import dagger.Module
import dagger.Provides
@Module
class WorkModule {
    @Provides
    fun provideWorkService(service: WorkServiceImpl): WorkService {
        return service
    }
}