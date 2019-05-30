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

public class UserProfileAdapter extends BaseAdapter {
    private List<String> mTitleList;
    private List<String> mDetailList;
    private LayoutInflater inflater;
    private Context context;
    private int selectedItem = -1;

    public UserProfileAdapter() {
    }

    public UserProfileAdapter(List<String> mTitleList, List<String> mDetailList, Context context) {
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

    public void setSelectedItem(int selectedItem) {
        this.selectedItem = selectedItem;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        @SuppressLint("ViewHolder")
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_user_profile_listview, null);
            holder.mTitleTV = convertView.findViewById(R.id.mTitleTV);
            holder.mDetailTV = convertView.findViewById(R.id.mDetailTV);
            holder.mCarStoreIV = convertView.findViewById(R.id.mCarStoreIV);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mTitleTV.setText(mTitleList.get(position));
        holder.mDetailTV.setText(mDetailList.get(position));

        if (position == 8) {
            holder.mCarStoreIV.setVisibility(View.VISIBLE);
        }else{
            holder.mCarStoreIV.setVisibility(View.GONE);
        }
        return convertView;
    }

    final class ViewHolder {
        AppCompatTextView mTitleTV;
        AppCompatTextView mDetailTV;
        AppCompatImageView mCarStoreIV;

    }

}

