package com.android.lixiang.cheshang.service.impl

import com.android.lixiang.cheshang.presenter.data.repository.WorkRecordRepository
import com.android.lixiang.cheshang.service.WorkRecordService
import javax.inject.Inject

class WorkRecordServiceImpl @Inject constructor() : WorkRecordService {
    @Inject
    lateinit var workRecordRepository: WorkRecordRepository
}