package com.android.lixiang.cheshang.presenter.injection.component

import com.android.lixiang.base.injection.ComponentScope
import com.android.lixiang.base.injection.component.FragmentComponent
import com.android.lixiang.cheshang.presenter.injection.module.WorkModule
import com.android.lixiang.cheshang.ui.fragment.WorkFragment
import dagger.Component

@ComponentScope
@Component(dependencies = arrayOf(FragmentComponent::class), modules = arrayOf(WorkModule::class))
interface WorkFragmentComponent {
    fun inject(fragment: WorkFragment)
}