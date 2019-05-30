package com.android.lixiang.cheshang.presenter.injection.component

import com.android.lixiang.base.injection.ComponentScope
import com.android.lixiang.base.injection.component.FragmentComponent
import com.android.lixiang.cheshang.presenter.injection.module.CheckModule
import com.android.lixiang.cheshang.presenter.injection.module.ConfirmCheckModule
import com.android.lixiang.cheshang.ui.fragment.CheckFragment
import com.android.lixiang.cheshang.ui.fragment.ConfirmCheckFragment
import dagger.Component

@ComponentScope
@Component(dependencies = arrayOf(FragmentComponent::class), modules = arrayOf(ConfirmCheckModule::class))
interface ConfirmCheckFragmentComponent {
    fun inject(fragment: ConfirmCheckFragment)
}