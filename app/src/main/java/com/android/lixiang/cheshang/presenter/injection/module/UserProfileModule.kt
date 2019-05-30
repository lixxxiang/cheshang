package com.android.lixiang.cheshang.presenter.injection.module

import com.android.lixiang.cheshang.service.*
import com.android.lixiang.cheshang.service.impl.*
import dagger.Module
import dagger.Provides
@Module
class UserProfileModule {
    @Provides
    fun provideUserProfileService(service: UserProfileServiceImpl): UserProfileService {
        return service
    }
}