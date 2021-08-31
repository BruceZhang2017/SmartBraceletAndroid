package com.sinophy.smartbracelet.device.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class BLEModel extends RealmObject {
    /// 增加是否绑定字段，方便开发者自行设置绑定，自行连接使用
    boolean isBond;

    /// 苹果系统里设备的唯一标识符
    String uuidString;

    /// 设备名
    String name;

    /// 广播名
    String localName;

    /// 搜索的设备广播强度，可用来大致判断距离
    int rssi;

    @PrimaryKey
    String mac;

    /// 硬件版本
    String hardwareVersion;

    /// 固件版本
    String firmwareVersion;

    /// 厂商数据 ascii字符串，用于产商识别
    String vendorNumberASCII;

    /// 厂商数据 16进制字符串，用于本公司固件升级服务器请求使用
    String vendorNumberString;

    /// 内部型号
    String internalNumber;

    /// ascii转出的string
    String internalNumberString;

    String imageName;

    public boolean isBond() {
        return isBond;
    }

    public void setBond(boolean bond) {
        isBond = bond;
    }

    public String getUuidString() {
        return uuidString;
    }

    public void setUuidString(String uuidString) {
        this.uuidString = uuidString;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

    public int getRssi() {
        return rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getHardwareVersion() {
        return hardwareVersion;
    }

    public void setHardwareVersion(String hardwareVersion) {
        this.hardwareVersion = hardwareVersion;
    }

    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }

    public String getVendorNumberASCII() {
        return vendorNumberASCII;
    }

    public void setVendorNumberASCII(String vendorNumberASCII) {
        this.vendorNumberASCII = vendorNumberASCII;
    }

    public String getVendorNumberString() {
        return vendorNumberString;
    }

    public void setVendorNumberString(String vendorNumberString) {
        this.vendorNumberString = vendorNumberString;
    }

    public String getInternalNumber() {
        return internalNumber;
    }

    public void setInternalNumber(String internalNumber) {
        this.internalNumber = internalNumber;
    }

    public String getInternalNumberString() {
        return internalNumberString;
    }

    public void setInternalNumberString(String internalNumberString) {
        this.internalNumberString = internalNumberString;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
