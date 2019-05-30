package com.android.lixiang.cheshang.ui.activity

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.os.Handler
import com.alibaba.android.arouter.launcher.ARouter
import com.android.lixiang.base.utils.view.StatusBarUtil
import com.android.lixiang.cheshang.R
import com.baidu.mapapi.SDKInitializer
import com.facebook.drawee.backends.pipeline.Fresco
import me.yokeyword.fragmentation.SupportActivity
import com.android.lixiang.cheshang.presenter.data.greenDao.RecordDao
import com.android.lixiang.cheshang.util.AlarmService
import com.android.lixiang.cheshang.util.CheshangApplication
import com.android.lixiang.cheshang.ui.fragment.*
import com.blankj.utilcode.util.TimeUtils
import java.text.SimpleDateFormat
import java.util.*
import android.os.CountDownTimer
import android.view.MotionEvent
import com.android.lixiang.cheshang.presenter.data.api.ApiService
import com.android.lixiang.cheshang.presenter.data.bean.AddRegistrationIdBean
import com.android.lixiang.cheshang.util.ToastUtil
import com.orhanobut.logger.Logger
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : SupportActivity() {

    private var times: MutableList<String>? = mutableListOf()
    private var details: MutableList<String>? = mutableListOf()
    private var ids: MutableList<String>? = mutableListOf()
    private val mHandler = Handler()
    private var runnable: Runnable? = null
    private var alarmSet: String? = null
    private var alarmSetIndex: String? = null
    internal var retrofit: Retrofit? = null
    private var addRegistrationIdBean: AddRegistrationIdBean? = null
    internal var apiService: ApiService? = null
    private var flag: Boolean? = false
    private var regisId: String? = null
    private fun getPolling() {
        runnable = object : Runnable {
            override fun run() {
                if (!flag!!) {
                    mHandler.postDelayed(this, (5 * 100).toLong())
                    val sp = getSharedPreferences("REGISID", Context.MODE_PRIVATE)
                    regisId = sp.getString("regisid", "")
                    if (sp.getString("regisid", "") != "") {
                        flag = true
                        retrofit = Retrofit.Builder()
                                .baseUrl("http://59.110.161.48:8089/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build()
                        apiService = retrofit!!.create(ApiService::class.java)
                        if(!(application as CheshangApplication).getDaoSession().userDao.loadAll().isEmpty()){
                            val call = apiService!!.addRegistrationId((application as CheshangApplication).getDaoSession().userDao.loadAll()[0].hrAccount, regisId!!)
                            call.enqueue(object : retrofit2.Callback<AddRegistrationIdBean> {
                                override fun onResponse(call: retrofit2.Call<AddRegistrationIdBean>, response: retrofit2.Response<AddRegistrationIdBean>) {
                                    if (response.body() != null) {
                                        addRegistrationIdBean = response.body()
                                        if (addRegistrationIdBean!!.message == "success") {
                                            Logger.d("success")
                                        } else {

                                        }
                                    } else {
                                    }
                                }
                                override fun onFailure(call: retrofit2.Call<AddRegistrationIdBean>, throwable: Throwable) {}
                            })
                        }
                    }
                }

            }
        }
        mHandler.postDelayed(runnable, (5 * 100).toLong())
    }

    public override fun onDestroy() {
        super.onDestroy()
        mHandler.removeCallbacks(runnable)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SDKInitializer.initialize(applicationContext)


        setContentView(R.layout.activity_main)
//        startActivity(Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"))
        Handler().postDelayed({
            //        if (isDebug()) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog()     // 打印日志
            ARouter.openDebug()   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
//        }


            ARouter.init(application)


//        AndroidBug5497Workaround.assistActivity(this, result)
            Fresco.initialize(this)
//        StatusBarUtil.setTranslucentForImageViewInFragment(this, 0, null)
//        UltimateBar.newImmersionBuilder()
//                .applyNav(true)         // 是否应用到导航栏
//                .build(this)
//                .apply()
        }, 2000)


//        Logger.d(intent.extras.getString("PUSH"))
        getPolling()

        if (Build.VERSION.SDK_INT >= 23) {
            StatusBarUtil.setTranslucentForImageViewInFragment(this, 0, null)
        }
        if (findFragment(IndexFragment::class.java) == null) {
            if (intent.extras != null) {
                if (intent.extras.getString("PUSH") == "push") {
                    val fragment = MessageDetailFragment().newInstance()
                    val bundle = Bundle()
                    bundle.putString("NAME", intent.extras.getString("NAME"))
                    bundle.putString("DETAIL", intent.extras.getString("DETAIL"))
                    bundle.putString("TIME", intent.extras.getString("TIME"))
                    fragment.arguments = bundle
                    loadRootFragment(R.id.fl_tab_container, fragment)
                } else {
                    loadRootFragment(R.id.fl_tab_container, LauncherFragment().newInstance())
//                    loadRootFragment(R.id.fl_tab_container, OCRListFragment().newInstance())
                }


            } else {
                loadRootFragment(R.id.fl_tab_container, LauncherFragment().newInstance())
//                loadRootFragment(R.id.fl_tab_container, OCRListFragment().newInstance())

            }
        }

        setAlarm()
    }


    override fun finish() {
        super.finish()
        overridePendingTransition(0, 0)
    }

    


    @SuppressLint("SimpleDateFormat")
    private fun setAlarm() {
        val sp = getSharedPreferences("RECORD", Context.MODE_PRIVATE)
        if (sp.getString("record", "") != "") {
            val list = (application as CheshangApplication).getDaoSession().recordDao.queryBuilder().where(RecordDao.Properties.IsToday.eq("true")).list()
            if (!list.isEmpty()) {
                for (i in 0 until list.size) {
                    if (list[i].realTime != null) {
                        times!!.add(list[i].realTime)
                        details!!.add(list[i].detail)
                        ids!!.add(list[i].id.toString())
                    }
                }

                val year = TimeUtils.getNowString(SimpleDateFormat("yyyy-MM-dd")).split("-")[0]
                val month = TimeUtils.getNowString(SimpleDateFormat("yyyy-MM-dd")).split("-")[1]
                val day = TimeUtils.getNowString(SimpleDateFormat("yyyy-MM-dd")).split("-")[2]
                val intentArray: ArrayList<PendingIntent> = ArrayList()
                val alarmManager = arrayOfNulls<AlarmManager>(4)
                for (i in 0 until times!!.size) {
                    val time = times!![i].split(" ")[1]
                    val intent = Intent(this, AlarmService::class.java)
                    intent.putExtra("TIME", times!![i])
                    intent.putExtra("DETAIL", details!![i])
                    intent.action = AlarmService.ACTION_ALARM
                    val myCal2 = Calendar.getInstance()
                    myCal2.set(year.toInt(), month.toInt() - 1, day.toInt(), time.split(":")[0].toInt(), time.split(":")[1].toInt() - 5, 0)
                    val pendingIntent = PendingIntent.getService(this, i, intent, PendingIntent.FLAG_UPDATE_CURRENT)
                    alarmManager[i] = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                    alarmSet = if (alarmSet == null) {
                        ids!![i] + ","
                    } else
                        alarmSet + ids!![i] + ","

                    alarmSetIndex = if (alarmSetIndex == null) {
                        i.toString() + ","
                    } else
                        alarmSetIndex + i.toString() + ","
                    val diff = System.currentTimeMillis() - myCal2.timeInMillis
                    if (diff < 0)
                        alarmManager[i]!!.set(AlarmManager.RTC_WAKEUP, myCal2.timeInMillis, pendingIntent)
                    intentArray.add(pendingIntent)
                }

                var mSharedPreferences: SharedPreferences? = null
                mSharedPreferences = getSharedPreferences("ALARMSET", Context.MODE_PRIVATE)
                val editor = mSharedPreferences!!.edit()
                editor.putString("alarmset", alarmSet!!.substring(0, alarmSet!!.length - 1))
                editor.putString("alarmsetIndex", alarmSetIndex!!.substring(0, alarmSet!!.length - 1))
                editor.putString("alarmsetIndexMax", (times!!.size - 1).toString())
                editor.apply()
            }
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (ev?.action == MotionEvent.ACTION_DOWN) {
            // 判断连续点击事件时间差
            if (Utils().isFastClick()) {
                return true
            }
        }
        return super.dispatchTouchEvent(ev)
    }
}
