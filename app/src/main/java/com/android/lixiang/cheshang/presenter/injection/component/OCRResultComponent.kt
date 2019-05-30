package com.android.lixiang.cheshang.presenter.injection.component

import com.android.lixiang.base.injection.ComponentScope
import com.android.lixiang.base.injection.component.FragmentComponent
import com.android.lixiang.cheshang.presenter.injection.module.CarStoreInfoReportModule
import com.android.lixiang.cheshang.presenter.injection.module.OCRResultModule
import com.android.lixiang.cheshang.ui.fragment.CarStoreInfoReportFragment
import com.android.lixiang.cheshang.ui.fragment.OCRResultFragment
import dagger.Component

@ComponentScope
@Component(dependencies = arrayOf(FragmentComponent::class), modules = arrayOf(OCRResultModule::class))
interface OCRResultFragmentComponent {
    fun inject(fragment: OCRResultFragment)
}