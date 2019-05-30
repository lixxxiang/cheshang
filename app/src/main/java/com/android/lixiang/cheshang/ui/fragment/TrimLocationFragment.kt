package com.android.lixiang.cheshang.ui.fragment

import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.ZoomControls
import com.android.lixiang.base.utils.view.DimenUtil
import com.android.lixiang.cheshang.R
import com.baidu.location.*
import com.baidu.mapapi.map.*
import com.baidu.mapapi.model.LatLng
import com.blankj.utilcode.util.SizeUtils
import kotlinx.android.synthetic.main.fragment_trim_location.*
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator

class TrimLocationFragment : SupportFragment(), SensorEventListener, View.OnClickListener {

    override fun onClick(v: View?) {
        when (v) {
            mTrimCommitRL -> {
                pop()
            }
        }
    }

    private var mTrimMapView: MapView? = null
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
    private var mSensorManager: SensorManager? = null
    private var lati_lt: Double? = 0.0
    private var longi_lt: Double? = 0.0
    private var lati_rb = 0.0
    private var longi_rb = 0.0
    private var located = false

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onSensorChanged(event: SensorEvent?) {
        val x = event!!.values[SensorManager.DATA_X].toDouble()
        if (Math.abs(x - lastX!!) > 1.0) {
            mCurrentDirection = x.toInt()
            locData = MyLocationData.Builder()
                    .accuracy(0F)
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection.toFloat()).latitude(mCurrentLat)
                    .longitude(mCurrentLon).build()
            mBaiduMap!!.setMyLocationData(locData)
        }
        lastX = x
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trim_location, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun initViews() {
        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        mTrimToolbar.title = "地点微调"
        (activity as AppCompatActivity).setSupportActionBar(mTrimToolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        mTrimToolbar.setNavigationOnClickListener {
            pop()
        }
        mSensorManager = activity!!.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        initMap()

//        val rl = RelativeLayout(activity)
//        val rlParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, (DimenUtil().getScreenHeight(context!!) - SizeUtils.getMeasuredHeight(mTrimLL)) + 200)
//        rl.layoutParams = rlParams
//        rl.setBackgroundColor(Color.parseColor("#F0F0F0"))
//        mTrimLL.addView(rl, rlParams)

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

        mTrimMapView!!.map.setOnMapStatusChangeListener(listener)
        mTrimCommitRL.setOnClickListener(this)
    }

    fun newInstance(): TrimLocationFragment {
        val args = Bundle()
        val fragment = TrimLocationFragment()
        fragment.arguments = args
        return fragment
    }

    private fun initMap() {
        val mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL
        mTrimMapView = activity!!.findViewById<MapView>(R.id.mTrimMapView)

        mLocClient = LocationClient(activity)
        mLocClient!!.registerLocationListener(myListener)
        val option = LocationClientOption()
        option.isOpenGps = true
        option.setCoorType("bd09ll")
        option.setScanSpan(1000)
        option.setAddrType("all")
        option.setIsNeedLocationPoiList(true)
        mLocClient!!.locOption = option
        mLocClient!!.start()

        mBaiduMap = mTrimMapView!!.map
        mBaiduMap!!.isMyLocationEnabled = true
        mBaiduMap!!.setMyLocationConfigeration(MyLocationConfiguration(mCurrentMode, true, null))
        val builder = MapStatus.Builder()
        builder.overlook(0f)
        mBaiduMap!!.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()))
        val child = mTrimMapView!!.getChildAt(1)
        if (child != null && (child is ImageView || child is ZoomControls)) {
            child.visibility = View.INVISIBLE
        }
        mTrimMapView!!.showScaleControl(false)
        mTrimMapView!!.showZoomControls(false)
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
            if (location == null || mTrimMapView == null) {
                return
            }

            val locData = MyLocationData.Builder()
                    .accuracy(1000F)
                    .direction(mCurrentDirection.toFloat())
                    .latitude(location.latitude)
                    .longitude(location.longitude).build()
            lati = location.latitude
            longi = location.longitude
            mCurrentLat = location.latitude
            mCurrentLon = location.longitude
            address = location.addrStr
            mCurrentAccracy = location.radius
            poi = location.poiList
            mBaiduMap!!.setMyLocationData(locData)
            if (isFirstLoc) {
                isFirstLoc = false
                val ll = LatLng(location.latitude,
                        location.longitude)
                located = true
                val builder = MapStatus.Builder()
                builder.target(ll).zoom(14.0f)
                mBaiduMap!!.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()))
            }
        }

        fun onConnectHotSpotMessage(s: String, i: Int) {

        }
    }

    override fun onPause() {
        super.onPause()
        mTrimMapView!!.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mTrimMapView!!.onDestroy()
    }

    override fun onResume() {
        mTrimMapView!!.onResume()
        super.onResume()
        mSensorManager!!.registerListener(this, mSensorManager!!.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_UI)
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }

}
