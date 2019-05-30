package com.android.lixiang.cheshang.util

import android.content.Context
import android.support.multidex.MultiDex
import cn.jpush.android.api.JPushInterface
import com.android.lixiang.base.common.BaseApplication
import com.android.lixiang.cheshang.presenter.data.greenDao.DaoMaster
import com.android.lixiang.cheshang.presenter.data.greenDao.DaoSession
import com.baidu.mapapi.SDKInitializer
import com.facebook.stetho.Stetho
import com.orhanobut.logger.Logger
import com.tencent.bugly.Bugly
import com.tencent.bugly.beta.Beta
import com.tencent.bugly.crashreport.CrashReport

class CheshangApplication : BaseApplication() {
    private var daoSession: DaoSession? = null

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        SDKInitializer.initialize(this)
//        CrashReport.initCrashReport(getApplicationContext(), "78c9c04df6", false)
        Bugly.init(this, "6c87f47227", true);
        initDao()
//        JPushInterface.setDebugMode(true) 	// 设置开启日志,发布时请关闭日志
//        JPushInterface.init(this);
//        JPushInterface.setAlias(this,0, "lixiang")
    }

    private fun initDao() {
        val helper = DaoMaster.DevOpenHelper(this, "userinfo")
        val db = helper.writableDb
        daoSession = DaoMaster(db).newSession()
    }

    open fun getDaoSession(): DaoSession {
        return daoSession!!
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base);
        // 安装tinker
        Beta.installTinker();
    }
}