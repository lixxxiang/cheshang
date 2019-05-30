package com.android.lixiang.cheshang.presenter.view

import com.android.lixiang.base.presenter.view.BaseView
import com.android.lixiang.cheshang.presenter.data.bean.AddAttendanceBean
import com.android.lixiang.cheshang.presenter.data.bean.GetAllAttendanceByHrAccountBean
import com.android.lixiang.cheshang.presenter.data.bean.UpdateAttendanceByHrAccountBean
import com.android.lixiang.cheshang.presenter.data.bean.UpdateReasonByCreateTimeBean

interface ConfirmCheckView: BaseView {
    fun returnAddAttendance(addAttendanceBean: AddAttendanceBean)
    fun returnAddAttendanceError()
    fun returnUpdateAttendanceByHrAccount(updateAttendanceByHrAccountBean: UpdateAttendanceByHrAccountBean)
    fun returnUpdateAttendanceByHrAccountError()
    fun returnUpdateReasonByCreateTime(updateReasonByCreateTimeBean: UpdateReasonByCreateTimeBean)
    fun returnUpdateReasonByCreateTimeError()

}