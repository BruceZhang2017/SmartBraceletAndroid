package com.health.data.fitday.device.model;

import com.health.data.fitday.global.RealmOperationHelper;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class DeviceManager {
    public List<BLEModel> models = new ArrayList<>();
    public BLEModel currentModel = null;

    /**
     * 内部类实现单例模式
     * 延迟加载，减少内存开销
     *
     * @author xuzhaohu
     *
     */
    private static class SingletonHolder {
        private static DeviceManager instance = new DeviceManager();
    }

    /**
     * 私有的构造函数
     */
    private DeviceManager() {
        initializeDevices();
    }

    public static DeviceManager getInstance() {
        return SingletonHolder.instance;
    }

    protected void method() {
        System.out.println("SingletonInner");
    }

    public void initializeDevices() {
        models.clear();
        RealmResults<BLEModel> results = (RealmResults<BLEModel>) RealmOperationHelper.getInstance(Realm.getDefaultInstance()).queryAll(BLEModel.class);
        for (BLEModel model : results) {
            models.add(model);
        }
    }
}
