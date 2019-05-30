package com.android.lixiang.cheshang.presenter.injection.module

import com.android.lixiang.cheshang.service.StoreService
import com.android.lixiang.cheshang.service.WorkService
import com.android.lixiang.cheshang.service.impl.StoreServiceImpl
import com.android.lixiang.cheshang.service.impl.WorkServiceImpl
import dagger.Module
import dagger.Provides
@Module
class StoreModule {
    @Provides
    fun provideStoreService(service: StoreServiceImpl): StoreService {
        return service
    }
}