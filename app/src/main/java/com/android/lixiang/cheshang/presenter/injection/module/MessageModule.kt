package com.android.lixiang.cheshang.presenter.injection.module

import com.android.lixiang.cheshang.service.LoginService
import com.android.lixiang.cheshang.service.MessageService
import com.android.lixiang.cheshang.service.WorkService
import com.android.lixiang.cheshang.service.impl.LoginServiceImpl
import com.android.lixiang.cheshang.service.impl.MessageServiceImpl
import com.android.lixiang.cheshang.service.impl.WorkServiceImpl
import dagger.Module
import dagger.Provides
@Module
class MessageModule {
    @Provides
    fun provideMessageService(service: MessageServiceImpl): MessageService {
        return service
    }
}