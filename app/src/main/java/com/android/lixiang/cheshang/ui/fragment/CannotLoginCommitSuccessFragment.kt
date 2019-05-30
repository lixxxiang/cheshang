package com.android.lixiang.cheshang.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.lixiang.cheshang.R
import me.yokeyword.fragmentation.SupportFragment
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.fragment_cannot_login.*
import kotlinx.android.synthetic.main.fragment_cannot_login_commit_success.*
import kotlinx.android.synthetic.main.fragment_change_phone_model.*
import kotlinx.android.synthetic.main.fragment_change_phone_number.*
import kotlinx.android.synthetic.main.fragment_choose_reason.*
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator


class CannotLoginCommitSuccessFragment : SupportFragment(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v) {
            mCommitSuccessDoneBtn -> {
//                popTo(findFragment(LoginFragment().javaClass).javaClass, false)
                startWithPop(LoginFragment().newInstance())
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cannot_login_commit_success, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
    }

    private fun initViews() {
        mCommitSuccessDoneBtn.setOnClickListener(this)
    }

    override fun onEnterAnimationEnd(savedInstanceState: Bundle?) {
        super.onEnterAnimationEnd(savedInstanceState)

    }

    fun newInstance(): CannotLoginCommitSuccessFragment {
        val args = Bundle()
        val fragment = CannotLoginCommitSuccessFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }

    override fun onBackPressedSupport(): Boolean {
        startWithPop(LoginFragment().newInstance())
        return true
    }
}
