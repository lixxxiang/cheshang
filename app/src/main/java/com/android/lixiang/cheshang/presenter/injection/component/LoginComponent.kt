package com.android.lixiang.cheshang.presenter.injection.component

import com.android.lixiang.base.injection.ComponentScope
import com.android.lixiang.base.injection.component.FragmentComponent
import com.android.lixiang.cheshang.presenter.injection.module.LoginModule
import com.android.lixiang.cheshang.ui.fragment.LoginFragment
import dagger.Component

@ComponentScope
@Component(dependencies = arrayOf(FragmentComponent::class), modules = arrayOf(LoginModule::class))
interface LoginFragmentComponent {
    fun inject(fragment: LoginFragment)
}