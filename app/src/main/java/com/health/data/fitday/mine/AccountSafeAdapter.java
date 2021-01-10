package com.health.data.fitday.mine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.kyleduo.switchbutton.SwitchButton;
import java.util.List;

class AccountSafeAdapter extends BaseAdapter {
    public Context mContext;

    public LayoutInflater mLayoutInflater;

    public List<AccountSafeBean> mList;

    public AccountSafeAdapter(List<AccountSafeBean> paramList, Context paramContext) {
        this.mList = paramList;
        this.mContext = paramContext;
        this.mLayoutInflater = LayoutInflater.from(paramContext);
    }

    public int getCount() {
        return this.mList.size();
    }

    public Object getItem(int paramInt) {
        return this.mList.get(paramInt);
    }

    public long getItemId(int paramInt) {
        return paramInt;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
        ViewHolder viewHolder;
        if (paramView == null) {
            viewHolder = new ViewHolder();
            paramView = this.mLayoutInflater.inflate(2131427417, null);
            viewHolder.arrowImageView = (ImageView)paramView.findViewById(2131231021);
            viewHolder.keyTextView = (TextView)paramView.findViewById(2131231321);
            viewHolder.keyDescTextView = (TextView)paramView.findViewById(2131231322);
            viewHolder.valueTextView = (TextView)paramView.findViewById(2131231348);
            viewHolder.mSwitch = (SwitchButton)paramView.findViewById(2131231236);
            paramView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)paramView.getTag();
        }
        AccountSafeBean accountSafeBean = this.mList.get(paramInt);
        viewHolder.keyTextView.setText(accountSafeBean.getKey());
        viewHolder.keyDescTextView.setText(accountSafeBean.getKeyDesc());
        viewHolder.valueTextView.setText(accountSafeBean.getValue());
        if (paramInt + 1 == this.mList.size()) {
            viewHolder.mSwitch.setVisibility(0);
            viewHolder.arrowImageView.setVisibility(4);
            viewHolder.valueTextView.setVisibility(4);
        } else {
            viewHolder.mSwitch.setVisibility(4);
            viewHolder.arrowImageView.setVisibility(0);
            viewHolder.valueTextView.setVisibility(0);
        }
        return paramView;
    }

    private static class ViewHolder {
        public ImageView arrowImageView;

        public TextView keyDescTextView;

        public TextView keyTextView;

        public SwitchButton mSwitch;

        public TextView valueTextView;

        private ViewHolder() {}
    }
}
