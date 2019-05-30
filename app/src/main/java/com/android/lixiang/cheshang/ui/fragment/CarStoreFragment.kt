package com.android.lixiang.cheshang.ui.fragment

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import com.android.lixiang.base.ui.fragment.BaseMvpFragment

import com.android.lixiang.cheshang.R
import com.android.lixiang.cheshang.presenter.CarStorePresenter
import com.android.lixiang.cheshang.presenter.data.bean.GetShopByHrAccountAndPositionBean
import com.android.lixiang.cheshang.presenter.injection.component.DaggerCarStoreFragmentComponent
import com.android.lixiang.cheshang.presenter.injection.module.CarStoreModule
import com.android.lixiang.cheshang.presenter.view.CarStoreView
import com.android.lixiang.cheshang.util.CheshangApplication
import com.android.lixiang.cheshang.util.LoadingDialog
import com.android.lixiang.cheshang.ui.adapter.CarStoreAdapter
import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation
import com.baidu.location.LocationClient
import kotlinx.android.synthetic.main.fragment_car_store.*
import me.yokeyword.fragmentation.ISupportFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator

class CarStoreFragment : BaseMvpFragment<CarStorePresenter>(), CarStoreView {
    override fun injectComponent() {
        DaggerCarStoreFragmentComponent.builder().fragmentComponent(fragmentComponent).carStoreModule(CarStoreModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun returnGetShopByHrAccountAndPosition(getShopByHrAccountAndPositionBean: GetShopByHrAccountAndPositionBean) {
        if (getShopByHrAccountAndPositionBean.message == "success") {
//            dialog!!.dismiss()
//            dialog!!.cancel()
//            progressBar.visibility = View.GONE
            for (i in 0 until getShopByHrAccountAndPositionBean.data.size) {
                mTitleList!!.add(getShopByHrAccountAndPositionBean.data[i].shopName)
                mIDList!!.add(getShopByHrAccountAndPositionBean.data[i].shopId)
                mShopAddressList!!.add(getShopByHrAccountAndPositionBean.data[i].address)
                val mDetail = format(getShopByHrAccountAndPositionBean.data[i].distance, getShopByHrAccountAndPositionBean.data[i].address)
                mDetailList!!.add(mDetail)
                val adapter = CarStoreAdapter(mTitleList, mDetailList, context)
                mCarStoreLV.adapter = adapter
                mCarStoreLV.choiceMode = AbsListView.CHOICE_MODE_SINGLE
                mCarStoreLV.setOnItemClickListener { adapterView, view, i, l ->
                    mCarStoreLV.visibility = View.VISIBLE
                    adapter.setSelectedItem(i)
                    adapter.notifyDataSetInvalidated()
                    if (arguments!!.getString("PAGE_FROM") == "CarStoreInfoReportFragment") {
                        val bundle = Bundle()
                        bundle.putString("SHOP", mTitleList!![i])
                        bundle.putString("SHOPID", mIDList!![i])
                        bundle.putString("SHOPADDRESS", mShopAddressList!![i])
                        bundle.putInt("INDEX", i)
                        setFragmentResult(ISupportFragment.RESULT_OK, bundle)
                        pop()
                    }


                }

                if (arguments!!.getString("PAGE_FROM") == "CarStoreInfoReportFragment") {
                    if (arguments!!.getInt("INDEX") != -1) {
                        adapter.setSelectedItem(arguments!!.getInt("INDEX"))
                        adapter.notifyDataSetInvalidated()
                    }
                }

            }
        }

    }

    private fun format(distance: Int, address: String): String {
        val km = distance.toDouble() / 1000
        return "距离 " + km.toString() + " km | " + address
    }

    private var mTitleList: MutableList<String>? = mutableListOf()
    private var mDetailList: MutableList<String>? = mutableListOf()
    private var mIDList: MutableList<String>? = mutableListOf()
    private var mShopNameList: MutableList<String>? = mutableListOf()
    private var mShopAddressList: MutableList<String>? = mutableListOf()
    private var mLocationClient: LocationClient? = null
    private var myListener = MyLocationListener()
    private var LATITUDE: Double? = 0.0
    private var LONGITUDE: Double? = 0.0
    private var HRACCOUNT: String? = null
    private var locationFlag: Boolean? = false
    private var dialog: LoadingDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_car_store, container, false)
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
//        progressBar.visibility = View.VISIBLE
    }

    private fun initViews() {
        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        mCarStoreToolbar.title = "车商店选择"
        mCarStoreToolbar.subtitle = (activity!!.application as CheshangApplication).getDaoSession().userDao.loadAll()[0].name
        (activity as AppCompatActivity).setSupportActionBar(mCarStoreToolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        mCarStoreToolbar.setNavigationOnClickListener {
            val bundle = Bundle()
            bundle.putString("SHOP", "")
            bundle.putString("SHOPID", "")
            bundle.putString("SHOPADDRESS", "")
            bundle.putInt("INDEX", 0)
            setFragmentResult(ISupportFragment.RESULT_OK, bundle)
            pop()
        }

        HRACCOUNT = (activity!!.application as CheshangApplication).getDaoSession().userDao.loadAll()[0].hrAccount
        mLocationClient = LocationClient(activity!!.applicationContext)
        mLocationClient!!.registerLocationListener(myListener)
        mLocationClient!!.start()
    }

    override fun onBackPressedSupport(): Boolean {
        val bundle = Bundle()
        bundle.putString("SHOP", "")
        bundle.putString("SHOPID", "")
        bundle.putString("SHOPADDRESS", "")
        bundle.putInt("INDEX", 0)
        setFragmentResult(ISupportFragment.RESULT_OK, bundle)
        return super.onBackPressedSupport()
    }

    fun newInstance(): CarStoreFragment {
        val args = Bundle()
        val fragment = CarStoreFragment()
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
                mPresenter.getShopByHrAccountAndPosition(HRACCOUNT!!, LONGITUDE.toString(), LATITUDE.toString())
            }
        }
    }

}
