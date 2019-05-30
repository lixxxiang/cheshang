package com.android.lixiang.cheshang.ui.fragment

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.lixiang.cheshang.R
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import com.android.lixiang.base.ui.fragment.BaseMvpFragment
import com.android.lixiang.cheshang.presenter.CannotLoginPresenter
import com.android.lixiang.cheshang.presenter.data.bean.GetUserByHrAccountBean
import com.android.lixiang.cheshang.presenter.injection.component.DaggerCannotLoginFragmentComponent
import com.android.lixiang.cheshang.presenter.injection.module.CannotLoginModule
import com.android.lixiang.cheshang.presenter.view.CannotLoginView
import com.android.lixiang.cheshang.util.LoadingDialog
import com.android.lixiang.cheshang.util.NetworkChangeReceiver
import com.android.lixiang.cheshang.util.ToastUtil
import com.blankj.utilcode.util.KeyboardUtils
import com.example.lixiang.testalertdialog.LoadingDialog2
import kotlinx.android.synthetic.main.fragment_cannot_login.*
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import org.jetbrains.anko.support.v4.act


class CannotLoginFragment : BaseMvpFragment<CannotLoginPresenter>(), View.OnClickListener, CannotLoginView {
    override fun returnGetUserByHrAccountError() {
        LoadingDialog2(activity!!).hideDialog(dialog2!!)
        ToastUtil().toast2(activity!!, "网络连接失败，请检查网络设置")
    }

    private var dialog2: AlertDialog? = null
    private var isClickable: Boolean? = false

    override fun injectComponent() {
        DaggerCannotLoginFragmentComponent.builder().fragmentComponent(fragmentComponent).cannotLoginModule(CannotLoginModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun returnGetUserByHrAccount(loginBean: GetUserByHrAccountBean) {
        LoadingDialog2(activity!!).hideDialog(dialog2!!)
        if (loginBean.status == 103) {
            ToastUtil().toast(activity!!, "HR号不正确")
        } else if (loginBean.message == "success") {
            val fragment = ChooseReasonFragment().newInstance()
            val bundle = Bundle()
            bundle.putSerializable("DATA", loginBean)
            fragment.arguments = bundle
            start(fragment)
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            mCannotLoginNextStepBtn -> {
                if (isClickable!!) {
                    if (isNetworkConnected == "true") {
                        KeyboardUtils.hideSoftInput(activity)
                        dialog2 = LoadingDialog2(activity!!).showDialog()
                        mPresenter.getUserByHrAccount(mCannotLoginET.text.toString())
                    } else {
                        ToastUtil().toast2(activity!!, "网络连接失败，请检查网络设置")
                    }

                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cannot_login, container, false)
    }

    private var intentFilter: IntentFilter? = null
    private var networkChangeReceiver: NetworkChangeReceiver? = null
    private var isNetworkConnected: String? = null

    private var broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            isNetworkConnected = "false"
        }
    }

    private var broadcastReceiver2: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            isNetworkConnected = "true"
        }
    }

    private fun checkAccess() {
        intentFilter = IntentFilter()
        intentFilter!!.addAction("android.net.conn.CONNECTIVITY_CHANGE")
        networkChangeReceiver = NetworkChangeReceiver()
        activity!!.registerReceiver(networkChangeReceiver, intentFilter)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
    }

    private fun initViews() {
        mCannotLoginToolbar.title = "无法登录"
        (activity as AppCompatActivity).setSupportActionBar(mCannotLoginToolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        mCannotLoginToolbar.setNavigationOnClickListener {
            KeyboardUtils.hideSoftInput(activity)
            pop()
        }
        mCannotLoginET.addTextChangedListener(mCannotLoginTextWatcher)

        mCannotLoginNextStepBtn.setOnClickListener(this)
        checkAccess()
        activity!!.registerReceiver(broadcastReceiver, IntentFilter("NO_ACCESS"))
        activity!!.registerReceiver(broadcastReceiver2, IntentFilter("ACCESS"))
    }

    private var mCannotLoginTextWatcher: TextWatcher = object : TextWatcher {
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            // TODO Auto-generated method stub
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int,
                                       after: Int) {
            // TODO Auto-generated method stub
        }

        override fun afterTextChanged(s: Editable) {
            // TODO Auto-generated method stub
            if (!mCannotLoginET.text.toString().isEmpty()) {
                isClickable = true
                mCannotLoginNextStepBtn.setBackgroundResource(R.drawable.ripple_bg_round_relativelayout_100_red)
            } else {
                isClickable = false
                mCannotLoginNextStepBtn.setBackgroundResource(R.drawable.round_relativelayout_100_gray)
            }
        }
    }

    override fun onEnterAnimationEnd(savedInstanceState: Bundle?) {
        super.onEnterAnimationEnd(savedInstanceState)

    }

    fun newInstance(): CannotLoginFragment {
        val args = Bundle()
        val fragment = CannotLoginFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }
}
