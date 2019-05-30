package com.android.lixiang.cheshang.presenter.injection.component

import com.android.lixiang.base.injection.ComponentScope
import com.android.lixiang.base.injection.component.FragmentComponent
import com.android.lixiang.cheshang.presenter.injection.module.ChangePhoneModelModule
import com.android.lixiang.cheshang.presenter.injection.module.ChangePhoneNumberModule
import com.android.lixiang.cheshang.presenter.injection.module.LoginModule
import com.android.lixiang.cheshang.ui.fragment.ChangePhoneModelFragment
import com.android.lixiang.cheshang.ui.fragment.ChangePhoneNumberFragment
import com.android.lixiang.cheshang.ui.fragment.LoginFragment
import dagger.Component

@ComponentScope
@Component(dependencies = arrayOf(FragmentComponent::class), modules = arrayOf(ChangePhoneModelModule::class))
interface ChangePhoneModelFragmentComponent {
    fun inject(fragment: ChangePhoneModelFragment)
}