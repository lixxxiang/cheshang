package com.android.lixiang.cheshang.ui.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.android.lixiang.cheshang.R
import com.android.lixiang.base.ui.fragment.BaseMvpFragment
import com.android.lixiang.cheshang.presenter.OCRResultPresenter
import com.android.lixiang.cheshang.presenter.data.bean.AddLicenseBean
import com.android.lixiang.cheshang.presenter.data.bean.GetLicenseByHrAccountBean
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
import com.intsig.vlcardscansdk.ResultData
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.fragment_ocr_detail.*
import kotlinx.android.synthetic.main.fragment_policy_list.*
//import kotlinx.android.synthetic.main.fragment_ocr_result.*
import me.yokeyword.fragmentation.ISupportFragment
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import java.util.*


class OCRDetailFragment : SupportFragment(), View.OnClickListener {

    private var dialog: LoadingDialog? = null
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
    private var mLocationClient: LocationClient? = null
    private var LATITUDE: Double? = 0.0
    private var LONGITUDE: Double? = 0.0
    private var locationFlag: Boolean? = false
    private var INDEX: Int? = -1

    override fun onClick(v: View?) {
        when (v) {
//            mDateRL -> {
//                KeyboardUtils.hideSoftInput(activity)
//                DatePickerDialog(activity, R.style.MyDatePickerDialogTheme, onDateSetListener, mYear, mMonth, mDay).show()
//            }
//
//            mConfirmBtn -> {
//                KeyboardUtils.hideSoftInput(activity)
//                val loadBuilder = LoadingDialog.Builder(activity)
//                        .setCancelable(false)
//                        .setCancelOutside(false)
//                        .setShowMessage(false)
//                dialog = loadBuilder.create()
//                dialog!!.show()
//                PLATENUMBER = if (mPlateNumberET.text.toString().isEmpty())
//                    mPlateNumberET.hint.toString()
//                else mPlateNumberET.text.toString()
//
//                SHOPID = (activity!!.application as CheshangApplication).getDaoSession().userDao.loadAll()[0].shopId
//                SHOPNAME = (activity!!.application as CheshangApplication).getDaoSession().userDao.loadAll()[0].shopName
//                SHOPADDRESS = (activity!!.application as CheshangApplication).getDaoSession().userDao.loadAll()[0].shopAddress
//
//                IDENTICODE = if (mIdET.text.toString().isEmpty())
//                    mIdET.hint.toString()
//                else mIdET.text.toString()
//
//                BRANDMODEL = if (mBrandET.text.toString().isEmpty())
//                    mBrandET.hint.toString()
//                else mBrandET.text.toString()
//
//                OWNER = if (mNameET.text.toString().isEmpty())
//                    mNameET.hint.toString()
//                else
//                    mNameET.text.toString()
//
//                TEL = if (mTelET.text.toString().isEmpty())
//                    mTelET.hint.toString()
//                else
//                    mTelET.text.toString()
//                REPORTER = (activity!!.application as CheshangApplication).getDaoSession().userDao.loadAll()[0].name
//                HRACCOUNT = (activity!!.application as CheshangApplication).getDaoSession().userDao.loadAll()[0].hrAccount
//                REGISTDATE = mDateeTV.text.toString()
////                mPresenter.addLicense(PLATENUMBER!!, SHOPID!!, SHOPNAME!!, SHOPADDRESS!!, IDENTICODE!!, BRANDMODEL!!, OWNER!!, TEL!!, REPORTER!!, HRACCOUNT!!, REGISTDATE!!)
//            }
//
//            mBackBtn -> {
//                pop()
//            }

//            mTitleRL -> {
//                val storeFragment = StoreFragment().newInstance()
//                val bundle = Bundle()
//                bundle.putString("LATITUDE", LATITUDE.toString())
//                bundle.putString("LONGITUDE", LONGITUDE.toString())
//                bundle.putInt("INDEX", INDEX!!)
//                bundle.putString("PAGE_FROM", "OCRResultFragment")
//                storeFragment.arguments = bundle
//                startForResult(storeFragment, 0x010)
////                start(StoreFragment().newInstance())
//            }
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ocr_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
    }

//    override fun onFragmentResult(requestCode: Int, resultCode: Int, data: Bundle) {
//        super.onFragmentResult(requestCode, resultCode, data)
//        if (requestCode == 0x010 && resultCode == ISupportFragment.RESULT_OK) {
//            if (data.getString("SHOP") != "") {
//                INDEX = data.getInt("INDEX")
//                Logger.d(data.getInt("INDEX"))
//                mShopTV.text = data.getString("SHOP")
//                SHOPID = data.getString("SHOPID")
//                val user = (activity!!.application as CheshangApplication).getDaoSession().userDao.load(1)
//                user.shopId = SHOPID
//                user.shopName = data.getString("SHOP")
//                user.shopAddress = data.getString("SHOPADDRESS")
//                INDEX = data.getInt("INDEX")
//                (activity!!.application as CheshangApplication).getDaoSession().userDao.update(user)
//            }
//        }
//    }

    private fun initViews() {

        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        mDetailToolbar.title = (activity!!.application as CheshangApplication).getDaoSession().userDao.loadAll()[0].shopName
        (activity as AppCompatActivity).setSupportActionBar(mDetailToolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        mDetailToolbar.setNavigationOnClickListener {
            pop()
        }


//        mTitleRL.setOnClickListener(this)
//        mLocationClient = LocationClient(activity!!.applicationContext)
//        mLocationClient!!.registerLocationListener(myListener)
//        mLocationClient!!.start()
        HRACCOUNT = (activity!!.application as CheshangApplication).getDaoSession().userDao.loadAll()[0].hrAccount
//        mShopTV.text = (activity!!.application as CheshangApplication).getDaoSession().userDao.loadAll()[0].shopName

        val result = arguments!!.getSerializable("DATA") as GetLicenseByHrAccountBean.DataBean

        mPlateNumberET.text = result.plateNumber
        mDateeTV.text = result.registDate
        mIdET.text = result.identiCode
        mBrandET.text = result.brandModel
        mNameET.text = result.owner
        mTelET.text = result.tel
        mEndET.text = result.dueDate
        mEngineET.text = result.engineNumber
        mInsuranceET.text = result.insuranceCompany
    }

    fun newInstance(): OCRDetailFragment {
        val args = Bundle()
        val fragment = OCRDetailFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }

}
