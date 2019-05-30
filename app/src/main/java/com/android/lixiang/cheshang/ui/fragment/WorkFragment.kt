package com.android.lixiang.cheshang.ui.fragment

import android.content.*
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.jpush.android.api.JPushInterface
import com.android.lixiang.base.ui.fragment.BaseMvpFragment
import com.android.lixiang.base.utils.view.DimenUtil
import com.android.lixiang.cheshang.R
import com.android.lixiang.cheshang.presenter.WorkPresenter
import com.android.lixiang.cheshang.presenter.data.bean.GetMissionByHrAccountBean
import com.android.lixiang.cheshang.presenter.data.bean.GetShopByHrAccountAndPositionBean
import com.android.lixiang.cheshang.presenter.injection.component.DaggerWorkFragmentComponent
import com.android.lixiang.cheshang.presenter.injection.module.WorkModule
import com.android.lixiang.cheshang.presenter.view.WorkView
import com.android.lixiang.cheshang.util.CheshangApplication
import com.android.lixiang.cheshang.util.NetworkChangeReceiver
import com.android.lixiang.cheshang.util.ToastUtil
import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation
import com.baidu.location.LocationClient
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.fragment_work_test.*
import me.yokeyword.fragmentation.SupportFragment


class WorkFragment : BaseMvpFragment<WorkPresenter>(), WorkView, View.OnClickListener {
    override fun returnGetMissionByHrAccount(getMissionByHrAccount: GetMissionByHrAccountBean) {
        when (getMissionByHrAccount.status) {
            103 -> {

            }

            200 -> {
                allNumber = getMissionByHrAccount.data
                val sp = activity!!.getSharedPreferences("DONEMISSIONCOUNT", Context.MODE_PRIVATE)
                val count: Int
                count = if (sp.getString("donemissioncount", "") != "")
                    sp.getString("donemissioncount", "").toInt()
                else
                    0

                if (allNumber - count == 0) {
                    mMissionTV.text = "查看任务详情"
                } else
                    mMissionTV.text = String.format("还有 %d项 未完成", allNumber - count)
            }
        }
    }

    //    private var mLocationClient: LocationClient? = null
//    private var myListener = MyLocationListener()
    private var LATITUDE: Double? = 43.986537
    private var LONGITUDE: Double? = 125.408312
    private var HRACCOUNT: String? = null
    var SHOPID: String? = null
    private var hasShop: Boolean? = false
    private var locationFlag: Boolean? = false
    private var intentFilter: IntentFilter? = null
    private var networkChangeReceiver: NetworkChangeReceiver? = null
    private var isNetworkConnected: String? = null
    private var allNumber = 0
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

    override fun returnGetShopByHrAccountAndPosition(getShopByHrAccountAndPositionBean: GetShopByHrAccountAndPositionBean) {
        if (getShopByHrAccountAndPositionBean.status == 200) {
            hasShop = true
            mCurrentShopTV.text = getShopByHrAccountAndPositionBean.data[0].shopName
            SHOPID = getShopByHrAccountAndPositionBean.data[0].shopId
            val user = (activity!!.application as CheshangApplication).getDaoSession().userDao.load(1)
            user.shopId = SHOPID
            user.shopName = getShopByHrAccountAndPositionBean.data[0].shopName
            user.shopAddress = getShopByHrAccountAndPositionBean.data[0].address
            (activity!!.application as CheshangApplication).getDaoSession().userDao.update(user)
//            val id = java.lang.Long.valueOf(et_id.getText().toString())
//            val newStudent = studentDao.load(id)
//
//            //修改名字
//            val name = et_name.getText().toString()
//            newStudent.setName(name)
//            //修改
//            studentDao.update(newStudent)
        } else if (getShopByHrAccountAndPositionBean.status == 103) {

        }
    }


    fun getShopId(): String? {
        return SHOPID
    }

    override fun injectComponent() {
        DaggerWorkFragmentComponent.builder().fragmentComponent(fragmentComponent).workModule(WorkModule()).build().inject(this)
        mPresenter.mView = this

    }

    private var fragment: SupportFragment? = null
    override fun onClick(v: View?) {
        when (v) {
            mMissionTodayBtn -> {
                if (isNetworkConnected == "true") {
                    (fragment as IndexFragment).start(MissionListFragment().newInstance())
                } else {
                    ToastUtil().toast2(activity!!, "网络连接失败，请检查网络设置")
                }
            }

            mInfoReportBtn -> {
                if (isNetworkConnected == "true") {
                    val fragment2 = InfomationReportFragment().newInstance()
                    val bundle = Bundle()
                    bundle.putInt("INDEX", (parentFragment as IndexFragment).INDEX!!)
                    fragment2.arguments = bundle
                    (fragment as IndexFragment).start(fragment2)
                } else {
                    ToastUtil().toast2(activity!!, "网络连接失败，请检查网络设置")
                }

            }
            mClockInBtn -> {
                (parentFragment as IndexFragment).changeFragment(1)
                (parentFragment as IndexFragment).page1Topage2()
            }
            mWorkRecordBtn -> {
                (fragment as IndexFragment).start(WorkRecordFragment().newInstance())
            }

            mPolicyBtn -> {
                if (isNetworkConnected == "true") {
                    (fragment as IndexFragment).start(PolicyListFragment().newInstance())
                } else {
                    ToastUtil().toast2(activity!!, "网络连接失败，请检查网络设置")
                }
            }

            mMsgBtn -> {
                (parentFragment as IndexFragment).changeFragment(2)
                (parentFragment as IndexFragment).page1Topage3()
            }

            mStoreRL -> {
                if (isNetworkConnected == "true") {
                    if (hasShop!!) {
                        val storeFragment = StoreFragment().newInstance()
                        val bundle = Bundle()
                        bundle.putString("LATITUDE", LATITUDE.toString())
                        bundle.putString("LONGITUDE", LONGITUDE.toString())
                        bundle.putInt("INDEX", (parentFragment as IndexFragment).INDEX!!)
                        bundle.putString("PAGE_FROM", "WorkFragment")
                        storeFragment.arguments = bundle
                        (fragment as IndexFragment).startForResult(storeFragment, 0x006)
                    } else {
                        ToastUtil().toast2(activity!!, "系统未获取到附近车商店")
                    }
                } else {
                    ToastUtil().toast2(activity!!, "网络连接失败，请检查网络设置")
                }

            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val height = DimenUtil().px2dip(context!!, DimenUtil().getScreenHeight(context!!).toFloat())
        Logger.d(height)
        return if (height > 658)
            inflater.inflate(R.layout.fragment_work, container, false)
        else
            inflater.inflate(R.layout.fragment_work_test, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        var height = DimenUtil().px2dip(context!!, DimenUtil().getScreenHeight(context!!).toFloat())
//        if(height>658)
        initViews()
    }

    private fun initViews() {
        JPushInterface.setDebugMode(true)    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(activity)
        JPushInterface.setAlias(activity, 0, "lixiang")
        mWorkToolbar.title = ""
        (activity as AppCompatActivity).setSupportActionBar(mWorkToolbar)
        fragment = parentFragment as SupportFragment
        mMissionTodayBtn.setOnClickListener(this)
        mInfoReportBtn.setOnClickListener(this)
        mClockInBtn.setOnClickListener(this)
        mWorkRecordBtn.setOnClickListener(this)
        mPolicyBtn.setOnClickListener(this)
        mMsgBtn.setOnClickListener(this)
        mStoreRL.setOnClickListener(this)
        mWorkToolbar.setNavigationOnClickListener {
            pop()
        }
        checkAccess()
        activity!!.registerReceiver(broadcastReceiver, IntentFilter("NO_ACCESS"))
        activity!!.registerReceiver(broadcastReceiver2, IntentFilter("ACCESS"))

        Handler().post {
            mPresenter.getMissionByHrAccount((activity!!.application as CheshangApplication).getDaoSession().userDao.loadAll()[0].hrAccount)
        }
        HRACCOUNT = (activity!!.application as CheshangApplication).getDaoSession().userDao.loadAll()[0].hrAccount
//        mLocationClient = LocationClient(activity!!.applicationContext)
//        mLocationClient!!.registerLocationListener(myListener)
//        mLocationClient!!.start()
        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        var count: Int? = 0
        val size = (activity!!.application as CheshangApplication).getDaoSession().msgDao.loadAll().size
        for (i in 0 until size) {
            if ((activity!!.application as CheshangApplication).getDaoSession().msgDao.loadAll()[i].isRead == "false")
                count = count!! + 1
        }
        mMessageTV.text = String.format("您有 %d条 未读", count)
        mPresenter.getShopByHrAccountAndPosition(HRACCOUNT!!, LONGITUDE.toString(), LATITUDE.toString())
    }

    fun newInstance(): WorkFragment {
        val args = Bundle()
        val fragment = WorkFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        if ((activity!!.application as CheshangApplication).getDaoSession().userDao.loadAll()[0].shopName != "")
            mCurrentShopTV.text = (activity!!.application as CheshangApplication).getDaoSession().userDao.loadAll()[0].shopName

        var count: Int? = 0
        val size = (activity!!.application as CheshangApplication).getDaoSession().msgDao.loadAll().size
        for (i in 0 until size) {
            if ((activity!!.application as CheshangApplication).getDaoSession().msgDao.loadAll()[i].isRead == "false")
                count = count!! + 1
        }
        mMessageTV.text = String.format("您有 %d条 未读", count)


        val sp = activity!!.getSharedPreferences("DONEMISSIONCOUNT", Context.MODE_PRIVATE)
        val count2: Int
        count2 = if (sp.getString("donemissioncount", "") != "")
            sp.getString("donemissioncount", "").toInt()
        else
            0
        if (allNumber - count2 == 0) {
            mMissionTV.text = "查看任务详情"
        } else
            mMissionTV.text = String.format("还有 %d项 未完成", allNumber - count2)

        Handler().postDelayed({
            activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }, 100)
    }

    override fun onResume() {
        super.onResume()
        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

    override fun onBackPressedSupport(): Boolean {
        val home = Intent(Intent.ACTION_MAIN)
        home.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        home.addCategory(Intent.CATEGORY_HOME)
        startActivity(home)
        return true
    }

}
