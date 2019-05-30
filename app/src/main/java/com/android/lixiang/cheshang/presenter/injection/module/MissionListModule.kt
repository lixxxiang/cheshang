package com.android.lixiang.cheshang.presenter.injection.module

import com.android.lixiang.cheshang.service.LoginService
import com.android.lixiang.cheshang.service.MissionListService
import com.android.lixiang.cheshang.service.WorkService
import com.android.lixiang.cheshang.service.impl.LoginServiceImpl
import com.android.lixiang.cheshang.service.impl.MissionListServiceImpl
import com.android.lixiang.cheshang.service.impl.WorkServiceImpl
import dagger.Module
import dagger.Provides
@Module
class MissionListModule {
    @Provides
    fun provideMissionListService(service: MissionListServiceImpl): MissionListService {
        return service
    }
}