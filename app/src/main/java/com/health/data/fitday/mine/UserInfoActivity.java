package com.health.data.fitday.mine;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

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
import com.health.data.fitday.device.UserBean;
import com.health.data.fitday.main.BaseActivity;
import com.health.data.fitday.utils.SpUtils;
import com.lljjcoder.style.citylist.CityListSelectActivity;
import com.lljjcoder.style.citylist.bean.CityInfoBean;
import com.sinophy.smartbracelet.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import per.goweii.actionbarex.common.ActionBarCommon;
import per.goweii.actionbarex.common.OnActionBarChildClickListener;

public class UserInfoActivity extends BaseActivity {
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

    UserBean user;

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
                UserInfoActivity.this.user.setHeight(Integer.parseInt(str));
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
                UserInfoActivity.this.user.setSex(param1Int1);
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
                UserInfoActivity.this.user.setWeight(Integer.parseInt(str));
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
                UserInfoActivity.this.user.setBirthday(str);
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
        SpUtils.putString((Context) this, "User", (new Gson()).toJson(this.user));
    }

    protected int getLayoutId() {
        return R.layout.activity_user_info;
    }

    protected void initData() {
        this.user = (UserBean) (new Gson()).fromJson(SpUtils.getString((Context) this, "User"), UserBean.class);
        UserInfoBean userInfoBean1 = new UserInfoBean();
        userInfoBean1.setKey("头像");
        this.mList.add(userInfoBean1);
        UserInfoBean userInfoBean2 = new UserInfoBean();
        userInfoBean2.setKey("昵称");
        String str1 = this.user.getNickname();
        String str2 = "未设置";
        if (str1 == null || this.user.getNickname().length() == 0) {
            str1 = "未设置";
        } else {
            str1 = this.user.getNickname();
        }
        userInfoBean2.setValue(str1);
        this.mList.add(userInfoBean2);
        userInfoBean2 = new UserInfoBean();
        userInfoBean2.setKey("姓名");
        if (this.user.getUsername() == null || this.user.getUsername().length() == 0) {
            str1 = "未设置";
        } else {
            str1 = this.user.getUsername();
        }
        userInfoBean2.setValue(str1);
        this.mList.add(userInfoBean2);
        userInfoBean2 = new UserInfoBean();
        userInfoBean2.setKey("性别");
        if (this.user.getSex() == 0) {
            str1 = "男";
        } else {
            str1 = "女";
        }
        userInfoBean2.setValue(str1);
        this.mList.add(userInfoBean2);
        userInfoBean2 = new UserInfoBean();
        userInfoBean2.setKey("出生年月");
        if (this.user.getBirthday() == null || this.user.getBirthday().length() == 0) {
            str1 = "未设置";
        } else {
            str1 = this.user.getBirthday();
        }
        userInfoBean2.setValue(str1);
        this.mList.add(userInfoBean2);
        userInfoBean2 = new UserInfoBean();
        userInfoBean2.setKey("身高");
        if (this.user.getHeight() == 0) {
            str1 = "未设置";
        } else {
            str1 = this.user.getHeight() + "CM";
        }
        userInfoBean2.setValue(str1);
        this.mList.add(userInfoBean2);
        userInfoBean2 = new UserInfoBean();
        userInfoBean2.setKey("体重");
        if (this.user.getWeight() == 0) {
            str1 = "未设置";
        } else {
            str1 = this.user.getWeight() + "KG";
        }
        userInfoBean2.setValue(str1);
        this.mList.add(userInfoBean2);
        userInfoBean2 = new UserInfoBean();
        userInfoBean2.setKey("地区");
        str1 = str2;
        if (this.user.getArea() != null)
            if (this.user.getArea().length() == 0) {
                str1 = str2;
            } else {
                str1 = this.user.getArea();
            }
        userInfoBean2.setValue(str1);
        this.mList.add(userInfoBean2);
        UserInfoAdapter userInfoAdapter = new UserInfoAdapter(this.mList, (Context) this);
        this.adapter = userInfoAdapter;
        this.listView.setAdapter((ListAdapter) userInfoAdapter);
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> param1AdapterView, View param1View, int param1Int, long param1Long) {
                switch (param1Int) {
                    case 7:
                        Intent intent = new Intent((Context) UserInfoActivity.this, CityListSelectActivity.class);
                        UserInfoActivity.this.startActivityForResult(intent, 50);
                        break;
                    case 6:
                        UserInfoActivity.this.weightOptions.show();
                        break;
                    case 5:
                        UserInfoActivity.this.heightOptions.show();
                        break;
                    case 4:
                        UserInfoActivity.this.pvTime.show();
                        break;
                    case 3:
                        UserInfoActivity.this.pvOptions.show();
                        break;
                    case 2:
                        Intent intent1 = new Intent((Context) UserInfoActivity.this, EditContentActivity.class);
                        intent1.putExtra("title", "姓名");
                        intent1.putExtra("value", ((UserInfoBean) UserInfoActivity.this.mList.get(2)).getValue());
                        UserInfoActivity.this.startActivityForResult(intent1, 2);
                        break;
                    case 1:
                        Intent intent2 = new Intent((Context) UserInfoActivity.this, EditContentActivity.class);
                        intent2.putExtra("title", "昵称");
                        intent2.putExtra("value", ((UserInfoBean) UserInfoActivity.this.mList.get(1)).getValue());
                        UserInfoActivity.this.startActivityForResult(intent2, 1);
                        break;
                    case 0:
                        break;
                }
                Intent intent = new Intent((Context) UserInfoActivity.this, EditHeadActivity.class);
                UserInfoActivity.this.startActivity(intent);
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
                System.out.println("点击事件");
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

    protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
        CityInfoBean cityInfoBean;
        super.onActivityResult(paramInt1, paramInt2, paramIntent);
        if (paramInt1 == 50) {
            if (paramInt2 == -1) {
                if (paramIntent == null)
                    return;
                cityInfoBean = (CityInfoBean) paramIntent.getExtras().getParcelable("cityinfo");
                if (cityInfoBean == null)
                    return;
                ((UserInfoBean) this.mList.get(7)).setValue(cityInfoBean.getName());
                this.adapter.notifyDataSetChanged();
                this.user.setArea(cityInfoBean.getName());
            }
            return;
        }
        String str = paramIntent.getStringExtra("result");
        if (paramInt1 == 1 && paramInt2 == 2) {
            ((UserInfoBean) this.mList.get(1)).setValue(str);
            this.adapter.notifyDataSetChanged();
            this.user.setNickname(str);
        }
        if (paramInt1 == 2 && paramInt2 == 2) {
            ((UserInfoBean) this.mList.get(2)).setValue(str);
            this.adapter.notifyDataSetChanged();
            this.user.setUsername(str);
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
