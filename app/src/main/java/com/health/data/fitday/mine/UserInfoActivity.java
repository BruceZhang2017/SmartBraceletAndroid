package com.health.data.fitday.mine;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.google.gson.Gson;
import com.health.data.fitday.device.model.UserBean;
import com.health.data.fitday.global.CacheUtils;
import com.health.data.fitday.global.RealmOperationHelper;
import com.health.data.fitday.main.BaseActivity;
import com.health.data.fitday.utils.SpUtils;
import com.lljjcoder.style.citylist.CityListSelectActivity;
import com.lljjcoder.style.citylist.bean.CityInfoBean;
import com.sinophy.smartbracelet.R;
import com.tjdL4.tjdmain.L4M;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.OnClick;
import io.realm.Realm;
import per.goweii.actionbarex.common.ActionBarCommon;
import per.goweii.actionbarex.common.OnActionBarChildClickListener;

public class UserInfoActivity extends BaseActivity {
    private static String TAG = "UserInfoActivity";
    @BindView(R.id.simple_action_bar)
    ActionBarCommon actionBarCommon;
    private UserInfoAdapter adapter;

    private OptionsPickerView heightOptions;

    @BindView(R.id.lv_user_info)
    ListView listView;

    private List<UserInfoBean> mList = new ArrayList<>();

    private ArrayList<CardBean> optionsHeight1Items = new ArrayList<>();

    private ArrayList<CardBean> optionsSex = new ArrayList<>();

    private ArrayList<CardBean> optionsWeight1Items = new ArrayList<>();

    private OptionsPickerView pvOptions;

    private TimePickerView pvTime;
    private int SELECT_PICTURE = 0x00;
    private int SELECT_CAMER = 0x01;
    //UserBean user;
    private Bitmap bitmap;
    private OptionsPickerView weightOptions;

    private String getTime(Date paramDate) {
        Log.d("getTime()", "choice date millis: " + paramDate.getTime());
        return (new SimpleDateFormat("yyyy-MM-dd")).format(paramDate);
    }

    private void initOptionHeightPicker() {
        OptionsPickerView optionsPickerView = (new OptionsPickerBuilder((Context) this, new OnOptionsSelectListener() {
            public void onOptionsSelect(int param1Int1, int param1Int2, int param1Int3, View param1View) {
                String str = ((CardBean) UserInfoActivity.this.optionsHeight1Items.get(param1Int1)).getPickerViewText();
                ((UserInfoBean) UserInfoActivity.this.mList.get(5)).setValue(str + "CM");
                //UserInfoActivity.this.user.setHeight(Integer.parseInt(str));
                L4M.SaveUser_Height(str + "CM");
            }
        })).setTitleText("身高").setContentTextSize(20).setSelectOptions(0).isRestoreItem(true).isDialog(false).isCenterLabel(true).setLabels("cm  ", "", "").setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
            public void onOptionsSelectChanged(int param1Int1, int param1Int2, int param1Int3) {
            }
        }).build();
        this.heightOptions = optionsPickerView;
        optionsPickerView.setPicker(this.optionsHeight1Items);
    }

    private void initOptionPicker() {
        OptionsPickerView optionsPickerView = (new OptionsPickerBuilder((Context) this, new OnOptionsSelectListener() {
            public void onOptionsSelect(int param1Int1, int param1Int2, int param1Int3, View param1View) {
                String str = ((CardBean) UserInfoActivity.this.optionsSex.get(param1Int1)).getPickerViewText();
                ((UserInfoBean) UserInfoActivity.this.mList.get(3)).setValue(str);
                //UserInfoActivity.this.user.setSex(param1Int1);
                L4M.SaveUser_Gender(param1Int1 + "");
            }
        })).setTitleText("性别").setContentTextSize(20).setSelectOptions(0).isRestoreItem(true).setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
            public void onOptionsSelectChanged(int param1Int1, int param1Int2, int param1Int3) {
            }
        }).build();
        this.pvOptions = optionsPickerView;
        optionsPickerView.setPicker(this.optionsSex);
    }

    private void initOptionWeightPicker() {
        OptionsPickerView optionsPickerView = (new OptionsPickerBuilder((Context) this, new OnOptionsSelectListener() {
            public void onOptionsSelect(int param1Int1, int param1Int2, int param1Int3, View param1View) {
                String str = ((CardBean) UserInfoActivity.this.optionsWeight1Items.get(param1Int1)).getPickerViewText();
                ((UserInfoBean) UserInfoActivity.this.mList.get(6)).setValue(str + "KG");
                //UserInfoActivity.this.user.setWeight(Integer.parseInt(str));
                L4M.SaveUser_Weight(str + "KG");
            }
        })).setTitleText("体重").setContentTextSize(20).setSelectOptions(0).isRestoreItem(true).isDialog(false).isCenterLabel(true).setLabels("kg  ", "", "").setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
            public void onOptionsSelectChanged(int param1Int1, int param1Int2, int param1Int3) {
            }
        }).build();
        this.weightOptions = optionsPickerView;
        optionsPickerView.setPicker(this.optionsWeight1Items);
    }

    private void initTimePicker() {
        TimePickerView timePickerView = (new TimePickerBuilder((Context) this, new OnTimeSelectListener() {
            public void onTimeSelect(Date param1Date, View param1View) {
                Log.i("pvTime", "onTimeSelect");
                String str = UserInfoActivity.this.getTime(param1Date);
                ((UserInfoBean) UserInfoActivity.this.mList.get(4)).setValue(str);
                UserInfoActivity.this.adapter.notifyDataSetChanged();
                //UserInfoActivity.this.user.setBirthday(str);
                L4M.SaveUser_Birthday(str);
            }
        })).setTimeSelectChangeListener(new OnTimeSelectChangeListener() {
            public void onTimeSelectChanged(Date param1Date) {
                Log.i("pvTime", "onTimeSelectChanged");
            }
        }).setType(new boolean[]{true, true, true, false, false, false}).isDialog(false).isCenterLabel(false).setLabel(getResources().getString(R.string.pickerview_year), getResources().getString(R.string.pickerview_month), getResources().getString(R.string.pickerview_day), "", "", "").addOnCancelClickListener(new View.OnClickListener() {
            public void onClick(View param1View) {
                Log.i("pvTime", "onCancelClickListener");
            }
        }).setLineSpacingMultiplier(2.0F).setContentTextSize(20).isAlphaGradient(true).build();
        this.pvTime = timePickerView;
        Dialog dialog = timePickerView.getDialog();
        if (dialog != null) {
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
            layoutParams.leftMargin = 0;
            layoutParams.rightMargin = 0;
            this.pvTime.getDialogContainerLayout().setLayoutParams((ViewGroup.LayoutParams) layoutParams);
            Window window = dialog.getWindow();
            if (window != null) {
                window.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);
                window.setGravity(Gravity.BOTTOM);
                window.setDimAmount(0.3F);
            }
        }
    }

    private void initializeForHeight() {
        for (int b = 30; b <= 250; b++)
        this.optionsHeight1Items.add(new CardBean(b, "" + b));
    }

    private void initializeForSex() {
        this.optionsSex.add(new CardBean(0, "男"));
        this.optionsSex.add(new CardBean(1, "女"));
    }

    private void initializeForWeight() {
        for (int b = 0; b < 150; b++)
        this.optionsWeight1Items.add(new CardBean(b, "" + b));
    }

    private void updateUserInfo() {
        //SpUtils.putString((Context) this, "User", (new Gson()).toJson(this.user));
        UserBean userBean = new UserBean();
        userBean.setWeight(Integer.parseInt(L4M.GetUser_Weight()));
        userBean.setHeight(Integer.parseInt(L4M.GetUser_Height()));
        userBean.setSex(Integer.parseInt(L4M.GetUser_Gender()));
        userBean.setMobile("13888888888");
        userBean.setBirthday(L4M.GetUser_Birthday());
        userBean.setNickname(L4M.GetUserName());
        RealmOperationHelper.getInstance(Realm.getDefaultInstance()).add(userBean);
        Log.i(TAG, "将用户信息保存至数据库中");
    }

    protected int getLayoutId() {
        return R.layout.activity_user_info;
    }

    protected void initData() {
        //this.user = (UserBean) (new Gson()).fromJson(SpUtils.getString((Context) this, "User"), UserBean.class);
        UserInfoBean userInfoBean1 = new UserInfoBean();
        userInfoBean1.setKey("头像");
        this.mList.add(userInfoBean1);
        UserInfoBean userInfoBean2 = new UserInfoBean();
        userInfoBean2.setKey("昵称");
        String str1 = L4M.GetUserName(); //this.user.getNickname();
//        String str2 = "未设置";
//        if (str1 == null || this.user.getNickname().length() == 0) {
//            str1 = "未设置";
//        } else {
//            str1 = this.user.getNickname();
//        }
        userInfoBean2.setValue(str1);
        this.mList.add(userInfoBean2);
//        userInfoBean2 = new UserInfoBean();
//        userInfoBean2.setKey("姓名");
//        if (this.user.getUsername() == null || this.user.getUsername().length() == 0) {
//            str1 = "未设置";
//        } else {
//            str1 = this.user.getUsername();
//        }
//        userInfoBean2.setValue(str1);
//        this.mList.add(userInfoBean2);
        userInfoBean2 = new UserInfoBean();
        userInfoBean2.setKey("性别");
        if (L4M.GetUser_Gender().equals("0")) {
            str1 = "男";
        } else {
            str1 = "女";
        }
        userInfoBean2.setValue(str1);
        this.mList.add(userInfoBean2);
        userInfoBean2 = new UserInfoBean();
        userInfoBean2.setKey("出生年月");
        if (L4M.GetUser_Birthday() == null) {
            str1 = "未设置";
        } else {
            str1 = L4M.GetUser_Birthday();
        }
        userInfoBean2.setValue(str1);
        this.mList.add(userInfoBean2);
        userInfoBean2 = new UserInfoBean();
        userInfoBean2.setKey("身高");
        if (L4M.GetUser_Height() == null) {
            str1 = "未设置";
        } else {
            str1 = L4M.GetUser_Height();
        }
        userInfoBean2.setValue(str1);
        this.mList.add(userInfoBean2);
        userInfoBean2 = new UserInfoBean();
        userInfoBean2.setKey("体重");
        if (L4M.GetUser_Weight() == null) {
            str1 = "未设置";
        } else {
            str1 = L4M.GetUser_Weight();
        }
        userInfoBean2.setValue(str1);
        this.mList.add(userInfoBean2);
//        userInfoBean2 = new UserInfoBean();
//        userInfoBean2.setKey("地区");
//        str1 = str2;
//        if (this.user.getArea() != null)
//            if (this.user.getArea().length() == 0) {
//                str1 = str2;
//            } else {
//                str1 = this.user.getArea();
//            }
//        userInfoBean2.setValue(str1);
//        this.mList.add(userInfoBean2);
        UserInfoAdapter userInfoAdapter = new UserInfoAdapter(this.mList, (Context) this);
        this.adapter = userInfoAdapter;
        this.listView.setAdapter((ListAdapter) userInfoAdapter);
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> param1AdapterView, View param1View, int param1Int, long param1Long) {
                switch (param1Int) {
//                    case 7:
//                        Intent intent = new Intent((Context) UserInfoActivity.this, CityListSelectActivity.class);
//                        UserInfoActivity.this.startActivityForResult(intent, 50);
//                        break;
                    case 5:
                        UserInfoActivity.this.weightOptions.show();
                        break;
                    case 4:
                        UserInfoActivity.this.heightOptions.show();
                        break;
                    case 3:
                        UserInfoActivity.this.pvTime.show();
                        break;
                    case 2:
                        UserInfoActivity.this.pvOptions.show();
                        break;
//                    case 2:
//                        Intent intent1 = new Intent((Context) UserInfoActivity.this, EditContentActivity.class);
//                        intent1.putExtra("title", "姓名");
//                        intent1.putExtra("value", ((UserInfoBean) UserInfoActivity.this.mList.get(2)).getValue());
//                        UserInfoActivity.this.startActivityForResult(intent1, 2);
//                        break;
                    case 1:
                        Intent intent2 = new Intent(UserInfoActivity.this, EditContentActivity.class);
                        intent2.putExtra("title", "昵称");
                        intent2.putExtra("value", ((UserInfoBean) UserInfoActivity.this.mList.get(1)).getValue());
                        UserInfoActivity.this.startActivityForResult(intent2, 1);
                        break;
                    case 0:
                        Intent intent = new Intent(UserInfoActivity.this, EditHeadActivity.class);
                        UserInfoActivity.this.startActivity(intent);
                        break;
                }
            }
        });
        initializeForSex();
        initOptionPicker();
        initTimePicker();
        initializeForHeight();
        initOptionHeightPicker();
        initializeForWeight();
        initOptionWeightPicker();
    }

    protected void initView() {
        ButterKnife.bind((Activity) this);
        this.actionBarCommon.setOnLeftIconClickListener(new OnActionBarChildClickListener() {
            public void onClick(View param1View) {
                UserInfoActivity.this.finish();
            }
        });
        this.actionBarCommon.setOnRightTextClickListener(new OnActionBarChildClickListener() {
            public void onClick(View param1View) {
                UserInfoActivity.this.updateUserInfo();
                UserInfoActivity.this.finish();
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        CityInfoBean cityInfoBean;
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 50) {
            if (resultCode == -1) {
                if (data == null)
                    return;
                cityInfoBean = (CityInfoBean) data.getExtras().getParcelable("cityinfo");
                if (cityInfoBean == null)
                    return;
                ((UserInfoBean) this.mList.get(7)).setValue(cityInfoBean.getName());
                this.adapter.notifyDataSetChanged();
                //this.user.setArea(cityInfoBean.getName());

            }
            return;
        }
        String str = data.getStringExtra("result");
        if (requestCode == 1 && resultCode == 2) {
            ((UserInfoBean) this.mList.get(1)).setValue(str);
            this.adapter.notifyDataSetChanged();
            //this.user.setNickname(str);
            L4M.SaveUserName(str);
        }
        if (requestCode == 2 && resultCode == 2) {
            ((UserInfoBean) this.mList.get(2)).setValue(str);
            this.adapter.notifyDataSetChanged();
            //this.user.setUsername(str);
            L4M.SaveUserName(str);
        }
        if (requestCode == SELECT_PICTURE) {
            handle(resultCode, data);
            saveBitmap("head.png", bitmap);
            adapter.notifyDataSetChanged();
        } else if (requestCode == SELECT_CAMER) {
            if (data.getData() == null) {
                bitmap = (Bitmap) data.getExtras().get("data");
                saveBitmap("head.png", bitmap);
                adapter.notifyDataSetChanged();
            } else try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                if (bitmap != bitmap) {//主要是防止handle处理出错，就会将先前获取相册的照片show出来
                    saveBitmap("head.png", bitmap);
                    adapter.notifyDataSetChanged();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /** 保存方法 */
    public void saveBitmap(String picName, Bitmap bm) {
        Log.e(TAG, "保存图片");
        File f = new File("/sdcard/namecard/", picName);
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
            Log.i(TAG, "已经保存");
            CacheUtils.setString(this, "imagePath", f.getAbsolutePath());
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 数据处理 共同点提取
     *
     * @param resultCode
     * @param data
     */
    private void handle(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {//结果代码是Ok的
            Uri uri = data.getData();
            if (uri != null && data.getData() != null) {
                Log.i(TAG, "uri 和 data.getData()不为空");
                ContentResolver contentResolver = this.getContentResolver();
                if (bitmap != null) {
                    bitmap.recycle();
                }
                try {
                    bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri));//出错
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                Log.i(TAG, "uri为空或者data为空   " + "数据：" + data.getData() + "  uri: " + uri);
            }
        }
    }

    protected void onResume() {
        super.onResume();
        String str = SpUtils.getString((Context) this, "UserHead");
        if (str != null && str.length() > 0) {
            ((UserInfoBean) this.mList.get(0)).setValue(str);
            this.adapter.notifyDataSetChanged();
        }
    }
}
