package com.health.data.fitday.device.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.health.data.fitday.device.model.DialBean;
import com.shehuan.niv.NiceImageView;
import com.sinophy.smartbracelet.R;

import java.util.List;

public class GiftPackageAdapter extends BaseAdapter {
    View.OnClickListener mOnClickListener;
    public List<DialBean> list;

    public int getCount() {
        return list.size();
    }

    public Object getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_gift_package, null);
        }
        TextView tvName = view.findViewById(R.id.tv_dial_name);
        tvName.setText(list.get(position).getDialName());
        NiceImageView ivDial = view.findViewById(R.id.iv_dial);
        ivDial.setImageResource(list.get(position).getImage());
        view.setOnClickListener(mOnClickListener);
        return view;
    }

    public View.OnClickListener getmOnClickListener() {
        return mOnClickListener;
    }

    public void setmOnClickListener(View.OnClickListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
    }
}
