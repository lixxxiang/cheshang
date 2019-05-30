package com.android.lixiang.cheshang.ui.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatTextView
import android.util.DisplayMetrics
import android.view.*
import android.widget.*
import com.android.lixiang.base.utils.view.DimenUtil

import com.android.lixiang.cheshang.R
import com.baidu.location.*
import com.baidu.mapapi.map.*
import com.baidu.mapapi.model.LatLng
import com.blankj.utilcode.util.SizeUtils
import android.view.LayoutInflater
import com.android.lixiang.base.ui.fragment.BaseMvpFragment
import com.android.lixiang.cheshang.presenter.ConfirmCheckPresenter
import com.android.lixiang.cheshang.presenter.data.bean.AddAttendanceBean
import com.android.lixiang.cheshang.presenter.data.bean.UpdateAttendanceByHrAccountBean
import com.android.lixiang.cheshang.presenter.data.bean.UpdateReasonByCreateTimeBean
import com.android.lixiang.cheshang.presenter.injection.component.DaggerConfirmCheckFragmentComponent
import com.android.lixiang.cheshang.presenter.injection.module.ConfirmCheckModule
import com.android.lixiang.cheshang.presenter.view.ConfirmCheckView
import com.android.lixiang.cheshang.util.CheshangApplication
import com.android.lixiang.cheshang.util.LoadingDialog
import com.android.lixiang.cheshang.util.ToastUtil
import com.baidu.mapapi.map.BitmapDescriptorFactory
import com.baidu.mapapi.map.BitmapDescriptorFactory.fromResource
import com.blankj.utilcode.util.ImageUtils
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.TimeUtils
import com.example.lixiang.testalertdialog.LoadingDialog2
import kotlinx.android.synthetic.main.fragment_confirm_check.*
import me.yokeyword.fragmentation.ISupportFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.DefaultNoAnimator
import me.yokeyword.fragmentation.anim.DefaultVerticalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import org.jetbrains.anko.find


class ConfirmCheckFragment : BaseMvpFragment<ConfirmCheckPresenter>(), ConfirmCheckView, View.OnClickListener {

    private var mMapView: MapView? = null
    private var lastX: Double? = 0.0
    private var mCurrentDirection = 0
    private var mCurrentLat = 0.0
    private var mCurrentLon = 0.0
    private var mCurrentAccracy: Float = 0.toFloat()
    private var myListener = MyLocationListenner()
    private var mLocClient: LocationClient? = null
    private var mBaiduMap: BaiduMap? = null
    private var isFirstLoc = true
    private var locData: MyLocationData? = null
    private var lati_lt: Double? = 0.0
    private var longi_lt: Double? = 0.0
    private var lati_rb = 0.0
    private var longi_rb = 0.0
    private var located = false
    private var mCheckTimeTV: AppCompatTextView? = null
    private var mCheckDetailTV: AppCompatTextView? = null
    private var mTitleTV: AppCompatTextView? = null

    private var mRL1: RelativeLayout? = null
    private var mRL2: RelativeLayout? = null
    private var mRL3: RelativeLayout? = null
    private var mRL4: RelativeLayout? = null
    private var mIV1: AppCompatImageView? = null
    private var mIV2: AppCompatImageView? = null
    private var mIV3: AppCompatImageView? = null
    private var mIV4: AppCompatImageView? = null
    private var mCheckDoneRL: RelativeLayout? = null
    private var mCommitRL: RelativeLayout? = null
    private var mTime: String? = null
    private var dialog: AlertDialog? = null
    private var dialogFailure: AlertDialog? = null
    private var dialog2: android.support.v7.app.AlertDialog? = null
    private var dialog3: android.support.v7.app.AlertDialog? = null
    private var dialog4: android.support.v7.app.AlertDialog? = null
    private var dialog5: android.support.v7.app.AlertDialog? = null

    private var mAddress: String? = null
    private var HRACCOUNT: String? = null
    private var ADDRESS: String? = null
    private var SHOPID: String? = null
    private var CREATETIME: String? = null
    private var REASON: Int? = 1


    override fun returnUpdateReasonByCreateTime(updateReasonByCreateTimeBean: UpdateReasonByCreateTimeBean) {
        if (dialog5 != null) {
            LoadingDialog2(activity!!).hideDialog(dialog5!!)
            dialogFailure!!.dismiss()

            if (updateReasonByCreateTimeBean.message == "success") {
                ToastUtil().toast2(activity!!, "已提交失败原因")
            } else {
                ToastUtil().toast2(activity!!, "系统繁忙，暂时无法提交，请稍后再试")
            }
            val bundle = Bundle()
            bundle.putString("STATUS", arguments!!.getString("FROM"))
            setFragmentResult(ISupportFragment.RESULT_OK, bundle)
            pop()
        }
    }

    override fun returnUpdateReasonByCreateTimeError() {

    }


    override fun returnAddAttendanceError() {
        if (dialog3 != null)
            LoadingDialog2(activity!!).hideDialog(dialog3!!)
        else if (dialog4 != null)
            LoadingDialog2(activity!!).hideDialog(dialog4!!)

        ToastUtil().toast2(activity!!, "网络连接失败，请检查网络设置")
    }

    override fun returnUpdateAttendanceByHrAccountError() {
        if (dialog3 != null)
            LoadingDialog2(activity!!).hideDialog(dialog3!!)
        else if (dialog4 != null)
            LoadingDialog2(activity!!).hideDialog(dialog4!!)

        ToastUtil().toast2(activity!!, "网络连接失败，请检查网络设置")
    }

    override fun returnUpdateAttendanceByHrAccount(updateAttendanceByHrAccountBean: UpdateAttendanceByHrAccountBean) {
        if (updateAttendanceByHrAccountBean.message == "success") {
            if(updateAttendanceByHrAccountBean.data.attendanceFlag == 0){
                if (updateAttendanceByHrAccountBean.data.positionFlag == 0) {
                    LoadingDialog2(activity!!).hideDialog(dialog3!!)
                    CREATETIME = updateAttendanceByHrAccountBean.data.createTime
                    dialog2Show()
                    mTitleTV!!.text = "进店打卡失败"
                } else {
                    LoadingDialog2(activity!!).hideDialog(dialog3!!)
                    dialogShow()
                    mCheckDetailTV!!.text = "进店打卡成功"
                }
            }else{
                if (updateAttendanceByHrAccountBean.data.positionFlag == 0) {
                    LoadingDialog2(activity!!).hideDialog(dialog4!!)
                    CREATETIME = updateAttendanceByHrAccountBean.data.createTime
                    dialog2Show()
                    mTitleTV!!.text = "出店打卡失败"
                } else {
                    LoadingDialog2(activity!!).hideDialog(dialog4!!)
                    dialogShow()
                    mCheckDetailTV!!.text = "出店打卡成功"
                }
            }
//            if (dialog3 != null) {
//                LoadingDialog2(activity!!).hideDialog(dialog3!!)
//                dialogShow()
//                mCheckDetailTV!!.text = "进店打卡成功"
//            } else if (dialog4 != null) {
//                LoadingDialog2(activity!!).hideDialog(dialog4!!)
//                dialogShow()
//                mCheckDetailTV!!.text = "出店打卡成功"
//            }
        } else if (updateAttendanceByHrAccountBean.status == -1) {
            if (dialog3 != null)
                LoadingDialog2(activity!!).hideDialog(dialog3!!)
            else if (dialog4 != null)
                LoadingDialog2(activity!!).hideDialog(dialog4!!)
            ToastUtil().toast2(activity!!, "网络连接失败，请检查网络设置")
        }
    }

    override fun returnAddAttendance(addAttendanceBean: AddAttendanceBean) {
        if (addAttendanceBean.message == "success") {
            if (addAttendanceBean.data.attendanceFlag == 0) {
                if (addAttendanceBean.data.positionFlag == 0) {
                    LoadingDialog2(activity!!).hideDialog(dialog3!!)
                    CREATETIME = addAttendanceBean.data.createTime
                    dialog2Show()
                    mTitleTV!!.text = "进店打卡失败"
                } else {
                    LoadingDialog2(activity!!).hideDialog(dialog3!!)
                    dialogShow()
                    mCheckDetailTV!!.text = "进店打卡成功"
                }
            } else {
                if (addAttendanceBean.data.positionFlag == 0) {
                    LoadingDialog2(activity!!).hideDialog(dialog4!!)
                    CREATETIME = addAttendanceBean.data.createTime
                    dialog2Show()
                    mTitleTV!!.text = "出店打卡失败"
                } else {
                    LoadingDialog2(activity!!).hideDialog(dialog4!!)
                    dialogShow()
                    mCheckDetailTV!!.text = "出店打卡成功"
                }
            }
        } else if (addAttendanceBean.status == -1) {
            if (dialog3 != null)
                LoadingDialog2(activity!!).hideDialog(dialog3!!)
            else if (dialog4 != null)
                LoadingDialog2(activity!!).hideDialog(dialog4!!)

            ToastUtil().toast2(activity!!, "网络连接失败，请检查网络设置")
        }
    }

    override fun injectComponent() {
        DaggerConfirmCheckFragmentComponent.builder().fragmentComponent(fragmentComponent).confirmCheckModule(ConfirmCheckModule()).build().inject(this)
        mPresenter.mView = this
    }


    override fun onClick(v: View?) {
        when (v) {

            mCheckInRL -> {
                SHOPID = (activity!!.application as CheshangApplication).getDaoSession().userDao.loadAll()[0].shopId
                if (arguments!!.getString("TAG") == "ADD")
                    mPresenter.addAttendance(HRACCOUNT!!, SHOPID!!, "0", mAddress!!, mCurrentLon.toString(), mCurrentLat.toString())
                else if (arguments!!.getString("TAG") == "UPDATE")
                    mPresenter.updateAttendanceByHrAccount(HRACCOUNT!!, SHOPID!!, "0", mAddress!!, mCurrentLon.toString(), mCurrentLat.toString(), arguments!!.getString("CREATETIME"))

                dialog3 = LoadingDialog2(activity!!).showDialog()

            }

            mCheckOutRL -> {
                SHOPID = (activity!!.application as CheshangApplication).getDaoSession().userDao.loadAll()[0].shopId

                if (arguments!!.getString("TAG") == "ADD")
                    mPresenter.addAttendance(HRACCOUNT!!, SHOPID!!, "1", mAddress!!, mCurrentLon.toString(), mCurrentLat.toString())
                else if (arguments!!.getString("TAG") == "UPDATE")
                    mPresenter.updateAttendanceByHrAccount(HRACCOUNT!!, SHOPID!!, "1", mAddress!!, mCurrentLon.toString(), mCurrentLat.toString(), arguments!!.getString("CREATETIME"))
                dialog4 = LoadingDialog2(activity!!).showDialog()

            }

            mCheckDoneRL -> {
                dialog!!.dismiss()
                val bundle = Bundle()
                bundle.putString("STATUS", arguments!!.getString("FROM"))
                setFragmentResult(ISupportFragment.RESULT_OK, bundle)
                pop()
            }

            mRL1 -> {
                REASON = 1
                mIV1!!.setBackgroundResource(R.drawable.ic_check_blue_highlight)
                mIV2!!.setBackgroundResource(R.drawable.ic_check_blue)
                mIV3!!.setBackgroundResource(R.drawable.ic_check_blue)
                mIV4!!.setBackgroundResource(R.drawable.ic_check_blue)
            }

            mRL2 -> {
                REASON = 2
                mIV2!!.setBackgroundResource(R.drawable.ic_check_blue_highlight)
                mIV1!!.setBackgroundResource(R.drawable.ic_check_blue)
                mIV3!!.setBackgroundResource(R.drawable.ic_check_blue)
                mIV4!!.setBackgroundResource(R.drawable.ic_check_blue)
            }

            mRL3 -> {
                REASON = 3
                mIV3!!.setBackgroundResource(R.drawable.ic_check_blue_highlight)
                mIV2!!.setBackgroundResource(R.drawable.ic_check_blue)
                mIV1!!.setBackgroundResource(R.drawable.ic_check_blue)
                mIV4!!.setBackgroundResource(R.drawable.ic_check_blue)
            }

            mRL4 -> {
                REASON = 0
                mIV4!!.setBackgroundResource(R.drawable.ic_check_blue_highlight)
                mIV2!!.setBackgroundResource(R.drawable.ic_check_blue)
                mIV3!!.setBackgroundResource(R.drawable.ic_check_blue)
                mIV1!!.setBackgroundResource(R.drawable.ic_check_blue)
            }

            mCommitRL -> {
                dialog5 = LoadingDialog2(activity!!).showDialog()
                mPresenter.updateReasonByCreateTime(CREATETIME!!, REASON.toString(), HRACCOUNT!!)
            }
        }
    }


    private fun dialogShow() {
        val builder = android.app.AlertDialog.Builder(context)
        val inflater = LayoutInflater.from(context)
        val v = inflater.inflate(R.layout.dialog_check, null)
        mCheckDoneRL = v.findViewById(R.id.mCheckDoneRL)
        mCheckDetailTV = v.findViewById(R.id.mCheckDetailTV)
        mCheckDoneRL!!.setOnClickListener(this)
        dialog = builder.create()
        dialog!!.setCancelable(false)
        dialog!!.show()
        dialog!!.window.setContentView(v)
        val window = dialog!!.window
        window.setBackgroundDrawableResource(android.R.color.transparent)
        window.setContentView(v)
    }

    private fun dialog2Show() {
        val builder = android.app.AlertDialog.Builder(context)
        val inflater = LayoutInflater.from(context)
        val v = inflater.inflate(R.layout.dialog_check_failure, null)
        mRL1 = v.findViewById(R.id.mRL1)
        mRL2 = v.findViewById(R.id.mRL2)
        mRL3 = v.findViewById(R.id.mRL3)
        mRL4 = v.findViewById(R.id.mRL4)

        mIV1 = v.findViewById(R.id.mIV1)
        mIV2 = v.findViewById(R.id.mIV2)
        mIV3 = v.findViewById(R.id.mIV3)
        mIV4 = v.findViewById(R.id.mIV4)

        mCommitRL = v.findViewById(R.id.mCommitRL)
        mTitleTV = v.findViewById(R.id.mTitleTV)
        mCommitRL!!.setOnClickListener(this)

        mRL1!!.setOnClickListener(this)
        mRL2!!.setOnClickListener(this)
        mRL3!!.setOnClickListener(this)
        mRL4!!.setOnClickListener(this)

        dialogFailure = builder.create()
        dialogFailure!!.setCancelable(false)
        dialogFailure!!.show()
        dialogFailure!!.window.setContentView(v)
        val window = dialogFailure!!.window
        window.setBackgroundDrawableResource(android.R.color.transparent)
        window.setContentView(v)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_confirm_check, container, false)
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

        mConfirmCheckToolbar.title = "确认打卡"
        (activity as AppCompatActivity).setSupportActionBar(mConfirmCheckToolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        mConfirmCheckToolbar.setNavigationOnClickListener {
            //            timeHandler2.removeMessages(1)
            val bundle = Bundle()
            bundle.putString("STATUS", "")
            setFragmentResult(ISupportFragment.RESULT_OK, bundle)
            pop()
        }

        if (arguments!!.getString("FROM") == "IN") {
            mCheckInRL.visibility = View.VISIBLE
            mCheckOutRL.visibility = View.GONE
        } else if (arguments!!.getString("FROM") == "OUT") {
            mCheckInRL.visibility = View.GONE
            mCheckOutRL.visibility = View.VISIBLE
        } else {
            if (arguments!!.getString("FLAG") == "0") {
                mCheckInRL.visibility = View.VISIBLE
                mCheckOutRL.visibility = View.GONE
            } else if (arguments!!.getString("FLAG") == "1") {
                mCheckInRL.visibility = View.GONE
                mCheckOutRL.visibility = View.VISIBLE
            }
        }

        HRACCOUNT = (activity!!.application as CheshangApplication).getDaoSession().userDao.loadAll()[0].hrAccount

        initMap()
//        fragment1 = parentFragment as SupportFragment
//        timeHandler2.sendEmptyMessage(1)
        val rl = RelativeLayout(activity)
        val rlParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, (DimenUtil().getScreenHeight(context!!) - SizeUtils.getMeasuredHeight(mCheckLL)) + 20)
        rl.layoutParams = rlParams
        rl.setBackgroundColor(Color.parseColor("#F0F0F0"))
        mCheckLL.addView(rl, rlParams)

        val listener: BaiduMap.OnMapStatusChangeListener = object : BaiduMap.OnMapStatusChangeListener {
            override fun onMapStatusChangeStart(p0: MapStatus?) {

            }

            override fun onMapStatusChangeStart(p0: MapStatus?, p1: Int) {
            }

            override fun onMapStatusChange(p0: MapStatus?) {

            }

            override fun onMapStatusChangeFinish(p0: MapStatus?) {
                val pt = Point()
                pt.x = 0
                pt.y = 0
                if (mBaiduMap != null) {
                    val ll = mBaiduMap?.projection?.fromScreenLocation(pt)
                    lati_lt = ll?.latitude
                    longi_lt = ll?.longitude

                    if (lati_lt != null && longi_lt != null) {
                        val dm = DisplayMetrics()
                        activity!!.windowManager.defaultDisplay.getMetrics(dm)
                        val pty = Point()
                        pty.x = dm.widthPixels
                        pty.y = dm.heightPixels
                        val lly = mBaiduMap!!.projection.fromScreenLocation(pty)
                        lati_rb = lly.latitude
                        longi_rb = lly.longitude
                    }
                    //右下角经纬度
                }
            }
        }

        mMapView!!.map.setOnMapStatusChangeListener(listener)
        mCheckInRL.setOnClickListener(this)
        mCheckOutRL.setOnClickListener(this)
    }

    override fun onEnterAnimationEnd(savedInstanceState: Bundle?) {
        super.onEnterAnimationEnd(savedInstanceState)
        dialog2 = LoadingDialog2(activity!!).showDialog()
        mLocClient = LocationClient(activity)
        mLocClient!!.registerLocationListener(myListener)
        val option = LocationClientOption()
        option.isOpenGps = true
        option.setAddrType("all")
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");
        option.setScanSpan(1000);
        option.setOpenGps(true);
        option.setLocationNotify(true);
        option.setIgnoreKillProcess(false);
        option.SetIgnoreCacheException(false);
        option.setWifiCacheTimeOut(5 * 60 * 1000);
        option.setEnableSimulateGps(false);
        option.setIsNeedLocationPoiList(true)
        mLocClient!!.locOption = option
        mLocClient!!.start()
    }

    private fun initLocationOption() {
        //定位服务的客户端。宿主程序在客户端声明此类，并调用，目前只支持在主线程中启动
        val locationClient = LocationClient(activity)
        //声明LocationClient类实例并配置定位参数
        val locationOption = LocationClientOption()
        val myLocationListener = MyLocationListener()
        //注册监听函数
        locationClient.registerLocationListener(myLocationListener)
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        locationOption.locationMode = LocationClientOption.LocationMode.Hight_Accuracy
        //可选，默认gcj02，设置返回的定位结果坐标系，如果配合百度地图使用，建议设置为bd09ll;
        locationOption.setCoorType("gcj02")
        //可选，默认0，即仅定位一次，设置发起连续定位请求的间隔需要大于等于1000ms才是有效的
        locationOption.setScanSpan(1000)
        //可选，设置是否需要地址信息，默认不需要
        locationOption.setIsNeedAddress(true)
        //可选，设置是否需要地址描述
        locationOption.setIsNeedLocationDescribe(true)
        //可选，设置是否需要设备方向结果
        locationOption.setNeedDeviceDirect(false)
        //可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        locationOption.isLocationNotify = true
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        locationOption.setIgnoreKillProcess(true)
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        locationOption.setIsNeedLocationDescribe(true)
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        locationOption.setIsNeedLocationPoiList(true)
        //可选，默认false，设置是否收集CRASH信息，默认收集
        locationOption.SetIgnoreCacheException(false)
        //可选，默认false，设置是否开启Gps定位
        locationOption.isOpenGps = true
        //可选，默认false，设置定位时是否需要海拔信息，默认不需要，除基础定位版本都可用
        locationOption.setIsNeedAltitude(false)
        //设置打开自动回调位置模式，该开关打开后，期间只要定位SDK检测到位置变化就会主动回调给开发者，该模式下开发者无需再关心定位间隔是多少，定位SDK本身发现位置变化就会及时回调给开发者
        locationOption.setOpenAutoNotifyMode()
        //设置打开自动回调位置模式，该开关打开后，期间只要定位SDK检测到位置变化就会主动回调给开发者
        locationOption.setOpenAutoNotifyMode(3000, 1, LocationClientOption.LOC_SENSITIVITY_HIGHT)
        //开始定位
        locationClient.start()
    }

    inner class MyLocationListener : BDAbstractLocationListener() {
        override fun onReceiveLocation(location: BDLocation) {
            val sb = StringBuffer(256)
            sb.append("time : ")
            /**
             * 时间也可以使用systemClock.elapsedRealtime()方法 获取的是自从开机以来，每次回调的时间；
             * location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
             */
            sb.append(location.time)
            sb.append("\nlocType : ")// 定位类型
            sb.append(location.locType)
            sb.append("\nlocType description : ")// *****对应的定位类型说明*****
            sb.append(location.locTypeDescription)
            sb.append("\nlatitude : ")// 纬度
            sb.append(location.latitude)
            sb.append("\nlontitude : ")// 经度
            sb.append(location.longitude)
            sb.append("\nradius : ")// 半径
            sb.append(location.radius)
            sb.append("\nCountryCode : ")// 国家码
            sb.append(location.countryCode)
            sb.append("\nCountry : ")// 国家名称
            sb.append(location.country)
            sb.append("\ncitycode : ")// 城市编码
            sb.append(location.cityCode)
            sb.append("\ncity : ")// 城市
            sb.append(location.city)
            sb.append("\nDistrict : ")// 区
            sb.append(location.district)
            sb.append("\nStreet : ")// 街道
            sb.append(location.street)
            sb.append("\naddr : ")// 地址信息
            sb.append(location.addrStr)
            sb.append("\nUserIndoorState: ")// *****返回用户室内外判断结果*****
            sb.append(location.userIndoorState)
            sb.append("\nDirection(not all devices have value): ")
            sb.append(location.direction)// 方向
            sb.append("\nlocationdescribe: ")
            sb.append(location.locationDescribe)// 位置语义化信息
            sb.append("\nPoi: ")// POI信息
            println("-------------" + sb.toString())
        }
    }

    fun newInstance(): ConfirmCheckFragment {
        val args = Bundle()
        val fragment = ConfirmCheckFragment()
        fragment.arguments = args
        return fragment
    }


    override fun onSupportVisible() {
        super.onSupportVisible()
        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

    }

    private fun initMap() {
        val mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL
        mMapView = activity!!.findViewById<MapView>(R.id.mCheckMapView)
        val mCurrentMarker = BitmapDescriptorFactory.fromBitmap(ImageUtils.scale(BitmapFactory
                .decodeResource(resources, R.drawable.ic_location_blue), DimenUtil().dip2px(activity!!, 15F), DimenUtil().dip2px(activity!!, 20F)))
//

        mBaiduMap = mMapView!!.map
        mBaiduMap!!.isMyLocationEnabled = true
//
        mBaiduMap!!.setMyLocationConfigeration(MyLocationConfiguration(mCurrentMode, true, mCurrentMarker))

//        var point = LatLng(43.9865370000,125.4083120000);
//        var option = MarkerOptions()
//                .position(point)
//                .icon(mCurrentMarker);
//        mBaiduMap!!.addOverlay(option);

        val builder = MapStatus.Builder()
        builder.overlook(0f)
        mBaiduMap!!.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()))
        val child = mMapView!!.getChildAt(1)
//        if (child != null && (child is ImageView || child is ZoomControls)) {
//            child.visibility = View.INVISIBLE
//        }
        mMapView!!.showScaleControl(false)
        mMapView!!.showZoomControls(false)
        val mUiSettings = mBaiduMap!!.uiSettings
        mUiSettings.isScrollGesturesEnabled = true
        mUiSettings.isOverlookingGesturesEnabled = true
        mUiSettings.isZoomGesturesEnabled = true
    }

    inner class MyLocationListenner : BDLocationListener {
        var lati: Double = 0.toDouble()
        var longi: Double = 0.toDouble()
        var address: String = ""
        internal lateinit var poi: List<Poi>

        override fun onReceiveLocation(location: BDLocation?) {
            if (location == null || mMapView == null) {
                return
            }

            val locData = MyLocationData.Builder()
                    .accuracy(0F)
                    .direction(mCurrentDirection.toFloat())
                    .latitude(location.latitude)
                    .longitude(location.longitude).build()
            lati = location.latitude
            longi = location.longitude
            mCurrentLat = location.latitude
            mCurrentLon = location.longitude

            /**
             *
             * fake -----------------
             */
//            if (mCurrentLat != 43.986537) {
//                mCurrentLat = 43.986537
//            }
//
//            if (mCurrentLon != 125.408312) {
//                mCurrentLon = 125.408312
//            }

            /**
             * ------------------- fake end
             */

            address = location.addrStr
            mAddress = location.addrStr
            mAddressTV.text = mAddress
            ADDRESS = mAddress

            /**
             *
             * fake -----------------
             */
//            address = "中国吉林省长春市宽城区吉星楼"
//            mAddress = "中国吉林省长春市宽城区吉星楼"
//            mAddressTV.text = mAddress
//            ADDRESS = "中国吉林省长春市宽城区吉星楼"
            /**
             * ------------------- fake end
             */
//            dialog2!!.dismiss()

            LoadingDialog2(activity!!).hideDialog(dialog2!!)

            mCurrentAccracy = location.radius
            poi = location.poiList
            mBaiduMap!!.setMyLocationData(locData)
            if (isFirstLoc) {
                isFirstLoc = false
                val ll = LatLng(location.latitude,
                        location.longitude)
                /**
                 *
                 * fake -----------------
                 */
//                val ll = LatLng(mCurrentLat,
//                        mCurrentLon)
                /**
                 * ------------------- fake end
                 */
                located = true
                val builder = MapStatus.Builder()
                builder.target(ll).zoom(14.0f)
                mBaiduMap!!.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()))
            }
        }

        fun onConnectHotSpotMessage(s: String, i: Int) {

        }
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

    @RequiresApi(Build.VERSION_CODES.M)
    private fun myRequestPermission() {
        val permissions = arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
        requestPermissions(permissions, 1)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        }
    }

    override fun onPause() {
        super.onPause()
        mMapView!!.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mMapView!!.onDestroy()
    }

    override fun onResume() {
        mMapView!!.onResume()
        super.onResume()
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultNoAnimator()
    }

    override fun onBackPressedSupport(): Boolean {
        val bundle = Bundle()
        bundle.putString("STATUS", "")
        setFragmentResult(ISupportFragment.RESULT_OK, bundle)
        return super.onBackPressedSupport()
    }
}
