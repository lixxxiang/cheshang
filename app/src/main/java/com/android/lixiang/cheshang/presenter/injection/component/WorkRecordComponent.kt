package com.android.lixiang.cheshang.presenter.injection.component

import com.android.lixiang.base.injection.ComponentScope
import com.android.lixiang.base.injection.component.FragmentComponent
import com.android.lixiang.cheshang.presenter.injection.module.WorkRecordModule
import com.android.lixiang.cheshang.ui.fragment.WorkRecordFragment
import dagger.Component

@ComponentScope
@Component(dependencies = arrayOf(FragmentComponent::class), modules = arrayOf(WorkRecordModule::class))
interface WorkRecordFragmentComponent {
    fun inject(fragment: WorkRecordFragment)
}