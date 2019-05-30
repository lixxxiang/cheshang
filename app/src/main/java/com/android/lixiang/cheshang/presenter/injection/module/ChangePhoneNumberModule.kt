package com.android.lixiang.cheshang.presenter.injection.module

import com.android.lixiang.cheshang.service.ChangePhoneNumberService
import com.android.lixiang.cheshang.service.LoginService
import com.android.lixiang.cheshang.service.WorkService
import com.android.lixiang.cheshang.service.impl.ChangePhoneNumberServiceImpl
import com.android.lixiang.cheshang.service.impl.LoginServiceImpl
import com.android.lixiang.cheshang.service.impl.WorkServiceImpl
import com.baidu.location.a.v
import dagger.Module
import dagger.Provides
@Module
class ChangePhoneNumberModule {
    @Provides
    fun provideChangePhoneNumberService(service: ChangePhoneNumberServiceImpl): ChangePhoneNumberService {
        return service
    }
}