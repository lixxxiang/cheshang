package com.android.lixiang.cheshang.presenter.injection.component

import com.android.lixiang.base.injection.ComponentScope
import com.android.lixiang.base.injection.component.FragmentComponent
import com.android.lixiang.cheshang.presenter.injection.module.UserProfileModule
import com.android.lixiang.cheshang.ui.fragment.UserProfileFragment
import dagger.Component

@ComponentScope
@Component(dependencies = arrayOf(FragmentComponent::class), modules = arrayOf(UserProfileModule::class))
interface UserProfileFragmentComponent {
    fun inject(fragment: UserProfileFragment)
}