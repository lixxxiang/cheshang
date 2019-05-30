package com.android.lixiang.cheshang.ui.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.lixiang.base.ui.fragment.BaseMvpFragment
import com.android.lixiang.cheshang.R
import com.android.lixiang.cheshang.presenter.OCRListPresenter
import com.android.lixiang.cheshang.presenter.data.bean.GetLicenseByHrAccountBean
import com.android.lixiang.cheshang.presenter.injection.component.DaggerOCRListFragmentComponent
import com.android.lixiang.cheshang.presenter.injection.module.OCRListModule
import com.android.lixiang.cheshang.presenter.view.OCRListView
import com.android.lixiang.cheshang.util.CheshangApplication
import com.android.lixiang.cheshang.util.LoadingDialog
import com.android.lixiang.cheshang.util.ToastUtil

import com.android.lixiang.cheshang.ui.adapter.OCRListAdapter
import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation
import com.baidu.location.LocationClient
import com.example.lixiang.testalertdialog.LoadingDialog2
import com.intsig.vlcardscansdk.CommonUtil
import com.intsig.vlcardscansdk.ISCardScanActivity
import com.intsig.vlcardscansdk.ResultData
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.fragment_ocr_list.*
import me.yokeyword.fragmentation.ISupportFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator


class OCRListFragment : BaseMvpFragment<OCRListPresenter>(), View.OnClickListener, OCRListView {
    override fun returnGetLicenseByHrAccountError() {
//        dialog!!.dismiss()
        LoadingDialog2(activity!!).hideDialog(dialog2!!)

        ToastUtil().toast2(activity!!, "网络连接失败，请检查网络设置")
    }

    private var mLocationClient: LocationClient? = null
    private var myListener = MyLocationListener()
    private var LATITUDE: Double? = 0.0
    private var LONGITUDE: Double? = 0.0
    private var locationFlag: Boolean? = false
    var SHOPID: String? = null

    override fun injectComponent() {
        DaggerOCRListFragmentComponent.builder().fragmentComponent(fragmentComponent).oCRListModule(OCRListModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun returnGetLicenseByHrAccount(getLicenseByHrAccountBean: GetLicenseByHrAccountBean) {
        if (getLicenseByHrAccountBean.message == "success") {
            ll.visibility = View.VISIBLE
//            dialog!!.dismiss()
            LoadingDialog2(activity!!).hideDialog(dialog2!!)

            if (getLicenseByHrAccountBean.data.size == 0) {
                mOCRListListView.visibility = View.GONE
                mNotFoundRL.visibility = View.VISIBLE
            } else
                for (i in 0 until getLicenseByHrAccountBean.data.size) {
                    mTitleList!!.add(getLicenseByHrAccountBean.data[i].plateNumber)
                    val time = getLicenseByHrAccountBean.data[i].reportTime.toString().split(" ")[0]
                    val timeArray = time.split("-")
                    val timeAfter = timeArray[0] + "年" + timeArray[1] + "月" + timeArray[2] + "日"
                    if (getLicenseByHrAccountBean.data[i].shopName == null) {
                        mDetailList!!.add("$timeAfter -")
                    } else
                        mDetailList!!.add(timeAfter + " " + getLicenseByHrAccountBean.data[i].shopName)
                    val adapter = OCRListAdapter(context, mTitleList, mDetailList)
                    mOCRListListView.adapter = adapter
                    mOCRListListView.setOnItemClickListener { parent, view, position, id ->
                        val fragment = OCRDetailFragment().newInstance()
                        val bundle = Bundle()
                        bundle.putSerializable("DATA", getLicenseByHrAccountBean.data[position])
                        fragment.arguments = bundle
                        start(fragment)
                    }
                }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 100) {
            val result = data?.getSerializableExtra(ISCardScanActivity.EXTRA_KEY_RESULT_DATA) as ResultData
            val storeFragment = OCRResultFragment().newInstance()
            val bundle = Bundle()
            bundle.putSerializable("RESULT", result)
            storeFragment.arguments = bundle
            start(storeFragment)


//            val intent = Intent(this, RecogResultActivity::class.java)
//            intent.putExtra(ISCardScanActivity.EXTRA_KEY_RESULT_IMAGE,
//                    imagePath)
//            intent.putExtra(ISCardScanActivity.EXTRA_KEY_RESULT_DATA, result)
//            intent.putExtra(ISCardScanActivity.EXTRA_KEY_RESULT_AVATAR,
//                    avatarPath)
//            startActivity(intent)

//        } else if (resultCode == Activity.RESULT_CANCELED && requestCode == REQ_CODE_CAPTURE) {
//            // 识别失败或取消
//            if (data != null) {
//                /**
//                 * 101 包名错误 102 appKey错误 103 超过时间限制 104 达到设备上限 201 签名错误 202 其他错误
//                 * 203 服务器错误 204 网络错误 205 包名/签名错误
//                 */
//                val error_code = data.getIntExtra(
//                        ISCardScanActivity.EXTRA_KEY_RESULT_ERROR_CODE, 0)
//
//
//            }
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            mScanRL -> {
                val intent = Intent(activity, ISCardScanActivity::class.java)
                // 指定要临时保存的图片路径
                intent.putExtra(ISCardScanActivity.EXTRA_KEY_IMAGE_FOLDER,
                        Environment
                                .getExternalStorageDirectory().toString() + "/vlcardscan/")
                // 指定SDK相机模块ISCardScanActivity四边框角线条,检测到图片后的颜色
                intent.putExtra(ISCardScanActivity.EXTRA_KEY_COLOR_MATCH, -0xd5820d)
                // 指定SDK相机模块ISCardScanActivity四边框角线条颜色，正常显示颜色
                intent.putExtra(ISCardScanActivity.EXTRA_KEY_COLOR_NORMAL, -0xfe2d01)
                // 合合信息授权提供的APP_KEY
                intent.putExtra(ISCardScanActivity.EXTRA_KEY_APP_KEY, "964bJLCP3M6N8L7S35R1K2rU")
                // 指定SDK相机模块ISCardScanActivity提示字符串
                intent.putExtra(ISCardScanActivity.EXTRA_KEY_TIPS, "请将行驶证放在框内识别")
                startActivityForResult(intent, 100)
            }
//                start(OCRResultFragment().newInstance())
            mBackBtn -> {
                pop()
            }
            mTitleRL -> {
                val storeFragment = StoreFragment().newInstance()
                val bundle = Bundle()
                bundle.putString("LATITUDE", LATITUDE.toString())
                bundle.putString("LONGITUDE", LONGITUDE.toString())
                bundle.putInt("INDEX", INDEX!!)
                bundle.putString("PAGE_FROM", "OCRListFragment")
                storeFragment.arguments = bundle
                startForResult(storeFragment, 0x009)
            }

            mNewBtn -> {
                start(OCRInsertFragment().newInstance())
            }
        }
    }

    override fun onFragmentResult(requestCode: Int, resultCode: Int, data: Bundle) {
        super.onFragmentResult(requestCode, resultCode, data)
        if (requestCode == 0x009 && resultCode == ISupportFragment.RESULT_OK) {
            if (data.getString("SHOP") != "") {
                INDEX = data.getInt("INDEX")
                Logger.d(data.getInt("INDEX"))
                mShopTV.text = data.getString("SHOP")
                SHOPID = data.getString("SHOPID")
                val user = (activity!!.application as CheshangApplication).getDaoSession().userDao.load(1)
                user.shopId = SHOPID
                user.shopName = data.getString("SHOP")
                user.shopAddress = data.getString("SHOPADDRESS")
                INDEX = data.getInt("INDEX")
                (activity!!.application as CheshangApplication).getDaoSession().userDao.update(user)
            }
        }
    }

    private var mTitleList: MutableList<String>? = mutableListOf()
    private var mDetailList: MutableList<String>? = mutableListOf()
    private var HRACCOUNT: String? = null
    private var INDEX: Int? = -1
    //    private var dialog: LoadingDialog? = null
    private var dialog2: AlertDialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ocr_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
    }

    override fun onEnterAnimationEnd(savedInstanceState: Bundle?) {
        super.onEnterAnimationEnd(savedInstanceState)
//        val loadBuilder = LoadingDialog.Builder(activity)
//                .setCancelable(false)
//                .setCancelOutside(false)
//                .setShowMessage(false)
//        dialog = loadBuilder.create()
//        dialog!!.show()

        mPresenter.getLicenseByHrAccount(HRACCOUNT!!)
    }

    private fun initViews() {
        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        HRACCOUNT = (activity!!.application as CheshangApplication).getDaoSession().userDao.loadAll()[0].hrAccount
        Logger.d((activity!!.application as CheshangApplication).getDaoSession().userDao.loadAll()[0].shopName)
//        if ((activity!!.application as CheshangApplication).getDaoSession().userDao.loadAll()[0].shopName == null)
//            mShopTV.text = "附近无车商店"
//        else
//            mShopTV.text = (activity!!.application as CheshangApplication).getDaoSession().userDao.loadAll()[0].shopName

        if ((activity!!.application as CheshangApplication).getDaoSession().userDao.loadAll()[0].shopName != "")
            mShopTV.text = (activity!!.application as CheshangApplication).getDaoSession().userDao.loadAll()[0].shopName
        dialog2 = LoadingDialog2(activity!!).showDialog()

        mLocationClient = LocationClient(activity!!.applicationContext)
        mLocationClient!!.registerLocationListener(myListener)
        mLocationClient!!.start()
        mTitleRL.setOnClickListener(this)
        mBackBtn.setOnClickListener(this)
        mScanRL.setOnClickListener(this)
        mNewBtn.setOnClickListener(this)
    }

    fun newInstance(): OCRListFragment {
        val args = Bundle()
        val fragment = OCRListFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }

    inner class MyLocationListener : BDAbstractLocationListener() {
        override fun onReceiveLocation(location: BDLocation) {
            LATITUDE = location.latitude    //获取纬度信息
            LONGITUDE = location.longitude    //获取经度信息
            if (!locationFlag!!) {
                locationFlag = true
//                mPresenter.getShopByHrAccountAndPosition(HRACCOUNT!!, LATITUDE.toString(), LONGITUDE.toString())
            }
        }
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
//        mShopTV.text = (activity!!.application as CheshangApplication).getDaoSession().userDao.loadAll()[0].shopName
        if ((activity!!.application as CheshangApplication).getDaoSession().userDao.loadAll()[0].shopName != "")
            mShopTV.text = (activity!!.application as CheshangApplication).getDaoSession().userDao.loadAll()[0].shopName
    }
}
