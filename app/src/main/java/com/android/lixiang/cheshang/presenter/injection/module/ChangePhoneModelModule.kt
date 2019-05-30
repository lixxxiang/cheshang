package com.android.lixiang.cheshang.presenter.injection.module

import com.android.lixiang.cheshang.service.ChangePhoneModelService
import com.android.lixiang.cheshang.service.ChangePhoneNumberService
import com.android.lixiang.cheshang.service.LoginService
import com.android.lixiang.cheshang.service.WorkService
import com.android.lixiang.cheshang.service.impl.ChangePhoneModelServiceImpl
import com.android.lixiang.cheshang.service.impl.ChangePhoneNumberServiceImpl
import com.android.lixiang.cheshang.service.impl.LoginServiceImpl
import com.android.lixiang.cheshang.service.impl.WorkServiceImpl
import com.baidu.location.a.v
import dagger.Module
import dagger.Provides
@Module
class ChangePhoneModelModule {
    @Provides
    fun provideChangePhoneModelService(service: ChangePhoneModelServiceImpl): ChangePhoneModelService {
        return service
    }
}