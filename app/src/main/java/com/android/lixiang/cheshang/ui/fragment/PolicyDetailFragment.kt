package com.android.lixiang.cheshang.ui.fragment

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

import com.android.lixiang.cheshang.R
import es.voghdev.pdfviewpager.library.remote.DownloadFile
import kotlinx.android.synthetic.main.fragment_policy_detail.*
import me.yokeyword.fragmentation.SupportFragment
import java.lang.Exception
import es.voghdev.pdfviewpager.library.RemotePDFViewPager
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter
import es.voghdev.pdfviewpager.library.util.FileUtil
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import com.android.lixiang.cheshang.util.LoadingDialog
import com.example.lixiang.testalertdialog.LoadingDialog2


class PolicyDetailFragment : SupportFragment(), DownloadFile.Listener {

    private var adapter: PDFPagerAdapter? = null
    private var remotePDFViewPager: RemotePDFViewPager? = null
    //    private var dialog: LoadingDialog? = null
    private var dialog2: AlertDialog? = null

    override fun onFailure(e: Exception?) {
        e!!.printStackTrace()
    }

    override fun onProgressUpdate(progress: Int, total: Int) {
    }

    override fun onSuccess(url: String?, destinationPath: String?) {
        adapter = PDFPagerAdapter(activity, FileUtil.extractFileNameFromURL(url))
        remotePDFViewPager!!.adapter = adapter
//        dialog!!.dismiss()
        LoadingDialog2(activity!!).hideDialog(dialog2!!)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_policy_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        initViews()
    }

    override fun onEnterAnimationEnd(savedInstanceState: Bundle?) {
        super.onEnterAnimationEnd(savedInstanceState)
//        val loadBuilder = LoadingDialog.Builder(activity)
//                .setCancelable(false)
//                .setCancelOutside(false)
//                .setShowMessage(false)
//        dialog = loadBuilder.create()
//        dialog!!.show()
        dialog2 = LoadingDialog2(activity!!).showDialog()

        initViews()
    }

    private fun initViews() {
        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        mPolicyDetailToolbar.title = ""

        (activity as AppCompatActivity).setSupportActionBar(mPolicyDetailToolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val url = arguments!!.getString("URL")
        remotePDFViewPager = RemotePDFViewPager(activity, url, this)
        remotePDFViewPager!!.id = R.id.pdfViewPager
        remote_pdf_root.addView(remotePDFViewPager,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        mPolicyDetailToolbar.setNavigationOnClickListener {
            if (adapter != null)
                adapter!!.close()
            pop()
        }
    }

    fun newInstance(): PolicyDetailFragment {
        val args = Bundle()
        val fragment = PolicyDetailFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onDestroy() {
        super.onDestroy()
        if (pdfViewPager != null)
            (pdfViewPager.adapter as PDFPagerAdapter).close()
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }

}
