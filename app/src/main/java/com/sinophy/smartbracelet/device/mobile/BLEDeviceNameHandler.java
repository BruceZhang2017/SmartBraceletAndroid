package com.sinophy.smartbracelet.device.mobile;

public class BLEDeviceNameHandler {
    public int handleName(String name) {
        if (name.length() > 0) {
            if (name.equals("M3")) {
                return 10;
            }
            if (name.equals("M4")) {
                return 10;
            }
            if (name.equals("M5")) {
                return 10;
            }
            if (name.equals("M6")) {
                return 10;
            }
            if (name.equals("B28")) {
                return 12;
            }
            if (name.equals("B33")) {
                return 11;
            }
            if (name.equals("B35")) {
                return 11;
            }
            if (name.equals("B37")) {
                return 11;
            }
            if (name.equals("B38")) {
                return 11;
            }
            if (name.equals("TB1")) {
                return 12;
            }
            if (name.equals("TB1S")) {
                return 12;
            }
            if (name.equals("TB1S-T")) {
                return 12;
            }
            if (name.equals("TB3") || name.toLowerCase().equals("itime")) {
                return 12;
            }
            if (name.equals("TB3S")) {
                return 12;
            }
            if (name.equals("TB3S-T")) {
                return 12;
            }
            if (name.equals("TB12")) {
                return 22;
            }
            if (name .equals("TB15")) {
                return 22;
            }
            if (name .equals("TB8B")) {
                return 22;
            }
            if (name .equals("TB14B")) { // 240 * 280
                return 23;
            }
            if (name .equals("TB7B")) {
                return 22;
            }
            if (name .equals("TB19B")) {
                return 22;
            }
            if (name .equals("TB20B")) {
                return 22;
            }
        }

        return 0;
    }
}
