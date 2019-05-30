package com.android.lixiang.cheshang.ui.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.*
import android.content.Context.MODE_PRIVATE
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AlertDialog
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.facade.annotation.Route
import com.android.lixiang.base.ui.fragment.BaseMvpFragment
import com.android.lixiang.cheshang.R
import com.android.lixiang.cheshang.R.id.*
import com.android.lixiang.cheshang.presenter.LoginPresenter
import com.android.lixiang.cheshang.presenter.data.bean.GetUserByHrAccountBean
import com.android.lixiang.cheshang.presenter.data.bean.LoginBean
import com.android.lixiang.cheshang.presenter.injection.component.DaggerLoginFragmentComponent
import com.android.lixiang.cheshang.presenter.injection.module.LoginModule
import com.android.lixiang.cheshang.presenter.view.LoginView
import com.android.lixiang.cheshang.util.LoadingDialog
import com.android.lixiang.cheshang.util.NetworkChangeReceiver
import com.android.lixiang.cheshang.util.ToastUtil
import com.blankj.utilcode.util.KeyboardUtils
import com.example.lixiang.testalertdialog.LoadingDialog2
import kotlinx.android.synthetic.main.fragment_login.*
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import pub.devrel.easypermissions.EasyPermissions

@Route(path = "/app/login")

class LoginFragment : BaseMvpFragment<LoginPresenter>(), LoginView, View.OnClickListener, EasyPermissions.PermissionCallbacks {
    private val RC_CAMERA_AND_RECORD_AUDIO = 10000

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        permissionFlag = false
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        permissionFlag = true
    }

    private var sharedPreferences: SharedPreferences? = null
    override fun returnGetUserByHrAccountError(e: Throwable) {
//        dialog!!.dismiss()
        LoadingDialog2(activity!!).hideDialog(dialog2!!)

        ToastUtil().toast2(activity!!, "网络连接失败，请检查网络设置")
    }

    override fun returnGetUserByHrAccount(loginBean: GetUserByHrAccountBean) {
//        dialog!!.dismiss()
        LoadingDialog2(activity!!).hideDialog(dialog2!!)

        when (loginBean.status) {
            103 -> {
                ToastUtil().toast(activity!!, "HR号不正确")
            }

            200 -> {
                if (loginBean.data.updateStatus == "1") {
                    ToastUtil().toast2(activity!!, "系统正在审核无法登录原因，待审核通过即可再次登录")
                } else {
                    val fragment = TelephoneFragment().newInstance()
                    val bundle = Bundle()
                    bundle.putString("HRACCOUNT", mLoginET.text.toString())
                    fragment.arguments = bundle
                    start(fragment)
                }
            }
        }
    }

    private fun requestPermissions() {
        val perms = arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (EasyPermissions.hasPermissions(context!!, *perms)) {
            permissionFlag = true
        } else {
            EasyPermissions.requestPermissions(this, "无法执行当前操作，请开启权限", RC_CAMERA_AND_RECORD_AUDIO, *perms)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        activity!!.unregisterReceiver(broadcastReceiver)
        activity!!.unregisterReceiver(broadcastReceiver2)
        activity!!.unregisterReceiver(networkChangeReceiver)

    }

    private var dialog2: AlertDialog? = null

    private var loginBean: LoginBean? = null
    private var intentFilter: IntentFilter? = null
    private var networkChangeReceiver: NetworkChangeReceiver? = null
    private var isNetworkConnected: String? = null
    private var permissionFlag: Boolean? = false
    private var broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            isNetworkConnected = "false"
        }
    }

    private var broadcastReceiver2: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            isNetworkConnected = "true"
        }
    }

    private fun checkAccess() {
        intentFilter = IntentFilter()
        intentFilter!!.addAction("android.net.conn.CONNECTIVITY_CHANGE")
        networkChangeReceiver = NetworkChangeReceiver()
        activity!!.registerReceiver(networkChangeReceiver, intentFilter)
    }

    override fun returnLogin(l: LoginBean) {

    }

    override fun injectComponent() {
        DaggerLoginFragmentComponent.builder().fragmentComponent(fragmentComponent).loginModule(LoginModule()).build().inject(this)
        mPresenter.mView = this
    }

    private var testString: String? = null
    private var clickable: Boolean? = false
    @SuppressLint("NewApi")
    override fun onClick(v: View?) {
        when (v) {
            mLoginRL -> {
                KeyboardUtils.hideSoftInput(activity)
//                ToastUtil().toast(activity!!,"test Buglyyyyyyyy")
//                handlePermisson()
                requestPermissions()
                if (clickable!!) {
                    if (!permissionFlag!!) {
//                        val builder = AlertDialog.Builder(activity!!)
//                        builder.setTitle("无法执行当前操作，请前往设置中心开启权限")
//                        builder.setPositiveButton("确认") { dialog, _ ->
//                            startActivity(Intent(Settings.ACTION_SETTINGS))
//                            dialog.dismiss()
//                        }
//                        builder.setNegativeButton("取消") { dialog, which ->
//                            dialog.dismiss()
//                        }
//                        builder.create().show()
                    } else {
                        if (isNetworkConnected == "true") {
//                        val loadBuilder = LoadingDialog.Builder(activity)
//                                .setCancelable(false)
//                                .setCancelOutside(false)
//                                .setShowMessage(false)
//                        dialog = loadBuilder.create()
//                        dialog!!.show()
                            dialog2 = LoadingDialog2(activity!!).showDialog()

                            mPresenter.getUserByHrAccount(mLoginET.text.toString())
                        } else {
                            ToastUtil().toast2(activity!!, "网络连接失败，请检查网络设置")
                        }
                    }
//                    start(TelephoneFragment().newInstance())


                } else {
//                    Snackbar.make(view!!, "请连接网络后操作", Snackbar.LENGTH_SHORT).show()
//                    ToastUtil().toast(activity!!, "请连接网络后操作")
                }
            }

//            mCannotLoginBtn -> {
//                KeyboardUtils.hideSoftInput(activity)
//                if (isNetworkConnected == "true") {
//                    start(CannotLoginFragment().newInstance())
//                } else {
//                    ToastUtil().toast2(activity!!, "网络连接失败，请检查网络设置")
//                }
//            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun initViews() {
        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        mLoginET.addTextChangedListener(mLoginTextWatcher)
        mLoginRL.setOnClickListener(this)
//        mCannotLoginBtn.setOnClickListener(this)
        testString = arguments!!.getString("key3")
        mLoginET.setText(testString)

        mLoginET.setOnFocusChangeListener { view, b ->
            if (b) {
                mUnderLine.setBackgroundColor(Color.parseColor("#F73E00"))
            } else {
                mUnderLine.setBackgroundColor(Color.parseColor("#F4F4F4"))
            }
        }
//        handlePermisson()
//        requestPermissions()

        sharedPreferences = activity!!.getSharedPreferences("count", MODE_PRIVATE)
        var count = sharedPreferences!!.getInt("count", 0)
        //判断程序是第几次运行，如果是第一次运行则跳转到引导页面
        if (count == 0) {
            val builder = AlertDialog.Builder(activity!!)
            builder.setTitle("为确保功能正常使用，请开启全部权限")
            builder.setPositiveButton("确认") { dialog, _ ->
                //                handlePermisson()
                requestPermissions()
                dialog.dismiss()
            }
            builder.create().show()
        }
        var editor = sharedPreferences!!.edit()
        editor.putInt("count", ++count)
        editor.commit()

        checkAccess()
        activity!!.registerReceiver(broadcastReceiver, IntentFilter("NO_ACCESS"))
        activity!!.registerReceiver(broadcastReceiver2, IntentFilter("ACCESS"))

//        presenter = LoginPresenter(this)
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }

    private var mLoginTextWatcher: TextWatcher = object : TextWatcher {
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            // TODO Auto-generated method stub
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int,
                                       after: Int) {
            // TODO Auto-generated method stub
        }

        override fun afterTextChanged(s: Editable) {
            // TODO Auto-generated method stub
            if (!mLoginET.text.toString().isEmpty()) {
                clickable = true
                mLoginRL.setBackgroundResource(R.drawable.ripple_bg_round_relativelayout_4_red)
                mUnderLine.setBackgroundColor(Color.parseColor("#F73E00"))
            } else {
                clickable = false
                mLoginRL.setBackgroundResource(R.drawable.round_relativelayout_4_gray)
                mUnderLine.setBackgroundColor(Color.parseColor("#F4F4F4"))
            }
        }
    }

    fun newInstance(): LoginFragment {
        val args = Bundle()
        val fragment = LoginFragment()
        fragment.arguments = args
        return fragment
    }

//    @RequiresApi(Build.VERSION_CODES.M)
//    fun handlePermisson() {
//        val permission = Manifest.permission.ACCESS_COARSE_LOCATION
//        val checkSelfPermission = ActivityCompat.checkSelfPermission(activity!!, permission)
//        if (checkSelfPermission == PackageManager.PERMISSION_GRANTED) {
////            Logger.d(PhoneInfoUtils(activity).nativePhoneNumber)
//        } else {
//            if (ActivityCompat.shouldShowRequestPermissionRationale(activity!!, permission)) {
//            } else {
//                myRequestPermission()
//            }
//        }
//    }
//
//    @RequiresApi(Build.VERSION_CODES.M)
//    private fun myRequestPermission() {
//        val permissions = arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION,
//                Manifest.permission.ACCESS_FINE_LOCATION,
//                Manifest.permission.CAMERA,
//                Manifest.permission.READ_EXTERNAL_STORAGE,
//                Manifest.permission.READ_PHONE_STATE,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE)
//        requestPermissions(permissions, 1)
//    }
//
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
////        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
////            Logger.d("0")
////        }
////
////        if (grantResults[1] == PackageManager.PERMISSION_GRANTED) {
////            Logger.d("1")
////        }
////
////        if (grantResults[2] == PackageManager.PERMISSION_GRANTED) {
////            Logger.d("2")
////        }
////
////        if (grantResults[3] == PackageManager.PERMISSION_GRANTED) {
////            Logger.d("3")
////        }
////
////        if (grantResults[4] == PackageManager.PERMISSION_GRANTED) {
////            Logger.d("4")
////        }
////
////        if (grantResults[5] == PackageManager.PERMISSION_GRANTED) {
////            Logger.d("5")
////        }
//        permissionFlag = true
//        for (i in 0 until grantResults.size) {
//            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
//                permissionFlag = false
//            }
//        }
//    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }


    override fun onBackPressedSupport(): Boolean {
//        if (arguments!!.getString("PAGE_FROM") == "MeFragment")
//        activity!!.finish()
//        else
        val home = Intent(Intent.ACTION_MAIN)
        home.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        home.addCategory(Intent.CATEGORY_HOME)
        startActivity(home)
        return true
    }

}
