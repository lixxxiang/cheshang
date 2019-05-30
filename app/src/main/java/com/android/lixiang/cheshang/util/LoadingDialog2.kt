package com.example.lixiang.testalertdialog

import android.content.Context
import android.support.v7.app.AlertDialog
import android.view.Gravity
import android.view.View
import com.android.lixiang.cheshang.R

class LoadingDialog2(context: Context) {

    private val context = context
    fun showDialog(): AlertDialog? {
        val builder = AlertDialog.Builder(context)
        val view = View
                .inflate(context, R.layout.dialog_loading2, null)
        builder.setView(view)
        builder.setCancelable(true)
        var dialog = builder.create()
        dialog!!.show()
        val window = dialog!!.window
        window.setGravity(Gravity.CENTER)
        val lp = window.attributes
        lp.width = dip2px(context, 250F)//定义宽度
        lp.height = dip2px(context, 110F)//定义高度
        dialog!!.window.attributes = lp
        dialog!!.setCanceledOnTouchOutside(false)
        dialog!!.setCancelable(false)
        return dialog
    }

    fun hideDialog(dialog2: AlertDialog) {
        dialog2!!.dismiss()
    }

    fun dip2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    fun px2dip(context: Context, pxValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }
}