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

public class CarStoreChargeAdapter extends BaseAdapter {
    private List<String> mTitleList;
    private List<String> mDetailList;
    private LayoutInflater inflater;
    private Context context;
    private int selectedItem = -1;

    public CarStoreChargeAdapter() {
    }

    public CarStoreChargeAdapter(List<String> mTitleList, List<String> mDetailList, Context context) {
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

//    public void setSelectedItem(int selectedItem) {
//        this.selectedItem = selectedItem;
//    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        @SuppressLint("ViewHolder")
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_car_store_listview, null);
            holder.mCarStoreTitleTV = convertView.findViewById(R.id.mCarStoreTitleTV);
            holder.mCarStoreDetailTV = convertView.findViewById(R.id.mCarStoreDetailTV);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mCarStoreTitleTV.setText(mTitleList.get(position));
        holder.mCarStoreDetailTV.setText(mDetailList.get(position));

        return convertView;
    }

    final class ViewHolder {
        AppCompatTextView mCarStoreTitleTV;
        AppCompatTextView mCarStoreDetailTV;
    }
}
