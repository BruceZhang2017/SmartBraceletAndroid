package com.sinophy.smartbracelet.device.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sinophy.smartbracelet.utils.SpUtils;
import com.sinophy.smartbracelet.R;
import com.suke.widget.SwitchButton;

public class PushListAdapter extends BaseAdapter {
    public static final int TYPE_ARROW = 0;
    public static final int TYPE_SWITCH = 1;
    public static final int TYPE_NULL = 2;
    private Context context;
    private String[] comNames;

    public PushListAdapter(Context context, String[] comNames) {
        super();
        this.context = context;
        this.comNames = comNames;
    }

    @Override
    public int getCount() {
        return comNames.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 || position == 6) {
            return TYPE_NULL;
        } else {
            return TYPE_SWITCH;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ComViewHolder comHolder;
        switch (getItemViewType(position)) {
            case TYPE_SWITCH:
                if (convertView == null) {
                    convertView = LayoutInflater.from(context).inflate(R.layout.item_device_detail_switch, null);
                    comHolder = new ComViewHolder();
                    comHolder.com = (TextView) convertView.findViewById(R.id.tv_key);
                    comHolder.icon = (SwitchButton) convertView.findViewById(R.id.switch_button);
                    convertView.setTag(comHolder);
                } else {
                    comHolder = (ComViewHolder) convertView.getTag();
                }
                comHolder.com.setText(comNames[position]);
                if (position == 1) {
                    boolean b = SpUtils.getBoolean(context, "wx");
                    comHolder.icon.setChecked(b);
                } else if (position == 2) {
                    boolean b = SpUtils.getBoolean(context, "qq");
                    comHolder.icon.setChecked(b);
                } else if (position == 3) {
                    boolean b = SpUtils.getBoolean(context, "likedin");
                    comHolder.icon.setChecked(b);
                } else if (position == 4) {
                    boolean b = SpUtils.getBoolean(context, "fb");
                    comHolder.icon.setChecked(b);
                } else if (position == 5) {
                    boolean b = SpUtils.getBoolean(context, "tiwtter");
                    comHolder.icon.setChecked(b);
                } else if (position == 7) {
                    boolean b = SpUtils.getBoolean(context, "whatsapp");
                    comHolder.icon.setChecked(b);
                }

                comHolder.icon.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                        if (position == 1) {
                            SpUtils.putBoolean(context, "wx", isChecked);
                        } else if (position == 2) {
                            SpUtils.putBoolean(context, "qq", isChecked);
                        } else if (position == 3) {
                            SpUtils.putBoolean(context, "likedin", isChecked);
                        } else if (position == 4) {
                            SpUtils.putBoolean(context, "fb", isChecked);
                        } else if (position == 5) {
                            SpUtils.putBoolean(context, "tiwtter", isChecked);
                        } else if (position == 7) {
                            SpUtils.putBoolean(context, "whatsapp", isChecked);
                        }

                    }
                });

                break;
            case TYPE_NULL:
                if (convertView == null) {
                    convertView = LayoutInflater.from(context).inflate(R.layout.item_device_detail_null, null);
                }
                break;
        }
        return convertView;
    }

    class ComViewHolder {
        TextView com;
        SwitchButton icon;
    }
}


