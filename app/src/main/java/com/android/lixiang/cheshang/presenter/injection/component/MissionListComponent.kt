package com.android.lixiang.cheshang.presenter.injection.component

import com.android.lixiang.base.injection.ComponentScope
import com.android.lixiang.base.injection.component.FragmentComponent
import com.android.lixiang.cheshang.presenter.injection.module.MissionListModule
import com.android.lixiang.cheshang.ui.fragment.MissionListFragment
import dagger.Component

@ComponentScope
@Component(dependencies = arrayOf(FragmentComponent::class), modules = arrayOf(MissionListModule::class))
interface MissionListFragmentComponent {
    fun inject(fragment: MissionListFragment)
}