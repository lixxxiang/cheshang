package com.android.lixiang.cheshang.ui.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityCompat
import android.view.*
import android.widget.*
import com.android.lixiang.base.utils.view.DimenUtil

import com.android.lixiang.cheshang.R
import kotlinx.android.synthetic.main.fragment_check.*
import me.yokeyword.fragmentation.SupportFragment
import android.view.LayoutInflater
import com.android.lixiang.base.ui.fragment.BaseMvpFragment
import com.android.lixiang.cheshang.presenter.CheckPresenter
import com.android.lixiang.cheshang.presenter.data.bean.GetAllAttendanceByHrAccountBean
import com.android.lixiang.cheshang.presenter.injection.component.DaggerCheckFragmentComponent
import com.android.lixiang.cheshang.presenter.injection.module.CheckModule
import com.android.lixiang.cheshang.presenter.view.CheckView
import com.android.lixiang.cheshang.util.CheshangApplication
import com.android.lixiang.cheshang.util.NetworkChangeReceiver
import com.android.lixiang.cheshang.util.ToastUtil
import com.android.lixiang.cheshang.ui.adapter.CheckNewAdapter

class CheckFragment : BaseMvpFragment<CheckPresenter>(), CheckView, View.OnClickListener {
    override fun returnGetAllAttendanceByHrAccountError() {
        ToastUtil().toast2(activity!!, "网络连接失败，请检查网络设置")
    }

    private var HRACCOUNT: String? = null

    override fun returnGetAllAttendanceByHrAccount(getAllAttendanceByHrAccountBean: GetAllAttendanceByHrAccountBean) {
        if (getAllAttendanceByHrAccountBean.status == 200) {
            if (mData!!.size != 0)
                mData = mutableListOf()

            if (getAllAttendanceByHrAccountBean.data.size != 0) {
                if (getAllAttendanceByHrAccountBean.data[getAllAttendanceByHrAccountBean.data.size - 1].attendanceFlag == 0) {
                    mCheckInRL.visibility = View.GONE
                    mCheckOutRL.visibility = View.VISIBLE
                } else if (getAllAttendanceByHrAccountBean.data[getAllAttendanceByHrAccountBean.data.size - 1].attendanceFlag == 1) {
                    mCheckInRL.visibility = View.VISIBLE
                    mCheckOutRL.visibility = View.GONE
                }

                if (getAllAttendanceByHrAccountBean.data.size == 0) {
                    mBSV.visibility = View.VISIBLE
                    mCheckLV.visibility = View.GONE
                } else {
                    mBSV.visibility = View.GONE
                    mCheckLV.visibility = View.VISIBLE
                    for (i in 0 until getAllAttendanceByHrAccountBean.data.size)
                        mData!!.add(getAllAttendanceByHrAccountBean.data[i])

                    adapter = CheckNewAdapter(mData!!, context, fragment1 as IndexFragment)
                    if (mCheckLV.footerViewsCount == 0 && getAllAttendanceByHrAccountBean.data.size > (DimenUtil().px2dip(activity!!, DimenUtil().getScreenHeight(activity!!).toFloat()) - 250) / 103) {
                        mCheckLV.addFooterView(layoutInflater.inflate(R.layout.msg_header, null))
                        mCheckLV.setFooterDividersEnabled(false)
                    }
                    mCheckLV.adapter = adapter
                    mCheckLV.choiceMode = AbsListView.CHOICE_MODE_SINGLE
                    mCheckLV.setOnItemClickListener { adapterView, view, i, l ->
                    }
                }
            } else {
                mCheckInRL.visibility = View.VISIBLE
                mCheckOutRL.visibility = View.GONE
                mBSV.visibility = View.VISIBLE
                mCheckLV.visibility = View.GONE
            }
        }
    }

    override fun injectComponent() {
        DaggerCheckFragmentComponent.builder().fragmentComponent(fragmentComponent).checkModule(CheckModule()).build().inject(this)
        mPresenter.mView = this
    }

    private var fragment1: SupportFragment? = null
    private var mTime: String? = null
    private var adapter: CheckNewAdapter? = null
    private var mData: MutableList<GetAllAttendanceByHrAccountBean.DataBean>? = mutableListOf()
    private var CREATETIME: String? = null
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

    override fun onClick(v: View?) {
        when (v) {

            mCheckInRL -> {
                if (isNetworkConnected == "true") {
                    val fragment = ConfirmCheckFragment().newInstance()
                    val bundle = Bundle()
                    bundle.putString("FROM", "IN")
                    bundle.putString("TAG", "ADD")
                    bundle.putString("SHOP", (parentFragment as IndexFragment).getShopId())
                    fragment.arguments = bundle
                    (fragment1 as IndexFragment).startForResult(fragment, 0x005)
                } else {
                    ToastUtil().toast2(activity!!, "网络连接失败，请检查网络设置")
                }


            }
            mCheckOutRL -> {
                if (isNetworkConnected == "true") {
                    val fragment = ConfirmCheckFragment().newInstance()
                    val bundle = Bundle()
                    bundle.putString("FROM", "OUT")
                    bundle.putString("TAG", "ADD")
                    bundle.putString("SHOP", (parentFragment as IndexFragment).getShopId())
                    fragment.arguments = bundle
                    (fragment1 as IndexFragment).startForResult(fragment, 0x005)
                } else {
                    ToastUtil().toast2(activity!!, "网络连接失败，请检查网络设置")
                }
            }
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_check, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun initViews() {
        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        handlePermisson()

        HRACCOUNT = (activity!!.application as CheshangApplication).getDaoSession().userDao.loadAll()[0].hrAccount
        mPresenter.getAllAttendanceByHrAccount(HRACCOUNT!!)
        fragment1 = parentFragment as SupportFragment

        mCheckInRL.setOnClickListener(this)
        mCheckOutRL.setOnClickListener(this)

        checkAccess()
        activity!!.registerReceiver(broadcastReceiver, IntentFilter("NO_ACCESS"))
        activity!!.registerReceiver(broadcastReceiver2, IntentFilter("ACCESS"))

    }

    fun newInstance(): CheckFragment {
        val args = Bundle()
        val fragment = CheckFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        HRACCOUNT = (activity!!.application as CheshangApplication).getDaoSession().userDao.loadAll()[0].hrAccount
        mPresenter.getAllAttendanceByHrAccount(HRACCOUNT!!)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun handlePermisson() {
        val permission = Manifest.permission.ACCESS_COARSE_LOCATION
        val checkSelfPermission = ActivityCompat.checkSelfPermission(activity!!, permission)
        if (checkSelfPermission == PackageManager.PERMISSION_GRANTED) {
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity!!, permission)) {
            } else {
                myRequestPermission()
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(Build.VERSION_CODES.M)
    private fun myRequestPermission() {
        val permissions = arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_SMS,
                Manifest.permission.READ_PHONE_NUMBERS,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
        requestPermissions(permissions, 1)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        }
    }

    override fun onBackPressedSupport(): Boolean {
        (parentFragment as IndexFragment).changeFragment(0)
        (parentFragment as IndexFragment).page2Topage1()
        return true
    }
}
