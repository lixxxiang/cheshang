package com.android.lixiang.cheshang.presenter.injection.module

import com.android.lixiang.cheshang.service.LoginService
import com.android.lixiang.cheshang.service.TelephoneService
import com.android.lixiang.cheshang.service.WorkService
import com.android.lixiang.cheshang.service.impl.LoginServiceImpl
import com.android.lixiang.cheshang.service.impl.TelephoneServiceImpl
import com.android.lixiang.cheshang.service.impl.WorkServiceImpl
import dagger.Module
import dagger.Provides
@Module
class TelephoneModule {
    @Provides
    fun provideTelephoneService(service: TelephoneServiceImpl): TelephoneService {
        return service
    }
}