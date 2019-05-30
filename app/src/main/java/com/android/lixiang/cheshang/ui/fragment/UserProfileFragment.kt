package com.android.lixiang.cheshang.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import com.android.lixiang.base.ui.fragment.BaseMvpFragment

import com.android.lixiang.cheshang.R
import com.android.lixiang.cheshang.presenter.UserProfilePresenter
import com.android.lixiang.cheshang.presenter.data.bean.GetUserByHrAccountBean
import com.android.lixiang.cheshang.presenter.injection.component.DaggerUserProfileFragmentComponent
import com.android.lixiang.cheshang.presenter.injection.module.UserProfileModule
import com.android.lixiang.cheshang.presenter.view.UserProfileView
import com.android.lixiang.cheshang.util.CheshangApplication
import com.android.lixiang.cheshang.util.LoadingDialog
import com.android.lixiang.cheshang.ui.adapter.UserProfileAdapter
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.fragment_user_profile.*
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import java.io.File

class UserProfileFragment : BaseMvpFragment<UserProfilePresenter>(), View.OnClickListener, UserProfileView {
    override fun returnGetUserByHrAccount(loginBean: GetUserByHrAccountBean) {
        if (loginBean.message == "success") {
            bsv.visibility = View.VISIBLE
            dialog!!.dismiss()
            Logger.d(loginBean.data.deptList[0].fullName)
            mDetailList!!.add(loginBean.data.name)
            mDetailList!!.add(loginBean.data.hrAccount)
            mDetailList!!.add(loginBean.data.tel)
            if (loginBean.data.deptList.size == 0) {
                mDetailList!!.add("-")
                mDetailList!!.add("-")
                mDetailList!!.add("-")
                mDetailList!!.add("-")

            } else if (loginBean.data.deptList.size == 1) {
                mDetailList!!.add(loginBean.data.deptList[0].fullName)
                mDetailList!!.add("-")
                mDetailList!!.add("-")
                mDetailList!!.add("-")


            } else if (loginBean.data.deptList.size == 2) {
                mDetailList!!.add(loginBean.data.deptList[0].fullName)
                mDetailList!!.add(loginBean.data.deptList[1].fullName)
                mDetailList!!.add("-")
                mDetailList!!.add("-")


            } else if (loginBean.data.deptList.size == 3) {
                mDetailList!!.add(loginBean.data.deptList[0].fullName)
                mDetailList!!.add(loginBean.data.deptList[1].fullName)
                mDetailList!!.add(loginBean.data.deptList[2].fullName)
                mDetailList!!.add("-")

            } else if (loginBean.data.deptList.size == 4) {
                mDetailList!!.add(loginBean.data.deptList[0].fullName)
                mDetailList!!.add(loginBean.data.deptList[1].fullName)
                mDetailList!!.add(loginBean.data.deptList[2].fullName)
                mDetailList!!.add(loginBean.data.deptList[3].fullName)
            }
            mDetailList!!.add(mRoleList!![loginBean.data.jobId.toInt()])
            mDetailList!!.add("")
            mGetUserByHrAccountBeanDataBean = loginBean.data

            val adapter = UserProfileAdapter(mTitleList, mDetailList, context)
            mUserProfileLV.adapter = adapter
            mUserProfileLV.choiceMode = AbsListView.CHOICE_MODE_SINGLE
            mUserProfileLV.setOnItemClickListener { adapterView, view, i, l ->
                if (i == 8) {
                    val fragment = CarStoreChargeFragment().newInstance()
                    val bundle = Bundle()
                    bundle.putSerializable("SHOP", mGetUserByHrAccountBeanDataBean!!)
                    fragment.arguments = bundle
                    start(fragment)
                }
            }
        }
    }

    override fun injectComponent() {
        DaggerUserProfileFragmentComponent.builder().fragmentComponent(fragmentComponent).userProfileModule(UserProfileModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onClick(v: View?) {
        when (v) {
            mAvatarRL -> {
                selectAvatar()
            }
            mUserProfileDoneTV -> {
                pop()
            }
        }
    }

    private var mTitleList: MutableList<String>? = mutableListOf("用户姓名", "HR 号码", "手机号码", "省公司", "地市公司", "专营支公司", "归属团队", "职务", "负责车商店")
    private var mDetailList: MutableList<String>? = mutableListOf()
    private var mRoleList: MutableList<String>? = mutableListOf("", "团队长", "渠道专员", "驻店员", "销售助理")
    private var HRACCOUNT: String? = null
    private var mGetUserByHrAccountBeanDataBean: GetUserByHrAccountBean.DataBean? = null
    private var dialog: LoadingDialog? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
    }

    override fun onEnterAnimationEnd(savedInstanceState: Bundle?) {
        super.onEnterAnimationEnd(savedInstanceState)
        val loadBuilder = LoadingDialog.Builder(activity)
                .setCancelable(false)
                .setCancelOutside(false)
                .setShowMessage(false)
        dialog = loadBuilder.create()
        dialog!!.show()
        mPresenter.getUserByHrAccount(HRACCOUNT!!)
    }

    private fun initViews() {
        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        mUserProfileToolbar.title = "个人资料"
        (activity as AppCompatActivity).setSupportActionBar(mUserProfileToolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        mUserProfileToolbar.setNavigationOnClickListener {
            pop()
        }

        HRACCOUNT = (activity!!.application as CheshangApplication).getDaoSession().userDao.loadAll()[0].hrAccount
        mAvatarRL.setOnClickListener(this)
        mUserProfileDoneTV.setOnClickListener(this)
    }

    private fun selectAvatar() {
//        val intent = Intent(activity, ClipImageActivity::class.java)
//        intent.putExtra(ImageSelector.MAX_SELECT_COUNT, 1)
//        intent.putExtra(ImageSelector.IS_SINGLE, true)
//        intent.putExtra(ImageSelector.USE_CAMERA, true)
//        startActivityForResult(intent, 0x002)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == 0x002 && data != null) {
//            val images = data.getStringArrayListExtra(ImageSelector.SELECT_RESULT)
//            Logger.d(images)
//            mAvatarIV.setImageURI(Uri.fromFile(File(images[0])))
//            mUserProfileDoneTV.visibility = View.VISIBLE
//        }
    }

    fun newInstance(): UserProfileFragment {
        val args = Bundle()
        val fragment = UserProfileFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }

}
