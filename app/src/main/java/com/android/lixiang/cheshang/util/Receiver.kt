package com.android.lixiang.cheshang.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import org.json.JSONException
import org.json.JSONObject
import cn.jpush.android.api.JPushInterface
import android.os.Bundle
import android.os.Handler
import android.support.v4.content.LocalBroadcastManager
import android.text.TextUtils
import android.util.Log
import com.android.lixiang.cheshang.presenter.data.api.ApiService
import com.android.lixiang.cheshang.presenter.data.bean.AddRegistrationIdBean
import com.android.lixiang.cheshang.presenter.data.greenDao.Msg
import com.android.lixiang.cheshang.presenter.data.greenDao.MsgDao
import com.blankj.utilcode.util.TimeUtils
import com.orhanobut.logger.Logger
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.android.lixiang.cheshang.ui.activity.MainActivity




class Receiver : BroadcastReceiver() {
    private val TAG = "JIGUANG-Example"
    var context: Context? = null
    private var NAME: String? = null
    private var DETAIL: String? = null
    private var TIME: String? = null
    private var ID: String? = null
    internal var retrofit: Retrofit? = null
    private var addRegistrationIdBean: AddRegistrationIdBean? = null
    internal var apiService: ApiService? = null
    override fun onReceive(context: Context, intent: Intent) {
        this.context = context
        try {
            val bundle = intent.extras
            Log.d(TAG, "[MyReceiver] onReceive - " + intent.action + ", extras: " + printBundle(bundle!!))
            when {
                JPushInterface.ACTION_REGISTRATION_ID == intent.action -> {
                    val regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID)
                    Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId!!)
                    var mSharedPreferences: SharedPreferences? = null
                    mSharedPreferences = context.getSharedPreferences("REGISID", Context.MODE_PRIVATE)
                    val editor = mSharedPreferences!!.edit()
                    editor.putString("regisid", regId)
                    editor.apply()
                    //send the Registration Id to your server...
//                    Handler().post {
//                        retrofit = Retrofit.Builder()
//                                .baseUrl("http://59.110.161.48:8089/")
//                                .addConverterFactory(GsonConverterFactory.create())
//                                .build()
//                        val call = apiService!!.addRegistrationId((context.applicationContext as CheshangApplication).getDaoSession().userDao.loadAll()[0].hrAccount, regId)
//                        call.enqueue(object : retrofit2.Callback<AddRegistrationIdBean> {
//                            override fun onResponse(call: retrofit2.Call<AddRegistrationIdBean>, response: retrofit2.Response<AddRegistrationIdBean>) {
//                                if (response.body() != null) {
//                                    addRegistrationIdBean = response.body()
//                                    if (addRegistrationIdBean!!.message == "success") {
//                                        Logger.d("success")
//                                    } else {
//                                        ToastUtil().toast2(context, "JPush initial failed")
//                                    }
//                                } else {
//                                }
//                            }
//
//                            override fun onFailure(call: retrofit2.Call<AddRegistrationIdBean>, throwable: Throwable) {}
//                        })
//                    }



                }
                JPushInterface.ACTION_MESSAGE_RECEIVED == intent.action -> Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE)!!)
                //                processCustomMessage(context, bundle)
                JPushInterface.ACTION_NOTIFICATION_RECEIVED == intent.action -> {
                    Log.d(TAG, "[MyReceiver] 接收到推送下来的通知")
                    val notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID)
                    Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: $notifactionId")
                    val msg = Msg()
                    msg.detail = DETAIL
                    msg.name = NAME
                    msg.time = TIME
                    msg.msgId = ID
                    msg.isRead = "false"
                    if (msg.name != null && msg.detail != null) {
                        (context.applicationContext as CheshangApplication).getDaoSession().msgDao.insert(msg)
                    }
                }
                JPushInterface.ACTION_NOTIFICATION_OPENED == intent.action -> {
                    Log.d(TAG, "[MyReceiver] 用户点击打开了通知")

                    val joes = (context.applicationContext as CheshangApplication).getDaoSession().msgDao.queryBuilder().where(MsgDao.Properties.MsgId.eq(ID)).list()
                    val msg = (context.applicationContext as CheshangApplication).getDaoSession().msgDao.load(joes[0].id)
                    msg.isRead = "true"
                    (context.applicationContext as CheshangApplication).getDaoSession().msgDao.update(msg)
                    bundle.putString("PUSH", "push")
                    bundle.putString("NAME", NAME)
                    bundle.putString("DETAIL", DETAIL)
                    bundle.putString("TIME", TIME)
                    bundle.putString("ID", ID)
                    val i = Intent(context, MainActivity::class.java)
                    i.putExtras(bundle)
                    //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    context.startActivity(i)

                }
                JPushInterface.ACTION_RICHPUSH_CALLBACK == intent.action -> Log.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA)!!)
                //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..
                JPushInterface.ACTION_CONNECTION_CHANGE == intent.action -> {
                    val connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false)
                    Log.w(TAG, "[MyReceiver]" + intent.action + " connected state change to " + connected)
                }
                else -> Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.action!!)
            }
        } catch (e: Exception) {

        }


    }

    // 打印所有的 intent extra 数据
    private fun printBundle(bundle: Bundle): String {

        val sb = StringBuilder()
        for (key in bundle.keySet()) {
            Log.d(TAG, key)
            if (key == JPushInterface.EXTRA_NOTIFICATION_ID) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key))
            } else if (key == JPushInterface.EXTRA_CONNECTION_CHANGE) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key))
            } else if (key == JPushInterface.EXTRA_EXTRA) {
                if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
                    Log.i(TAG, "This message has no Extra data")
                    continue
                }

                try {
                    val json = JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA))
                    val it = json.keys()

                    while (it.hasNext()) {
                        val myKey = it.next()
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]")
                    }
                } catch (e: JSONException) {
                    Log.e(TAG, "Get message extra JSON error!")
                }

            } else {
                if (key == "cn.jpush.android.ALERT") {
                    DETAIL = bundle.get(key).toString()
                }
                if (key == "cn.jpush.android.NOTIFICATION_CONTENT_TITLE") {
                    NAME = bundle.get(key).toString()
                }
                if (key == "cn.jpush.android.MSG_ID") {
                    ID = bundle.get(key).toString()
                }
                TIME = TimeUtils.getNowString()
                sb.append("\nkey:" + key + ", value:" + bundle.get(key))
            }
        }

        return sb.toString()
    }
}