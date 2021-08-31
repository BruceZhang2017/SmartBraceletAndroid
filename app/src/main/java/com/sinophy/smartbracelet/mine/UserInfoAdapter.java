package com.sinophy.smartbracelet.mine;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinophy.smartbracelet.utils.SpUtils;
import com.sinophy.smartbracelet.R;

import java.util.List;

class UserInfoAdapter extends BaseAdapter {
    public Context mContext;

    public LayoutInflater mLayoutInflater;

    public List<UserInfoBean> mList;

    public UserInfoAdapter(List<UserInfoBean> paramList, Context paramContext) {
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
        ViewHolder viewHolder1;
        if (paramView == null) {
            viewHolder1 = new ViewHolder();
            paramView = this.mLayoutInflater.inflate(R.layout.item_user_info, null);
            viewHolder1.headImageView = (ImageView)paramView.findViewById(R.id.iv_head);
            viewHolder1.keyTextView = (TextView)paramView.findViewById(R.id.tv_key);
            viewHolder1.valueTextView = (TextView)paramView.findViewById(R.id.tv_value);
            paramView.setTag(viewHolder1);
        } else {
            ViewHolder viewHolder = (ViewHolder)paramView.getTag();
            viewHolder1 = viewHolder;
        }
        UserInfoBean userInfoBean = this.mList.get(paramInt);
        viewHolder1.keyTextView.setText(userInfoBean.getKey());
        viewHolder1.valueTextView.setText(userInfoBean.getValue());
        if (paramInt == 0) {
            if (userInfoBean.getValue() != null && userInfoBean.getValue().length() > 0) {
                Uri uri = Uri.parse(userInfoBean.getValue());
                viewHolder1.headImageView.setImageURI(uri);
            }
            viewHolder1.headImageView.setVisibility(View.VISIBLE);
            viewHolder1.valueTextView.setVisibility(View.INVISIBLE);
            String imagePath = SpUtils.getString(mContext, "UserHead");
            if(imagePath!=null) {
                Uri uri = Uri.parse(imagePath);
                Bitmap bm = getBitmapFromUri(mContext, uri);
                viewHolder1.headImageView.setImageBitmap(bm);
            }
        } else {
            viewHolder1.headImageView.setVisibility(View.INVISIBLE);
            viewHolder1.valueTextView.setVisibility(View.VISIBLE);
        }
        return paramView;
    }

    private static class ViewHolder {
        public ImageView headImageView;

        public TextView keyTextView;

        public TextView valueTextView;

        private ViewHolder() {}
    }

    private Bitmap getBitmapFromUri(Context context, Uri uri) {
        Bitmap bitmap = null;
        try {
            // 读取uri所在的图片
            bitmap = MediaStore.Images.Media.getBitmap(
                    context.getContentResolver(), uri);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
