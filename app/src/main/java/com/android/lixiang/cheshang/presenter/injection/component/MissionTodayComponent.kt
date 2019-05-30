package com.android.lixiang.cheshang.presenter.injection.component

import com.android.lixiang.base.injection.ComponentScope
import com.android.lixiang.base.injection.component.FragmentComponent
import com.android.lixiang.cheshang.presenter.injection.module.MissionListModule
import com.android.lixiang.cheshang.presenter.injection.module.MissionTodayModule
import com.android.lixiang.cheshang.ui.fragment.MissionListFragment
import com.android.lixiang.cheshang.ui.fragment.MissionTodayFragment
import dagger.Component

@ComponentScope
@Component(dependencies = arrayOf(FragmentComponent::class), modules = arrayOf(MissionTodayModule::class))
interface MissionTodayFragmentComponent {
    fun inject(fragment: MissionTodayFragment)
}