package com.android.lixiang.cheshang.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.android.lixiang.base.utils.view.CacheUtil;
import com.android.lixiang.cheshang.R;

import java.util.ArrayList;
import java.util.List;

public class MeAdapter extends BaseAdapter {
    private List<String> mTitleList = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;
    private int selectedItem = -1;

    public MeAdapter() {
    }

    public MeAdapter(List<String> mTitleList, Context context) {
        this.mTitleList = mTitleList;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    public MeAdapter(Context context) {
        mTitleList.add("个人资料");
        mTitleList.add("允许通知");
        mTitleList.add("允许访问网络");
        mTitleList.add("清理缓存");
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
        MeViewHolder holder = null;
        if (convertView == null) {
            holder = new MeViewHolder();
            convertView = inflater.inflate(R.layout.item_me_listview, null);
            holder.mTitleTV = convertView.findViewById(R.id.mTitleTV);
            holder.mCacheTV = convertView.findViewById(R.id.mCacheTV);
            holder.mFowardIV = convertView.findViewById(R.id.mFowardIV);
            convertView.setTag(holder);
        } else {
            holder = (MeViewHolder) convertView.getTag();
        }
        holder.mTitleTV.setText(mTitleList.get(position));


        if (position == 3) {
            holder.mFowardIV.setVisibility(View.GONE);
            holder.mCacheTV.setVisibility(View.VISIBLE);
            try {
                holder.mCacheTV.setText(CacheUtil.getTotalCacheSize(context));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            holder.mFowardIV.setVisibility(View.VISIBLE);
            holder.mCacheTV.setVisibility(View.GONE);
        }
        return convertView;
    }

    final class MeViewHolder {
        AppCompatTextView mTitleTV;
        AppCompatTextView mCacheTV;
        AppCompatImageView mFowardIV;
    }
}
