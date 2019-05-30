package com.android.lixiang.cheshang.presenter.injection.module

import com.android.lixiang.cheshang.service.*
import com.android.lixiang.cheshang.service.impl.*
import dagger.Module
import dagger.Provides
@Module
class CannotLoginModule {
    @Provides
    fun provideCannotLoginService(service: CannotLoginServiceImpl): CannotLoginService {
        return service
    }
}