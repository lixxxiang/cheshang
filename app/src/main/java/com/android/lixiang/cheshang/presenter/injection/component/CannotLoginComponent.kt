package com.android.lixiang.cheshang.presenter.injection.component

import com.android.lixiang.base.injection.ComponentScope
import com.android.lixiang.base.injection.component.FragmentComponent
import com.android.lixiang.cheshang.presenter.injection.module.CannotLoginModule
import com.android.lixiang.cheshang.presenter.injection.module.UserProfileModule
import com.android.lixiang.cheshang.ui.fragment.CannotLoginFragment
import com.android.lixiang.cheshang.ui.fragment.UserProfileFragment
import dagger.Component

@ComponentScope
@Component(dependencies = arrayOf(FragmentComponent::class), modules = arrayOf(CannotLoginModule::class))
interface CannotLoginFragmentComponent {
    fun inject(fragment: CannotLoginFragment)
}