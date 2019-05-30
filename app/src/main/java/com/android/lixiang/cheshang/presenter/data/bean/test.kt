//package com.android.lixiang.cheshang.presenter.data.bean
//
////package com.android.lixiang.cheshang.presenter.data.bean
////
////import com.baidu.location.BDAbstractLocationListener
////import com.baidu.location.BDLocation
////
////class test {
////    inner class MyLocationListener : BDAbstractLocationListener {
////        override fun onReceiveLocation(location: BDLocation) {
////            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
////            //以下只列举部分获取经纬度相关（常用）的结果信息
////            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明
////
////            val latitude = location.latitude    //获取纬度信息
////            val longitude = location.longitude    //获取经度信息
////            val radius = location.radius    //获取定位精度，默认值为0.0f
////
////            val coorType = location.coorType
////            //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准
////
////            val errorCode = location.locType
////            //获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明
////        }
////    }
////}
//
//import android.telephony.TelephonyManager
//
//internal class test {
//    var telephonyManager = context.getSystemService(context.TELEPHONY_SERVICE) as TelephonyManager
//    var imei = telephonyManager.deviceId
//}
