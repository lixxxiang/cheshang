package com.android.lixiang.cheshang.ui.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.telephony.TelephonyManager
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.jpush.android.api.JPushInterface
import com.alibaba.android.arouter.facade.annotation.Route
import com.android.lixiang.base.ui.fragment.BaseMvpFragment
import com.android.lixiang.cheshang.R
import com.android.lixiang.cheshang.R.id.*
import com.android.lixiang.cheshang.presenter.TelephonePresenter
import com.android.lixiang.cheshang.presenter.data.bean.LoginBean
import com.android.lixiang.cheshang.presenter.data.greenDao.User
import com.android.lixiang.cheshang.presenter.injection.component.DaggerTelephoneFragmentComponent
import com.android.lixiang.cheshang.presenter.injection.module.TelephoneModule
import com.android.lixiang.cheshang.presenter.view.TelephoneView
import com.android.lixiang.cheshang.util.*
import kotlinx.android.synthetic.main.fragment_telephone.*
import com.blankj.utilcode.util.KeyboardUtils
import com.example.lixiang.testalertdialog.LoadingDialog2
import me.yokeyword.fragmentation.ISupportFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator

@Route(path = "/app/login")

class TelephoneFragment : BaseMvpFragment<TelephonePresenter>(), TelephoneView, View.OnClickListener {
    override fun returnLoginError() {
//        dialog!!.dismiss()
        LoadingDialog2(activity!!).hideDialog(dialog2!!)
        ToastUtil().toast2(activity!!, "网络连接失败，请检查网络设置")
    }

    override fun injectComponent() {
        DaggerTelephoneFragmentComponent.builder().fragmentComponent(fragmentComponent).telephoneModule(TelephoneModule()).build().inject(this)
        mPresenter.mView = this
    }

    //    private var dialog: LoadingDialog? = null
    private var dialog2: AlertDialog? = null
    private var loginBean: LoginBean? = null
    override fun returnLogin(l: LoginBean) {
        loginBean = l
        when (loginBean!!.status) {
            200 -> {
//                dialog!!.dismiss()
                LoadingDialog2(activity!!).hideDialog(dialog2!!)

                JPushInterface.setDebugMode(true)    // 设置开启日志,发布时请关闭日志
                JPushInterface.init(activity)
                JPushInterface.setAlias(activity, 0, "lixiang")
                var size = loginBean!!.data.deptList.size
                val user = User(1, loginBean!!.data.hrAccount, loginBean!!.data.deviceId, loginBean!!.data.name, loginBean!!.data.tel, "", "", "", loginBean!!.data.deptList[size - 1].deptId)
                (activity!!.application as CheshangApplication).getDaoSession().userDao.insert(user)
                if (arguments!!.getString("PAGE_FROM") == "MeFragment") {
                    val bundle = Bundle()
                    bundle.putString("USERNAME", (activity!!.application as CheshangApplication).getDaoSession().userDao.loadAll()[0].name)
                    setFragmentResult(ISupportFragment.RESULT_OK, bundle)
                    pop()
                } else
                    startWithPop(IndexFragment().newInstance())
            }
            402 -> {
//                dialog!!.dismiss()
                LoadingDialog2(activity!!).hideDialog(dialog2!!)

                ToastUtil().toast(activity!!, "手机号不匹配")
            }

            401 -> {
//                dialog!!.dismiss()
                LoadingDialog2(activity!!).hideDialog(dialog2!!)

                ToastUtil().toast(activity!!, "手机型号不匹配")
            }
            403 -> {
//                dialog!!.dismiss()
                LoadingDialog2(activity!!).hideDialog(dialog2!!)

                ToastUtil().toast(activity!!, "手机号码及型号均不匹配")
            }
            104 -> {
//                dialog!!.dismiss()
                LoadingDialog2(activity!!).hideDialog(dialog2!!)

                ToastUtil().toast(activity!!, "HR号不正确")
            }
            else -> {

            }
        }
    }

    private var testString: String? = null
    private var clickable: Boolean? = false
    @SuppressLint("MissingPermission")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onClick(v: View?) {
        when (v) {
            mTelRL -> {
                KeyboardUtils.hideSoftInput(activity)
                if (clickable!!) {
                    dialog2 = LoadingDialog2(activity!!).showDialog()
                    mPresenter.login(arguments!!.getString("HRACCOUNT"), mTelET.text.toString(), IMEIUtil.getIMEI(activity))
//                    val loadBuilder = LoadingDialog.Builder(activity)
//                            .setCancelable(false)
//                            .setCancelOutside(false)
//                            .setShowMessage(false)
//                    dialog = loadBuilder.create()
//                    dialog!!.show()
                } else {
                }
            }

            mCannotTelBtn -> {
                if (isNetworkConnected == "true") {
                    KeyboardUtils.hideSoftInput(activity)
                    start(CannotLoginFragment().newInstance())
                } else {
                    ToastUtil().toast2(activity!!, "网络连接失败，请检查网络设置")
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_telephone, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
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

    @RequiresApi(Build.VERSION_CODES.M)
    private fun initViews() {
        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        mTelET.addTextChangedListener(mTelTextWatcher)
        mTelRL.setOnClickListener(this)
        mCannotTelBtn.setOnClickListener(this)
        testString = arguments!!.getString("key3")
        mTelET.setText(testString)

        checkAccess()
        activity!!.registerReceiver(broadcastReceiver, IntentFilter("NO_ACCESS"))
        activity!!.registerReceiver(broadcastReceiver2, IntentFilter("ACCESS"))

        mTelET.setOnFocusChangeListener { view, b ->
            if (b) {
                if (underLine != null)
                    underLine.setBackgroundColor(Color.parseColor("#F73E00"))
            } else {
                if (underLine != null)
                    underLine.setBackgroundColor(Color.parseColor("#F4F4F4"))
            }
        }

        mTelphoneToolbar.title = ""
        (activity as AppCompatActivity).setSupportActionBar(mTelphoneToolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        mTelphoneToolbar.setNavigationOnClickListener {
            KeyboardUtils.hideSoftInput(activity)
            pop()
        }
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }

    private var mTelTextWatcher: TextWatcher = object : TextWatcher {
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            // TODO Auto-generated method stub
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int,
                                       after: Int) {
            // TODO Auto-generated method stub
        }

        override fun afterTextChanged(s: Editable) {
            // TODO Auto-generated method stub
            if (!mTelET.text.toString().isEmpty()) {
                clickable = true
                mTelRL.setBackgroundResource(R.drawable.ripple_bg_round_relativelayout_4_red)
                underLine.setBackgroundColor(Color.parseColor("#F73E00"))
            } else {
                clickable = false
                mTelRL.setBackgroundResource(R.drawable.round_relativelayout_4_gray)
                underLine.setBackgroundColor(Color.parseColor("#F4F4F4"))
            }
        }
    }

    fun newInstance(): TelephoneFragment {
        val args = Bundle()
        val fragment = TelephoneFragment()
        fragment.arguments = args
        return fragment
    }


    override fun onBackPressedSupport(): Boolean {
        KeyboardUtils.hideSoftInput(activity)
        return super.onBackPressedSupport()
    }
}
