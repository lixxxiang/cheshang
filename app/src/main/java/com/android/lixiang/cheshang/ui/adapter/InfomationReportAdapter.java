package com.android.lixiang.cheshang.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;

import com.android.lixiang.cheshang.R;

import java.util.List;

public class InfomationReportAdapter extends BaseAdapter {
    private List<String> mTitleList;
    private List<String> mDetailList;

    private LayoutInflater inflater;
    private Context context;

    public InfomationReportAdapter() {
    }

    public InfomationReportAdapter(List<String> mTitleList, List<String> mDetailList, Context context) {
        this.mTitleList = mTitleList;
        this.mDetailList = mDetailList;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mTitleList == null ? 0 : mTitleList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        @SuppressLint("ViewHolder")
        View view = inflater.inflate(R.layout.item_infomation_report_listview, null);
        AppCompatTextView mCarStoreTitleTV = view.findViewById(R.id.mCarStoreReportTitleTV);
        AppCompatTextView mCarStoreReportDetailTV = view.findViewById(R.id.mCarStoreReportDetailTV);
        AppCompatTextView mTextInImageTV1 = view.findViewById(R.id.mTextInImageTV1);
        RelativeLayout rl = view.findViewById(R.id.mCarStoreReportTitleRL);
        mCarStoreTitleTV.setText(mTitleList.get(position));
        mCarStoreReportDetailTV.setText(mDetailList.get(position));
        if (position == 0) {
            mTextInImageTV1.setText("车");
            rl.setBackgroundResource(R.drawable.round_relativelayout_5_ff7445);
        } else {
            mTextInImageTV1.setText("行");
            rl.setBackgroundResource(R.drawable.round_relativelayout_5_62b5ff);
        }
        return view;
    }
}
