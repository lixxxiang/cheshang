package com.android.lixiang.cheshang.presenter.view

import com.android.lixiang.base.presenter.view.BaseView
import com.android.lixiang.cheshang.presenter.data.bean.GetAllAttendanceByHrAccountBean

interface CheckView: BaseView {
    fun returnGetAllAttendanceByHrAccount(getAllAttendanceByHrAccountBean: GetAllAttendanceByHrAccountBean)
    fun returnGetAllAttendanceByHrAccountError()


}