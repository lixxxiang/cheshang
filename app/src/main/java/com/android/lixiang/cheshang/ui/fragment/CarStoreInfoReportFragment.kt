package com.android.lixiang.cheshang.ui.fragment

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.lixiang.base.ui.fragment.BaseMvpFragment
import com.android.lixiang.base.utils.view.AndroidBug5497Workaround

import com.android.lixiang.cheshang.R
import com.android.lixiang.cheshang.R.id.*
import com.android.lixiang.cheshang.presenter.CarStoreInfoReportPresenter
import com.android.lixiang.cheshang.presenter.data.bean.AddInfomationBean
import com.android.lixiang.cheshang.presenter.injection.component.DaggerCarStoreInfoReportFragmentComponent
import com.android.lixiang.cheshang.presenter.injection.module.CarStoreInfoReportModule
import com.android.lixiang.cheshang.presenter.view.CarStoreInfoReportView
import com.android.lixiang.cheshang.util.*
import com.blankj.utilcode.util.KeyboardUtils
import com.example.lixiang.testalertdialog.LoadingDialog2
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.fragment_info_report.*
import me.yokeyword.fragmentation.ISupportFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import org.jetbrains.anko.textColor

class CarStoreInfoReportFragment : BaseMvpFragment<CarStoreInfoReportPresenter>(), CarStoreInfoReportView, View.OnClickListener {
    override fun returnAddInformationError() {
//        dialog!!.dismiss()
        LoadingDialog2(activity!!).hideDialog(dialog2!!)
        mCheckInfoRL2.isClickable = true
        ToastUtil().toast2(activity!!, "网络连接失败，请检查网络设置")
    }

    override fun returnAddInformation(addInfomationBean: AddInfomationBean) {
        Logger.d(addInfomationBean.message)
        if (addInfomationBean.message == "success") {
//            dialog!!.dismiss()
            LoadingDialog2(activity!!).hideDialog(dialog2!!)

//            val loadBuilder = DoneDialog.Builder(activity)
//                    .setCancelable(false)
//                    .setCancelOutside(false)
//                    .setShowMessage(false)
//            val dialog2 = loadBuilder.create()
//            dialog2.show()

//            Handler().postDelayed({
//                dialog2.dismiss()
            val bundle = Bundle()
            bundle.putString("TAG", "SUCCESS")
            setFragmentResult(ISupportFragment.RESULT_OK, bundle)
            pop()
//            }, 1000)
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
        DaggerCarStoreInfoReportFragmentComponent.builder().fragmentComponent(fragmentComponent).carStoreInfoReportModule(CarStoreInfoReportModule()).build().inject(this)
        mPresenter.mView = this
    }

    private var mInfo1Flag = true
    private var mInfo2Flag = false
    private var mCarStoreName: String? = null
    private var TYPE: Int? = 0
    private var INDEX: Int? = -1
    //    private var dialog: LoadingDialog? = null
    private var dialog2: AlertDialog? = null

    override fun onClick(v: View?) {
        when (v) {
            mInfo1TV -> {
                if (!mInfo1Flag) {
                    mInfo1TV.setBackgroundResource(R.drawable.round_textview_red)
                    mInfo1TV.setTextColor(Color.WHITE)
                    mInfo2TV.setBackgroundResource(R.drawable.round_textview_border)
                    mInfo2TV.setTextColor(Color.parseColor("#9B9B9B"))
                    mInfo1Flag = true
                    mInfo2Flag = false
                }
                TYPE = 0
            }

            mInfo2TV -> {
                if (!mInfo2Flag) {
                    mInfo2TV.setBackgroundResource(R.drawable.round_textview_red)
                    mInfo2TV.setTextColor(Color.WHITE)
                    mInfo1TV.setBackgroundResource(R.drawable.round_textview_border)
                    mInfo1TV.setTextColor(Color.parseColor("#9B9B9B"))
                    mInfo2Flag = true
                    mInfo1Flag = false
                }
                TYPE = 1
            }

            mCarStoreRL -> {
                KeyboardUtils.hideSoftInput(activity)

                if (isNetworkConnected == "true") {
                    val fragment = CarStoreFragment().newInstance()
                    val bundle = Bundle()
                    bundle.putString("PAGE_FROM", "CarStoreInfoReportFragment")
                    bundle.putInt("INDEX", INDEX!!)
                    fragment.arguments = bundle
                    startForResult(fragment, 0x004)
                } else {
                    ToastUtil().toast2(activity!!, "网络连接失败，请检查网络设置")
                }
//                CrashReport.testJavaCrash();

            }

            mCheckInfoRL2 -> {
                KeyboardUtils.hideSoftInput(activity)

                if (isNetworkConnected == "true") {
                    dialog2 = LoadingDialog2(activity!!).showDialog()
                    mCheckInfoRL2.isClickable = false
                    mPresenter.addInfomation(
                            "-1",
                            (activity!!.application as CheshangApplication).getDaoSession().userDao.loadAll()[0].shopId,
                            (activity!!.application as CheshangApplication).getDaoSession().userDao.loadAll()[0].shopName,
                            (activity!!.application as CheshangApplication).getDaoSession().userDao.loadAll()[0].shopAddress,
                            TYPE.toString(),
                            mReportET.text.toString(),
                            (activity!!.application as CheshangApplication).getDaoSession().userDao.loadAll()[0].hrAccount,
                            (activity!!.application as CheshangApplication).getDaoSession().userDao.loadAll()[0].name
                    )
                } else {
                    ToastUtil().toast2(activity!!, "网络连接失败，请检查网络设置")
                }

            }
        }
    }

    override fun onFragmentResult(requestCode: Int, resultCode: Int, data: Bundle) {
        super.onFragmentResult(requestCode, resultCode, data)
        if (requestCode == 0x004 && resultCode == ISupportFragment.RESULT_OK) {
            mCarStoreName = data.getString("SHOP")
            if (mCarStoreName != "") {
                mCarStoreNameTV.text = mCarStoreName
                mCarStoreNameTV.textColor = Color.parseColor("#4a90e2")
                mLocateIV.setBackgroundResource(R.drawable.ic_locate_blue)
                val user = (activity!!.application as CheshangApplication).getDaoSession().userDao.load(1)
                user.shopId = data.getString("SHOPID")
                user.shopName = data.getString("SHOP")
                user.shopAddress = data.getString("SHOPADDRESS")
                INDEX = data.getInt("INDEX")
                (activity!!.application as CheshangApplication).getDaoSession().userDao.update(user)
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info_report, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
    }

    private fun initViews() {
        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        mInfoReportToolbar.title = "车商店信息上报"
        mInfoReportToolbar.subtitle = (activity!!.application as CheshangApplication).getDaoSession().userDao.loadAll()[0].name
        var result = 0
        val resourceId = this.getResources().getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = this.getResources().getDimensionPixelSize(resourceId)
        }
        AndroidBug5497Workaround.assistActivity(activity)


        (activity as AppCompatActivity).setSupportActionBar(mInfoReportToolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        mInfoReportToolbar.setNavigationOnClickListener {
            KeyboardUtils.hideSoftInput(activity)
            pop()
        }
        mReportET.isFocusable = true
        mReportET.isFocusableInTouchMode = true
        mReportET.requestFocus()
        mReportET.addTextChangedListener(mTextWatcher)

        mInfo1TV.setOnClickListener(this)
        mInfo2TV.setOnClickListener(this)
        mCarStoreRL.setOnClickListener(this)

        checkAccess()
        activity!!.registerReceiver(broadcastReceiver, IntentFilter("NO_ACCESS"))
        activity!!.registerReceiver(broadcastReceiver2, IntentFilter("ACCESS"))


        mCheckInfoRL2.setOnClickListener(this)


    }


    private var mTextWatcher: TextWatcher = object : TextWatcher {
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            // TODO Auto-generated method stub
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int,
                                       after: Int) {
            // TODO Auto-generated method stub
        }

        override fun afterTextChanged(s: Editable) {
            // TODO Auto-generated method stub
            if (!mReportET.text.toString().isEmpty()) {
                mInfoReportRL.visibility = View.GONE
                mInfoReportRLH.visibility = View.VISIBLE


            } else {
                mInfoReportRL.visibility = View.VISIBLE
                mInfoReportRLH.visibility = View.GONE
            }
        }
    }

    fun newInstance(): CarStoreInfoReportFragment {
        val args = Bundle()
        val fragment = CarStoreInfoReportFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }
}
