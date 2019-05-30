package com.android.lixiang.cheshang.ui.fragment

import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.lixiang.cheshang.R
import com.android.lixiang.base.ui.fragment.BaseMvpFragment
import com.android.lixiang.cheshang.presenter.OCRResultPresenter
import com.android.lixiang.cheshang.presenter.data.bean.AddLicenseBean
import com.android.lixiang.cheshang.presenter.injection.component.DaggerOCRResultFragmentComponent
import com.android.lixiang.cheshang.presenter.injection.module.OCRResultModule
import com.android.lixiang.cheshang.presenter.view.OCRResultView
import com.android.lixiang.cheshang.util.CheshangApplication
import com.android.lixiang.cheshang.util.DoneDialog
import com.android.lixiang.cheshang.util.LoadingDialog
import com.android.lixiang.cheshang.util.ToastUtil
import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation
import com.baidu.location.LocationClient
import com.blankj.utilcode.util.KeyboardUtils
import com.example.lixiang.testalertdialog.LoadingDialog2
import com.intsig.vlcardscansdk.ResultData
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.fragment_ocr_result.*
import me.yokeyword.fragmentation.ISupportFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import java.util.*


class OCRResultFragment : BaseMvpFragment<OCRResultPresenter>(), View.OnClickListener, OCRResultView {
    override fun returnAddLicenseError() {
        LoadingDialog2(activity!!).hideDialog(dialog2!!)
        ToastUtil().toast2(activity!!, "网络连接失败，请检查网络设置")
    }

    override fun injectComponent() {
        DaggerOCRResultFragmentComponent.builder().fragmentComponent(fragmentComponent).oCRResultModule(OCRResultModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun returnAddLicense(addLicenseBean: AddLicenseBean) {
        if (addLicenseBean.message == "success") {
            LoadingDialog2(activity!!).hideDialog(dialog2!!)
            popTo(findFragment(InfomationReportFragment().javaClass).javaClass, false)
            ToastUtil().toast2(activity!!, "信息上报成功")
        }
    }

    private var ca = Calendar.getInstance()
    private var mYear = ca.get(Calendar.YEAR)
    private var mMonth = ca.get(Calendar.MONTH)
    private var mDay = ca.get(Calendar.DAY_OF_MONTH)
    private var PLATENUMBER: String? = null
    private var SHOPID: String? = null
    private var SHOPNAME: String? = null
    private var SHOPADDRESS: String? = null
    private var IDENTICODE: String? = null
    private var BRANDMODEL: String? = null
    private var OWNER: String? = null
    private var TEL: String? = null
    private var REPORTER: String? = null
    private var HRACCOUNT: String? = null
    private var REGISTDATE: String? = null
    private var ENGINENUMBER: String? = null
    private var DUEDATE: String? = null
    private var INSURANCECECOMPANY: String? = null
    private var mLocationClient: LocationClient? = null
    private var myListener = MyLocationListener()
    private var LATITUDE: Double? = 0.0
    private var LONGITUDE: Double? = 0.0
    private var locationFlag: Boolean? = false
    private var INDEX: Int? = -1
    private var dialog2: AlertDialog? = null


    override fun onClick(v: View?) {
        when (v) {
            mDateRL -> {
                KeyboardUtils.hideSoftInput(activity)
                DatePickerDialog(activity, R.style.MyDatePickerDialogTheme, onDateSetListener, mYear, mMonth, mDay).show()
            }

            mConfirmBtn -> {
                KeyboardUtils.hideSoftInput(activity)
                SHOPID = (activity!!.application as CheshangApplication).getDaoSession().userDao.loadAll()[0].shopId
                SHOPNAME = (activity!!.application as CheshangApplication).getDaoSession().userDao.loadAll()[0].shopName
                SHOPADDRESS = (activity!!.application as CheshangApplication).getDaoSession().userDao.loadAll()[0].shopAddress
                REPORTER = (activity!!.application as CheshangApplication).getDaoSession().userDao.loadAll()[0].name
                HRACCOUNT = (activity!!.application as CheshangApplication).getDaoSession().userDao.loadAll()[0].hrAccount
                REGISTDATE = if (mDateeTV.text.toString() != "请选择注册日期")
                    mDateeTV.text.toString()
                else
                    ""
                if (!mPlateNumberET.text.toString().isEmpty() &&
                        !mNameET.text.toString().isEmpty() &&
                        !mTelET.text.toString().isEmpty() &&
                        mEndET.text.toString() != "请选择保单止期") {

                    dialog2 = LoadingDialog2(activity!!).showDialog()
                    PLATENUMBER = mPlateNumberET.text.toString()
                    IDENTICODE = mIdET.text.toString()
                    BRANDMODEL = mBrandET.text.toString()
                    OWNER = mNameET.text.toString()
                    TEL = mTelET.text.toString()
                    ENGINENUMBER = mEngineET.text.toString()
                    DUEDATE = mEndET.text.toString()
                    INSURANCECECOMPANY = mInsuranceET.text.toString()
                    mPresenter.addLicense(PLATENUMBER!!,
                            SHOPID!!,
                            SHOPNAME!!,
                            SHOPADDRESS!!,
                            IDENTICODE!!,
                            BRANDMODEL!!,
                            OWNER!!,
                            TEL!!,
                            REPORTER!!,
                            HRACCOUNT!!,
                            REGISTDATE!!,
                            ENGINENUMBER!!,
                            DUEDATE!!,
                            INSURANCECECOMPANY!!)
                } else {
                    ToastUtil().toast2(activity!!, "请填写全部信息再提交")
                }
            }

            mBackBtn -> {
                KeyboardUtils.hideSoftInput(activity!!)
                pop()
            }

            mTitleRL -> {
                val storeFragment = StoreFragment().newInstance()
                val bundle = Bundle()
                bundle.putString("LATITUDE", LATITUDE.toString())
                bundle.putString("LONGITUDE", LONGITUDE.toString())
                bundle.putInt("INDEX", INDEX!!)
                bundle.putString("PAGE_FROM", "OCRResultFragment")
                storeFragment.arguments = bundle
                startForResult(storeFragment, 0x010)
//                start(StoreFragment().newInstance())
            }

            mEndRL -> {
                KeyboardUtils.hideSoftInput(activity)
                DatePickerDialog(activity, R.style.MyDatePickerDialogTheme, onDateSetListener2, mYear, mMonth, mDay).show()
            }
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ocr_result, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
    }

    override fun onFragmentResult(requestCode: Int, resultCode: Int, data: Bundle) {
        super.onFragmentResult(requestCode, resultCode, data)
        if (requestCode == 0x010 && resultCode == ISupportFragment.RESULT_OK) {
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

    private fun initViews() {
        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        mDateRL.setOnClickListener(this)
        mConfirmBtn.setOnClickListener(this)
        mBackBtn.setOnClickListener(this)
        mTitleRL.setOnClickListener(this)
        mEndRL.setOnClickListener(this)
        mLocationClient = LocationClient(activity!!.applicationContext)
        mLocationClient!!.registerLocationListener(myListener)
        mLocationClient!!.start()
        HRACCOUNT = (activity!!.application as CheshangApplication).getDaoSession().userDao.loadAll()[0].hrAccount
        mShopTV.text = (activity!!.application as CheshangApplication).getDaoSession().userDao.loadAll()[0].shopName

        val result = arguments!!.getSerializable("RESULT") as ResultData
        if (result.plateNo != null)
            mPlateNumberET.setText(result.plateNo)
        else
            mPlateNumberET.setText("请输入车牌号")
        if (result.registerDate != null)
            mDateeTV.text = result.registerDate.toString()
        else {
            mDateeTV.text = "请选择注册日期"
        }
        if (result.vin != null)
            mIdET.setText(result.vin)
        else
            mIdET.setText("请输入车辆识别号/车架号")
        if (result.model != null)
            mBrandET.setText(result.model)
        else
            mBrandET.setText("请输入品牌型号")
        if (result.owner != null)
            mNameET.setText(result.owner)
        else
            mNameET.setText("请输入持证人姓名")
    }

    fun newInstance(): OCRResultFragment {
        val args = Bundle()
        val fragment = OCRResultFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }

    private val onDateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
        mYear = year
        mMonth = monthOfYear
        mDay = dayOfMonth
        val days: String = if (mMonth + 1 < 10) {
            StringBuffer().append(mYear).append("-").append(mMonth + 1).append("-").append(mDay).toString()
        } else {
            StringBuffer().append(mYear).append("-").append(mMonth + 1).append("-").append(mDay).toString()
        }

        mDateeTV.text = days
        mDateeTV.setTextColor(Color.parseColor("#686868"))
    }


    private val onDateSetListener2 = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
        mYear = year
        mMonth = monthOfYear
        mDay = dayOfMonth
        val days: String = if (mMonth + 1 < 10) {
            StringBuffer().append(mYear).append("-").append(mMonth + 1).append("-").append(mDay).toString()
        } else {
            StringBuffer().append(mYear).append("-").append(mMonth + 1).append("-").append(mDay).toString()
        }

        mEndET.text = days
        mEndET.setTextColor(Color.parseColor("#686868"))
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
}
