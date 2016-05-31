package com.ys.ui.utils;

/**
 * Created by river on 2016/5/29.
 */
public class Utils {
    private static long lastClickTime;
    public synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if ( time - lastClickTime < 200) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}
