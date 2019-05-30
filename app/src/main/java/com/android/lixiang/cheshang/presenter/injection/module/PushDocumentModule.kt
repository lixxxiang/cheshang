package com.android.lixiang.cheshang.presenter.injection.module

import com.android.lixiang.cheshang.service.CarStoreService
import com.android.lixiang.cheshang.service.PushDocumentService
import com.android.lixiang.cheshang.service.StoreService
import com.android.lixiang.cheshang.service.WorkService
import com.android.lixiang.cheshang.service.impl.CarStoreServiceImpl
import com.android.lixiang.cheshang.service.impl.PushDocumentServiceImpl
import com.android.lixiang.cheshang.service.impl.StoreServiceImpl
import com.android.lixiang.cheshang.service.impl.WorkServiceImpl
import dagger.Module
import dagger.Provides
@Module
class PushDocumentModule {
    @Provides
    fun providePushDocumentService(service: PushDocumentServiceImpl): PushDocumentService {
        return service
    }
}