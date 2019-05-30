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

public class OCRListAdapter extends BaseAdapter {
    private List<String> mTitleList;
    private List<String> mDetailList;
    private LayoutInflater inflater;
    private Context context;
    private int selectedItem = -1;

    public OCRListAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
    }

    public OCRListAdapter(Context context, List<String> mTitleList, List<String> mDetailList) {
        this.mTitleList = mTitleList;
        this.mDetailList = mDetailList;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mTitleList.size();
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
            convertView = inflater.inflate(R.layout.item_ocr_list, null);
            holder.mOcrTitleTV = convertView.findViewById(R.id.mOcrTitleTV);
            holder.mOcrDetailTV = convertView.findViewById(R.id.mOcrDetailTV);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mOcrTitleTV.setText(mTitleList.get(position));
        holder.mOcrDetailTV.setText(mDetailList.get(position));

        return convertView;
    }

    final class ViewHolder {
        AppCompatTextView mOcrTitleTV;
        AppCompatTextView mOcrDetailTV;
    }
}
