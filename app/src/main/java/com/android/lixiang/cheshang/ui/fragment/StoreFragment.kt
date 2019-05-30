package com.android.lixiang.cheshang.ui.fragment

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import com.android.lixiang.base.ui.fragment.BaseMvpFragment

import com.android.lixiang.cheshang.R
import com.android.lixiang.cheshang.presenter.StorePresenter
import com.android.lixiang.cheshang.presenter.data.bean.GetShopByHrAccountAndPositionBean
import com.android.lixiang.cheshang.presenter.injection.component.DaggerStoreFragmentComponent
import com.android.lixiang.cheshang.presenter.injection.module.StoreModule
import com.android.lixiang.cheshang.presenter.view.StoreView
import com.android.lixiang.cheshang.util.CheshangApplication
import com.android.lixiang.cheshang.util.LoadingDialog
import com.android.lixiang.cheshang.util.ToastUtil
import com.android.lixiang.cheshang.ui.adapter.StoreAdapter
import com.example.lixiang.testalertdialog.LoadingDialog2
import kotlinx.android.synthetic.main.fragment_store.*
import me.yokeyword.fragmentation.ISupportFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.DefaultVerticalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator

class StoreFragment : BaseMvpFragment<StorePresenter>(), StoreView {
    override fun returnGetShopByHrAccountAndPositionError() {
//        dialog!!.dismiss()
//        LoadingDialog2(activity!!).hideDialog(dialog2!!)
        progressBar.visibility = View.GONE
        ToastUtil().toast2(activity!!, "网络连接失败，请检查网络设置")
    }

    override fun injectComponent() {
        DaggerStoreFragmentComponent.builder().fragmentComponent(fragmentComponent).storeModule(StoreModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun returnGetShopByHrAccountAndPosition(getShopByHrAccountAndPositionBean: GetShopByHrAccountAndPositionBean) {
//        dialog!!.dismiss()
//        LoadingDialog2(activity!!).hideDialog(dialog2!!)
        progressBar.visibility = View.GONE

        if (getShopByHrAccountAndPositionBean.message == "success") {

            for (i in 0 until getShopByHrAccountAndPositionBean.data.size) {
                mTitleList!!.add(getShopByHrAccountAndPositionBean.data[i].shopName)
                mIDList!!.add(getShopByHrAccountAndPositionBean.data[i].shopId)
                mShopAddressList!!.add(getShopByHrAccountAndPositionBean.data[i].address)
                val mDetail = format(getShopByHrAccountAndPositionBean.data[i].distance, getShopByHrAccountAndPositionBean.data[i].address)
                mDetailList!!.add(mDetail)
                val adapter = StoreAdapter(mTitleList, mDetailList, context)
                mStoreLV.adapter = adapter
                mStoreLV.choiceMode = AbsListView.CHOICE_MODE_SINGLE

                mStoreLV.setOnItemClickListener { adapterView, view, i, l ->
                    mStoreLV.visibility = View.VISIBLE
                    adapter.setSelectedItem(i)
                    adapter.notifyDataSetInvalidated()
                    if (arguments!!.getString("PAGE_FROM") == "WorkFragment" || arguments!!.getString("PAGE_FROM") == "OCRListFragment" || arguments!!.getString("PAGE_FROM") == "OCRResultFragment") {
                        val bundle = Bundle()
                        bundle.putString("SHOP", mTitleList!![i])
                        bundle.putString("SHOPID", mIDList!![i])
                        bundle.putString("SHOPADDRESS", mShopAddressList!![i])
                        bundle.putInt("INDEX", i)
                        setFragmentResult(ISupportFragment.RESULT_OK, bundle)
                        pop()
                    }
                }
                if (arguments!!.getString("PAGE_FROM") == "WorkFragment" || arguments!!.getString("PAGE_FROM") == "OCRListFragment" || arguments!!.getString("PAGE_FROM") == "OCRResultFragment") {
                    if (arguments!!.getInt("INDEX") != -1) {
                        adapter.setSelectedItem(arguments!!.getInt("INDEX"))
                        adapter.notifyDataSetInvalidated()
                    }
                }
            }
        }
    }

    private fun format(distance: Int, address: String): String {
        val km = distance.toDouble() / 1000
        return "距离 " + km.toString() + " km | " + address
    }

    private var mTitleList: MutableList<String>? = mutableListOf()
    private var mDetailList: MutableList<String>? = mutableListOf()
    private var mIDList: MutableList<String>? = mutableListOf()
    private var mShopNameList: MutableList<String>? = mutableListOf()
    private var mShopAddressList: MutableList<String>? = mutableListOf()
    //    private var dialog: LoadingDialog? = null
//    private var dialog2: AlertDialog? = null
    private var HRACCOUNT: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_store, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
    }

    override fun onBackPressedSupport(): Boolean {
        val bundle = Bundle()
        bundle.putString("SHOP", "")
        bundle.putString("SHOPID", "")
        bundle.putString("SHOPADDRESS", "")
        bundle.putInt("INDEX", -1)
        setFragmentResult(ISupportFragment.RESULT_OK, bundle)

        return super.onBackPressedSupport()
    }

    override fun onEnterAnimationEnd(savedInstanceState: Bundle?) {
        super.onEnterAnimationEnd(savedInstanceState)
        if (arguments!!.getString("LATITUDE") != null && arguments!!.getString("LONGITUDE") != null) {
//            val loadBuilder = LoadingDialog.Builder(activity)
//                    .setCancelable(false)
//                    .setCancelOutside(false)
//                    .setShowMessage(false)
//            dialog = loadBuilder.create()
//            dialog!!.show()

//            dialog2 = LoadingDialog2(activity!!).showDialog()
            progressBar.visibility = View.VISIBLE
            mPresenter.getShopByHrAccountAndPosition(HRACCOUNT!!, arguments!!.getString("LONGITUDE"), arguments!!.getString("LATITUDE"))
        } else {
            Snackbar.make(view!!, "???", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun initViews() {
        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        mStoreToolbar.title = "车商店信息"
        (activity as AppCompatActivity).setSupportActionBar(mStoreToolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        mStoreToolbar.setNavigationOnClickListener {
            val bundle = Bundle()
            bundle.putString("SHOP", "")
            bundle.putString("SHOPID", "")
            bundle.putString("SHOPADDRESS", "")
            bundle.putInt("INDEX", -1)
            setFragmentResult(ISupportFragment.RESULT_OK, bundle)

            pop()
        }

        HRACCOUNT = (activity!!.application as CheshangApplication).getDaoSession().userDao.loadAll()[0].hrAccount

    }

    fun newInstance(): StoreFragment {
        val args = Bundle()
        val fragment = StoreFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        if (arguments!!.getString("PAGE_FROM") == "WorkFragment")
            return DefaultVerticalAnimator()
        else
            return DefaultHorizontalAnimator()
    }

}
