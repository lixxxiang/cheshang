package com.android.lixiang.cheshang.ui.fragment


import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.lixiang.cheshang.R
import com.android.lixiang.cheshang.util.CheshangApplication
import me.yokeyword.fragmentation.SupportFragment

class LauncherFragment : SupportFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_launcher, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
    }

    private fun initViews() {
        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        Handler().postDelayed({
            if ((activity!!.application as CheshangApplication).getDaoSession().userDao.loadAll().size == 0) {
                startWithPop(LoginFragment().newInstance())
            }else{
                startWithPop(IndexFragment().newInstance())
            }
        }, 3000)
    }

    fun newInstance(): LauncherFragment {
        val args = Bundle()
        val fragment = LauncherFragment()
        fragment.arguments = args
        return fragment
    }


}
