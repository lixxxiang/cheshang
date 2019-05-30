package com.android.lixiang.cheshang.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.lixiang.cheshang.R;

import java.util.List;

public class PolicyAdapter extends BaseAdapter {
    private List<String> mTitleList;
    private LayoutInflater inflater;
    private Context context;
    private int selectedItem = -1;

    public PolicyAdapter() {
    }

    public PolicyAdapter(List<String> mTitleList, Context context) {
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

    public void setSelectedItem(int selectedItem) {
        this.selectedItem = selectedItem;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        @SuppressLint("ViewHolder")
//        View view = inflater.inflate(R.layout.item_car_store_listview, null);
//        AppCompatTextView mCarStoreTitleTV = view.findViewById(R.id.mCarStoreTitleTV);
//        AppCompatTextView mCarStoreDetailTV = view.findViewById(R.id.mCarStoreDetailTV);
//        AppCompatImageView mCarStoreCheckIV = view.findViewById(R.id.mCarStoreCheckIV);
//        mCarStoreTitleTV.setText(mTitleList.get(position));
//        mCarStoreDetailTV.setText(mDetailList.get(position));
//
//        if (position == selectedItem) {
//            mCarStoreCheckIV.setVisibility(View.VISIBLE);
//        }

        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_policy_list, parent, false);
            holder.mPolicyTitleTV = convertView.findViewById(R.id.mPolicyTitleTV);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mPolicyTitleTV.setText(mTitleList.get(position));
        return convertView;
    }

    final class ViewHolder {
        AppCompatTextView mPolicyTitleTV;
    }
}
