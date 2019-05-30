package com.android.lixiang.cheshang.presenter.injection.module

import com.android.lixiang.cheshang.service.LoginService
import com.android.lixiang.cheshang.service.WorkService
import com.android.lixiang.cheshang.service.impl.LoginServiceImpl
import com.android.lixiang.cheshang.service.impl.WorkServiceImpl
import dagger.Module
import dagger.Provides
@Module
class LoginModule {
    @Provides
    fun provideWorkService(service: LoginServiceImpl): LoginService {
        return service
    }
}