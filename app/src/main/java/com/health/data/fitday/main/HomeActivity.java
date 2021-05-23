package com.health.data.fitday.main;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.health.data.fitday.MyApplication;
import com.health.data.fitday.main.widget.AlphaTabsIndicator;
import com.health.data.fitday.utils.ToastUtil;
import com.sinophy.smartbracelet.R;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity {
    private static final int REQUEST_CODE_OPEN_GPS = 1;

    private static final int REQUEST_CODE_PERMISSION_LOCATION = 2;

    private AlphaTabsIndicator alphaTabsIndicator;

    public BlueToothManager bleManager;

    private long exitTime = 0L;

    protected int getLayoutId() {
        return R.layout.activity_home_layout;
    }

    protected void initData() {
        this.bleManager = new BlueToothManager((Application)MyApplication.getInstance(), (Context)this);
    }

    protected void initView() {
        ViewPager viewPager = (ViewPager)findViewById(R.id.mViewPager);
        HomeAdapter homeAdapter = new HomeAdapter(getSupportFragmentManager());
        viewPager.setAdapter((PagerAdapter)homeAdapter);
        viewPager.addOnPageChangeListener(homeAdapter);
        AlphaTabsIndicator alphaTabsIndicator = (AlphaTabsIndicator)findViewById(R.id.alphaIndicator);
        this.alphaTabsIndicator = alphaTabsIndicator;
        alphaTabsIndicator.setViewPager(viewPager);
    }

    protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
        super.onActivityResult(paramInt1, paramInt2, paramIntent);
        if (paramInt1 == 1 && this.bleManager.checkGPSIsOpen()) {
            this.bleManager.setScanRule();
            this.bleManager.startScan();
        }
    }

    public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent) {
        if (paramInt == 4 && paramKeyEvent.getAction() == 0) {
            if (System.currentTimeMillis() - this.exitTime > 2000L) {
                ToastUtil.showToast("双击退出应用");
                this.exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(paramInt, paramKeyEvent);
    }

    public final void onRequestPermissionsResult(int paramInt, String[] paramArrayOfString, int[] paramArrayOfint) {
        super.onRequestPermissionsResult(paramInt, paramArrayOfString, paramArrayOfint);
        if (paramInt == 2 && paramArrayOfint.length > 0)
            for (paramInt = 0; paramInt < paramArrayOfint.length; paramInt++) {
                if (paramArrayOfint[paramInt] == 0)
                    this.bleManager.onPermissionGranted(paramArrayOfString[paramInt]);
            }
    }

    public void startSearchingDevice() {
        this.bleManager.checkPermissions();
    }

    private class HomeAdapter extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener {
        private List<Fragment> fragments;

        private String[] titles;

        public HomeAdapter(FragmentManager param1FragmentManager) {
            super(param1FragmentManager);
            ArrayList<Fragment> arrayList = new ArrayList();
            this.fragments = arrayList;
            String[] arrayOfString = new String[4];
            arrayOfString[0] = "健康";
            arrayOfString[1] = "运动";
            arrayOfString[2] = "设备";
            arrayOfString[3] = "我的";
            this.titles = arrayOfString;
            arrayList.add(HealthFragment.newInstance(arrayOfString[0]));
            this.fragments.add(SportFragment.newInstance(this.titles[1]));
            this.fragments.add(DeviceFragment.newInstance(this.titles[2]));
            this.fragments.add(MineFragment.newInstance(this.titles[3]));
        }

        public int getCount() {
            return this.fragments.size();
        }

        public Fragment getItem(int param1Int) {
            return this.fragments.get(param1Int);
        }

        public void onPageScrollStateChanged(int param1Int) {}

        public void onPageScrolled(int param1Int1, float param1Float, int param1Int2) {}

        public void onPageSelected(int param1Int) {
            if (param1Int != 0 && 2 == param1Int);
        }
    }
}
