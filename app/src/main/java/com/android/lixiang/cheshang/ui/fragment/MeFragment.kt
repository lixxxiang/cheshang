package com.android.lixiang.cheshang.ui.fragment

import android.content.*
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.android.lixiang.base.utils.view.DimenUtil

import com.android.lixiang.cheshang.R
import com.android.lixiang.cheshang.ui.adapter.MeAdapter
import com.blankj.utilcode.util.SizeUtils
import kotlinx.android.synthetic.main.fragment_me.*
import me.yokeyword.fragmentation.SupportFragment
import android.support.annotation.RequiresApi
import android.support.v7.app.AlertDialog
import android.support.v7.widget.AppCompatTextView
import com.android.lixiang.base.utils.view.CacheUtil
import com.android.lixiang.cheshang.util.CheshangApplication
import com.android.lixiang.cheshang.util.NetworkChangeReceiver
import com.android.lixiang.cheshang.util.ToastUtil
import com.orhanobut.logger.Logger


class MeFragment : SupportFragment(), View.OnClickListener {
    override fun onClick(p0: View?) {
        when (p0) {
            mLogOutRL -> {
                val builder = AlertDialog.Builder(activity!!)
                builder.setTitle("确认要退出登录吗？")
                builder.setPositiveButton("确认") { dialog, _ ->
                    (activity!!.application as CheshangApplication).getDaoSession().userDao.deleteAll()
                    dialog.dismiss()
                    activity!!.getSharedPreferences("REGISID", Context.MODE_PRIVATE).edit().clear().apply()
                    val fragment2 = LoginFragment().newInstance()
                    val bundle = Bundle()
                    bundle.putString("PAGE_FROM", "MeFragment")
                    fragment2.arguments = bundle
                    (fragment as IndexFragment).startWithPop(fragment2)
                }
                builder.setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                    dialog.dismiss()
                }

                builder.create().show()

            }

            mLoginRL -> {
                if (clickable!!) {
                    val loginFragment = LoginFragment().newInstance()
                    val bundle = Bundle()
                    bundle.putString("PAGE_FROM", "MeFragment")
                    loginFragment.arguments = bundle
                    (fragment as IndexFragment).startForResult(loginFragment, 0x007)
                }
            }
        }
    }

    private var fragment: SupportFragment? = null
    private var USERNAME: String? = null
    private var clickable: Boolean? = false
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_me, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
    }

//    override fun onResume() {
//        super.onResume()
//        Logger.d("dddFFFF")
//        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun initViews() {
        val rl = RelativeLayout(activity)
        val rlParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, (DimenUtil().getScreenHeight(activity!!) - SizeUtils.getMeasuredHeight(mMeLL)))
        rl.layoutParams = rlParams
        rl.setBackgroundColor(Color.parseColor("#F0F0F0"))
        mMeLL.addView(rl, rlParams)

        checkAccess()
        activity!!.registerReceiver(broadcastReceiver, IntentFilter("NO_ACCESS"))
        activity!!.registerReceiver(broadcastReceiver2, IntentFilter("ACCESS"))

        if ((activity!!.application as CheshangApplication).getDaoSession().userDao.loadAll().size != 0) {
            USERNAME = (activity!!.application as CheshangApplication).getDaoSession().userDao.loadAll()[0].name
            mUserNameTV.text = String.format("HI! %s", USERNAME)
            mLogOutRL.visibility = View.VISIBLE
            clickable = false
        } else {
            mUserNameTV.text = "登录"
            mLogOutRL.visibility = View.GONE
            clickable = true
        }
        fragment = parentFragment as IndexFragment
        val adapter = MeAdapter(context)
        mLogOutRL.setOnClickListener(this)
        mLoginRL.setOnClickListener(this)
        mSettingsListView.adapter = adapter
        mSettingsListView.setOnItemClickListener { adapterView, view, i, l ->
            when (i) {
                0 -> {
                    if (isNetworkConnected == "true") {
                        (fragment as IndexFragment).start(UserProfileFragment().newInstance())
                    } else {
                        ToastUtil().toast2(activity!!, "网络连接失败，请检查网络设置")
                    }
                }
                1 -> {
                    val intent = Intent()
                    intent.action = "android.settings.APP_NOTIFICATION_SETTINGS"
                    if (Build.VERSION.SDK_INT in 21..24) {
                        intent.putExtra("app_package", activity!!.packageName)
                        intent.putExtra("app_uid", activity!!.applicationInfo.uid)
                    } else if (Build.VERSION.SDK_INT >= 25) {
                        intent.putExtra("android.provider.extra.APP_PACKAGE", activity!!.packageName)
                        startActivity(intent)
                    }
                }
                2 -> {
                    // TODO Auto-generated method stub
                    var intent: Intent? = null
                    if (Build.VERSION.SDK_INT > 10) {
                        intent = Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS)
                    } else {
                        intent = Intent()
                        val component = ComponentName("com.android.settings", "com.android.settings.WirelessSettings")
                        intent.component = component
                        intent.action = "android.intent.action.VIEW"
                    }
                    context!!.startActivity(intent)
                }
                3 -> {
                    val builder = AlertDialog.Builder(activity!!)
                    builder.setTitle("确认清理吗？")
                    builder.setPositiveButton(getString(R.string.confirm_2)) { dialog, _ ->
                        CacheUtil.clearAllCache(context)
                        mSettingsListView.getChildAt(3).findViewById<AppCompatTextView>(R.id.mCacheTV).text = getString(R.string.no_cache)
                        dialog.dismiss()
                        ToastUtil().toast2(activity!!, "清理成功")
                    }

                    builder.setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                        dialog.dismiss()
                    }

                    builder.create().show()
                }
            }
        }
    }

    override fun onEnterAnimationEnd(savedInstanceState: Bundle?) {
        super.onEnterAnimationEnd(savedInstanceState)

    }

    fun newInstance(): MeFragment {
        val args = Bundle()
        val fragment = MeFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        mSettingsListView.visibility = View.VISIBLE
        ll.visibility = View.GONE
    }

    override fun onSupportInvisible() {
        super.onSupportInvisible()
        mSettingsListView.visibility = View.GONE
        ll.visibility = View.VISIBLE

    }

    override fun onBackPressedSupport(): Boolean {
        (parentFragment as IndexFragment).changeFragment(0)
        (parentFragment as IndexFragment).page4Topage1()
        return true
    }

}
