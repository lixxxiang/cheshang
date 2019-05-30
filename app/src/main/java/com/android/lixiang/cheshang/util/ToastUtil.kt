package com.android.lixiang.cheshang.util

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.ConditionVariable
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.support.v7.widget.AppCompatTextView
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.RelativeLayout
import android.widget.Toast
import com.android.lixiang.base.utils.view.DimenUtil
import com.android.lixiang.cheshang.R

import com.baidu.mapapi.BMapManager.getContext
import android.opengl.ETC1.getHeight
import android.view.Display
import com.android.lixiang.cheshang.ui.activity.MainActivity


class ToastUtil {
    fun toast(context: Context, text: String) {
        val toast = Toast(context)
        val view = LayoutInflater.from(context).inflate(R.layout.toast_view, null)
        val rl = view.findViewById<RelativeLayout>(R.id.mToastRL)
        val tv = view.findViewById<AppCompatTextView>(R.id.tv)
        tv.text = text
        val layoutParams = RelativeLayout.LayoutParams(DimenUtil().dip2px(context, 220F), DimenUtil().dip2px(context, 54F))
        rl.layoutParams = layoutParams
        toast.view = view
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }

    fun toast2(context: Context, text: String) {
        val toast = Toast(context)
        val view = LayoutInflater.from(context).inflate(R.layout.toast_view, null)
        val rl = view.findViewById<RelativeLayout>(R.id.mToastRL)
        val tv = view.findViewById<AppCompatTextView>(R.id.tv)
        tv.text = text
        val layoutParams = RelativeLayout.LayoutParams(DimenUtil().dip2px(context, 220F), DimenUtil().dip2px(context, 54F))
        rl.layoutParams = layoutParams
        toast.view = view
        val display = (context as MainActivity).getWindowManager().getDefaultDisplay()
        // 获取屏幕高度
        val height = display.getHeight()
        toast.setGravity(Gravity.BOTTOM, 0, height / 6)
        toast.show()
    }
}
