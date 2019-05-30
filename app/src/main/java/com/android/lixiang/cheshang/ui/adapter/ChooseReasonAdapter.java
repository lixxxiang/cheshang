package com.android.lixiang.cheshang.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.android.lixiang.cheshang.R;

import java.util.List;

public class ChooseReasonAdapter extends BaseAdapter {
    private List<String> mTitleList;
    private LayoutInflater inflater;
    private Context context;

    public ChooseReasonAdapter() {
    }

    public ChooseReasonAdapter(List<String> mTitleList, Context context) {
        this.mTitleList = mTitleList;
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
        View view = inflater.inflate(R.layout.item_choose_reason_listview, null);
        AppCompatTextView mCarStoreTitleTV = view.findViewById(R.id.mChooseReasonTitleTV);
        mCarStoreTitleTV.setText(mTitleList.get(position));
        return view;
    }
}
