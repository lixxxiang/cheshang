package com.android.lixiang.cheshang.ui.fragment

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.lixiang.cheshang.R
import android.support.v7.app.AppCompatActivity
import com.android.lixiang.base.ui.fragment.BaseMvpFragment
import com.android.lixiang.cheshang.presenter.ChangePhoneNumberPresenter
import com.android.lixiang.cheshang.presenter.data.bean.GetUserByHrAccountBean
import com.android.lixiang.cheshang.presenter.data.bean.UpdateLoginInfoBean
import com.android.lixiang.cheshang.presenter.injection.component.DaggerChangePhoneNumberFragmentComponent
import com.android.lixiang.cheshang.presenter.injection.module.ChangePhoneNumberModule
import com.android.lixiang.cheshang.presenter.view.ChangePhoneNumberView
import com.android.lixiang.cheshang.util.IMEIUtil
import com.android.lixiang.cheshang.util.LoadingDialog
import com.android.lixiang.cheshang.util.NetworkChangeReceiver
import com.android.lixiang.cheshang.util.ToastUtil
import com.blankj.utilcode.util.KeyboardUtils
import com.example.lixiang.testalertdialog.LoadingDialog2
import kotlinx.android.synthetic.main.fragment_change_phone_number.*
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator


class ChangePhoneNumberFragment : BaseMvpFragment<ChangePhoneNumberPresenter>(), View.OnClickListener, ChangePhoneNumberView {
    private var getUserByHrAccountBean: GetUserByHrAccountBean? = null
    private var IMEI: String? = null
    private var HR_ACCOUNT: String? = null
    private var DEVICE_ID: String? = null
    private var TEL: String? = null
//    private var dialog: LoadingDialog? = null
    private var dialog2: AlertDialog? = null

    override fun returnUpdateLoginInfo(loginBean: UpdateLoginInfoBean) {
//        dialog!!.dismiss()
        LoadingDialog2(activity!!).hideDialog(dialog2!!)

        if (loginBean.message == "success") {
            start(CannotLoginCommitSuccessFragment().newInstance())
        } else {
            Snackbar.make(view!!, "??", Snackbar.LENGTH_SHORT).show()
        }
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


    override fun injectComponent() {
        DaggerChangePhoneNumberFragmentComponent.builder().fragmentComponent(fragmentComponent).changePhoneNumberModule(ChangePhoneNumberModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onClick(v: View?) {
        when (v) {
            mChangePhoneNumberCommitBtn -> {
                KeyboardUtils.hideSoftInput(activity!!)

                if (isNetworkConnected == "true") {
                    if (mTelET.text.toString() == TEL) {
                        ToastUtil().toast2(activity!!, "请使用不同手机号申请")
                    } else {
//                    val loadBuilder = LoadingDialog.Builder(activity)
//                            .setCancelable(false)
//                            .setCancelOutside(false)
//                            .setShowMessage(false)
//                    dialog = loadBuilder.create()
//                    dialog!!.show()
                        dialog2 = LoadingDialog2(activity!!).showDialog()

                        if (DEVICE_ID == null) {
                            DEVICE_ID = IMEIUtil.getIMEI(activity)
                        }
                        mPresenter.updateLoginInfo(HR_ACCOUNT!!, DEVICE_ID!!, mTelET.text.toString())
                    }
                } else {
                    ToastUtil().toast2(activity!!, "网络连接失败，请检查网络设置")
                }

//                mPresenter.updateLoginInfo()
//                start(CannotLoginCommitSuccessFragment().newInstance())


            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_change_phone_number, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
    }

    private fun initViews() {
        mChangePhoneNumberToolbar.title = "更换手机号码"
        (activity as AppCompatActivity).setSupportActionBar(mChangePhoneNumberToolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        mChangePhoneNumberToolbar.setNavigationOnClickListener {
            KeyboardUtils.hideSoftInput(activity)
            pop()
        }

        checkAccess()
        activity!!.registerReceiver(broadcastReceiver, IntentFilter("NO_ACCESS"))
        activity!!.registerReceiver(broadcastReceiver2, IntentFilter("ACCESS"))

        getUserByHrAccountBean = arguments!!.getSerializable("DATA") as GetUserByHrAccountBean

        mNameTV.text = getUserByHrAccountBean!!.data.name
        mHRTV.text = getUserByHrAccountBean!!.data.hrAccount
        mTelTV.text = getUserByHrAccountBean!!.data.tel
        if (getUserByHrAccountBean!!.data.deviceId == null) {
            mIMEITV.text = "-"
        } else
            mIMEITV.text = getUserByHrAccountBean!!.data.deviceId

        HR_ACCOUNT = getUserByHrAccountBean!!.data.hrAccount
        DEVICE_ID = getUserByHrAccountBean!!.data.deviceId
        TEL = getUserByHrAccountBean!!.data.tel
        mChangePhoneNumberCommitBtn.setOnClickListener(this)
    }

    override fun onEnterAnimationEnd(savedInstanceState: Bundle?) {
        super.onEnterAnimationEnd(savedInstanceState)

    }

    fun newInstance(): ChangePhoneNumberFragment {
        val args = Bundle()
        val fragment = ChangePhoneNumberFragment()
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
