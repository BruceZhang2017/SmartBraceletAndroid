package com.sinophy.smartbracelet.mine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.kyleduo.switchbutton.SwitchButton;
import com.sinophy.smartbracelet.R;

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
            paramView = this.mLayoutInflater.inflate(R.layout.item_account_safe, null);
            viewHolder.arrowImageView = (ImageView)paramView.findViewById(R.id.iv_arrow);
            viewHolder.keyTextView = (TextView)paramView.findViewById(R.id.tv_key);
            viewHolder.keyDescTextView = (TextView)paramView.findViewById(R.id.tv_key_desc);
            viewHolder.valueTextView = (TextView)paramView.findViewById(R.id.tv_value);
            viewHolder.mSwitch = (SwitchButton)paramView.findViewById(R.id.switchbutton);
            paramView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)paramView.getTag();
        }
        AccountSafeBean accountSafeBean = this.mList.get(paramInt);
        viewHolder.keyTextView.setText(accountSafeBean.getKey());
        viewHolder.keyDescTextView.setText(accountSafeBean.getKeyDesc());
        viewHolder.valueTextView.setText(accountSafeBean.getValue());
        if (paramInt + 1 == this.mList.size()) {
            viewHolder.mSwitch.setVisibility(View.VISIBLE);
            viewHolder.arrowImageView.setVisibility(View.INVISIBLE);
            viewHolder.valueTextView.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.mSwitch.setVisibility(View.INVISIBLE);
            viewHolder.arrowImageView.setVisibility(View.VISIBLE);
            viewHolder.valueTextView.setVisibility(View.VISIBLE);
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
