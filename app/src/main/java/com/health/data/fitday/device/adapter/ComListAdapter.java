package com.health.data.fitday.device.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.cktim.camera2library.Camera2Config;
import com.cktim.camera2library.camera.Camera2RecordActivity;
import com.health.data.fitday.device.DeviceDetailActivity;
import com.health.data.fitday.device.DeviceDetailPushActivity;
import com.health.data.fitday.device.DeviceFoundActivity;
import com.health.data.fitday.device.DeviceInfoActivity;
import com.health.data.fitday.device.LongsitInternalActivity;
import com.health.data.fitday.device.WeatherSearchActivity;
import com.health.data.fitday.device.alarm.AlarmListActivity;
import com.health.data.fitday.utils.SpUtils;
import com.sinophy.smartbracelet.R;
import com.suke.widget.SwitchButton;
import com.tjdL4.tjdmain.contr.BractletFuncSet;
import com.tjdL4.tjdmain.contr.L4Command;

public class ComListAdapter extends BaseAdapter {
    public static final int TYPE_ARROW = 0;
    public static final int TYPE_SWITCH = 1;
    public static final int TYPE_NULL = 2;
    private Context context;
    private String[] comNames;
    public BractletFuncSet.FuncSetData funcSetData;

    public ComListAdapter(Context context, String[] comNames) {
        super();
        this.context = context;
        this.comNames = comNames;
        funcSetData = new BractletFuncSet.FuncSetData();
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
        if (position == 0 || position == 7) {
            return TYPE_NULL;
        } else if (position == 2 || position == 3 || position == 4) {
            return TYPE_SWITCH;
        } else {
            return TYPE_ARROW;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TitleViewHolder titleHolder;
        ComViewHolder comHolder;
        switch (getItemViewType(position)) {
            case TYPE_ARROW:
                if (convertView == null) {
                    convertView = LayoutInflater.from(context).inflate(R.layout.item_device_detail, null);
                    titleHolder = new TitleViewHolder();
                    titleHolder.title = (TextView) convertView.findViewById(R.id.tv_key);
                    titleHolder.value = (TextView) convertView.findViewById(R.id.tv_value);
                    convertView.setTag(titleHolder);
                } else {
                    titleHolder = (TitleViewHolder) convertView.getTag();
                }
                titleHolder.title.setText(comNames[position]);
                titleHolder.value.setText(" ");
                if (position == 5) {
                    int value = SpUtils.getInt(context, "Longsit");
                    titleHolder.value.setText(value + "分钟");
                }
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (position == 1) {
                            Intent intent = new Intent(context, DeviceDetailPushActivity.class);
                            context.startActivity(intent);
                        } else if (position == 8) {
                            Intent intent = new Intent(context, AlarmListActivity.class);
                            context.startActivity(intent);
                            return;
                        } else if (position == 9) {
                            Intent intent = new Intent(context, DeviceFoundActivity.class);
                            context.startActivity(intent);
                        } else if (position == 10) {
                            Intent intent = new Intent(context, DeviceInfoActivity.class);
                            context.startActivity(intent);
                        } else if (position == 5) {
                            Intent intent = new Intent(context, LongsitInternalActivity.class);
                            context.startActivity(intent);
                        } else if (position == 6) {
                            Intent intent = new Intent(context, WeatherSearchActivity.class);
                            context.startActivity(intent);
                        } else if (position == 11) {
                            Camera2Config.ENABLE_CAPTURE=true;
                            if(Build.BRAND .equals("Xiaomi") ){ // 小米手机
                                Camera2Config.PATH_SAVE_PIC= Environment.getExternalStorageDirectory().getAbsolutePath() + "/DCIM/Camera/";
                            }else{ // Meizu 、Oppo
                                Camera2Config.PATH_SAVE_PIC= Environment.getExternalStorageDirectory().getAbsolutePath() + "/DCIM/";
                            }

                            Camera2RecordActivity.start(context);
                            L4Command.CameraOn(((DeviceDetailActivity)context).listener);
                        }
                    }
                });
                break;
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
                if (position == 2) {
                    comHolder.icon.setChecked(funcSetData.mSW_manage);
                } else if (position == 3) {
                    comHolder.icon.setChecked(funcSetData.mSW_sed);
                } else if (position == 4) {

                }
                comHolder.icon.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                        if (position == 2) {
                            funcSetData.mSW_manage = isChecked;
                            String ret= L4Command.Brlt_FuncSet(funcSetData);/*ret  返回值类型在文档最下面*/
                        } else if (position == 3) {
                            funcSetData.mSW_sed = isChecked;
                            String ret= L4Command.Brlt_FuncSet(funcSetData);/*ret  返回值类型在文档最下面*/
                        } else if (position == 4) {

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

    class TitleViewHolder {
        TextView title;
        TextView value;
    }

    class ComViewHolder {
        TextView com;
        SwitchButton icon;
    }
}


