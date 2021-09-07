package com.sinophy.smartbracelet.device.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cktim.camera2library.Camera2Config;
import com.cktim.camera2library.camera.Camera2RecordActivity;
import com.cktim.camera2library.camera.MessageEvent;
import com.sinophy.smartbracelet.device.DeviceDetailActivity;
import com.sinophy.smartbracelet.device.DeviceDetailPushActivity;
import com.sinophy.smartbracelet.device.DeviceFoundActivity;
import com.sinophy.smartbracelet.device.DeviceInfoActivity;
import com.sinophy.smartbracelet.device.LongsitInternalActivity;
import com.sinophy.smartbracelet.device.WeatherSearchActivity;
import com.sinophy.smartbracelet.device.alarm.AlarmListActivity;
import com.sinophy.smartbracelet.device.model.BLEModel;
import com.sinophy.smartbracelet.device.model.DeviceManager;
import com.sinophy.smartbracelet.global.RealmOperationHelper;
import com.sinophy.smartbracelet.utils.SpUtils;
import com.sinophy.smartbracelet.R;
import com.suke.widget.SwitchButton;
import com.tjdL4.tjdmain.Dev;
import com.tjdL4.tjdmain.L4M;
import com.tjdL4.tjdmain.contr.BractletFuncSet;
import com.tjdL4.tjdmain.contr.L4Command;

import org.greenrobot.eventbus.EventBus;

import io.realm.Realm;


public class ComListAdapter extends BaseAdapter {
    public static final int TYPE_ARROW = 0;
    public static final int TYPE_SWITCH = 1;
    public static final int TYPE_NULL = 2;
    public static final int TYPE_DELETE = 3;
    private Context context;
    public int index;
    private String[] comNames;
    public static BractletFuncSet.FuncSetData funcSetData;
    public String mac;

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
        } else if (position == 12) {
            return TYPE_DELETE;
        }else {
            return TYPE_ARROW;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 4;
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
                    int value = SpUtils.getInt(context, "longsit");
                    if (value <= 0) {
                        value = 60;
                    }
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
                if (position == 3) {
                    comHolder.icon.setChecked(funcSetData.mSW_manage);
                } else if (position == 4) {
                    String s = SpUtils.getString(context, "sed");
                    if (s.length() > 0) {
                        String mac = L4M.GetConnectedMAC();
                        String[] arr = s.split("_");
                        boolean b = Integer.parseInt(arr[1]) > 0;
                        comHolder.icon.setChecked(b);
                    } else {
                        comHolder.icon.setChecked(funcSetData.mSW_sed);
                    }
                } else if (position == 2) {
                    boolean c = SpUtils.getBoolean(context, "call");
                    comHolder.icon.setChecked(c);
                }
                comHolder.icon.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                        if (position == 3) {
                            funcSetData.mSW_manage = isChecked;
                            String ret= L4Command.Brlt_FuncSet(funcSetData);/*ret  返回值类型在文档最下面*/
                        } else if (position == 4) {
                            funcSetData.mSW_sed = isChecked;
                            String ret= L4Command.Brlt_FuncSet(funcSetData);/*ret  返回值类型在文档最下面*/
                        } else if (position == 2) {
                            System.out.println("打开电话监听功能: " + isChecked);
                            SpUtils.putBoolean(context, "call", isChecked);
                        }

                    }
                });
                break;
            case TYPE_NULL:
                if (convertView == null) {
                    convertView = LayoutInflater.from(context).inflate(R.layout.item_device_detail_null, null);
                }
                break;
            case TYPE_DELETE:
                if (convertView == null) {
                    convertView = LayoutInflater.from(context).inflate(R.layout.item_device_detail_delete, null);
                }
                Button button = convertView.findViewById(R.id.btn_delete);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        (new android.app.AlertDialog.Builder(context)).setTitle("提示").setMessage("您确定解除绑定该设备？").setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface param1DialogInterface, int param1Int) {

                            }
                        }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface param1DialogInterface, int param1Int) {
                                // 将设备解绑
                                if (L4M.Get_Connect_flag() == 2) {
                                    Dev.ClearConnectedDevice();
                                    Dev.RemoteDev_CloseManual(); // 手动断开
                                }
                                SpUtils.deleteContent(context, "lastDeviceMac");
                                SpUtils.deleteContent(context, "blood");
                                SpUtils.deleteContent(context, "oxy");
                                DeviceManager.getInstance().models.remove(index);
                                EventBus.getDefault().post(new MessageEvent("healthclear"));
                                EventBus.getDefault().post(new MessageEvent("deleteDevice"));
                                ((DeviceDetailActivity)context).finish();

                            }
                        }).setCancelable(false).show();
                    }
                });
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


