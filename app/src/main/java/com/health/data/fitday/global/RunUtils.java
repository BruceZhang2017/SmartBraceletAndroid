package com.health.data.fitday.global;

/**
 * Created by zhou on 2017/12/5 12:33 .
 */

public class RunUtils {
    // 步数转公里
    public static float getDistanceByStep(long steps) {
        return steps * 0.6f / 1000;
    }

    // 千卡路里计算公式
    //String.format("%.1f", steps * 0.6f * 60 * 1.036f / 1000);
    public static float getCalorieByStep(long steps) {
        return steps * 0.6f * 60 * 1.036f / 1000;
    }

    // 千
    // 米转千卡路里
    public static double getCalorieByDistance(double distance) {
        return  distance * 60 * 1.036f;
    }
}



