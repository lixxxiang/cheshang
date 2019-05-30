package com.android.lixiang.cheshang.presenter.injection.component

import com.android.lixiang.base.injection.ComponentScope
import com.android.lixiang.base.injection.component.FragmentComponent
import com.android.lixiang.cheshang.presenter.injection.module.CarStoreInfoReportModule
import com.android.lixiang.cheshang.ui.fragment.CarStoreInfoReportFragment
import dagger.Component

@ComponentScope
@Component(dependencies = arrayOf(FragmentComponent::class), modules = arrayOf(CarStoreInfoReportModule::class))
interface CarStoreInfoReportFragmentComponent {
    fun inject(fragment: CarStoreInfoReportFragment)
}