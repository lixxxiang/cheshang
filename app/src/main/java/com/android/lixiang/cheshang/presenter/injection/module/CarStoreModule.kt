package com.android.lixiang.cheshang.presenter.injection.module

import com.android.lixiang.cheshang.service.CarStoreService
import com.android.lixiang.cheshang.service.StoreService
import com.android.lixiang.cheshang.service.WorkService
import com.android.lixiang.cheshang.service.impl.CarStoreServiceImpl
import com.android.lixiang.cheshang.service.impl.StoreServiceImpl
import com.android.lixiang.cheshang.service.impl.WorkServiceImpl
import dagger.Module
import dagger.Provides
@Module
class CarStoreModule {
    @Provides
    fun provideCarStoreService(service: CarStoreServiceImpl): CarStoreService {
        return service
    }
}