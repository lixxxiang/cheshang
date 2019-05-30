package com.android.lixiang.cheshang.presenter.injection.module

import com.android.lixiang.cheshang.service.*
import com.android.lixiang.cheshang.service.impl.*
import dagger.Module
import dagger.Provides
@Module
class ConfirmCheckModule {
    @Provides
    fun provideConfirmCheckService(service: ConfirmCheckServiceImpl): ConfirmCheckService {
        return service
    }
}