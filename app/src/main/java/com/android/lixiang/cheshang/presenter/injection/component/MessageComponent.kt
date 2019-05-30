package com.android.lixiang.cheshang.presenter.injection.component

import com.android.lixiang.base.injection.ComponentScope
import com.android.lixiang.base.injection.component.FragmentComponent
import com.android.lixiang.cheshang.presenter.injection.module.MessageModule
import com.android.lixiang.cheshang.ui.fragment.MessageFragment
import dagger.Component

@ComponentScope
@Component(dependencies = arrayOf(FragmentComponent::class), modules = arrayOf(MessageModule::class))
interface MessageFragmentComponent {
    fun inject(fragment: MessageFragment)
}