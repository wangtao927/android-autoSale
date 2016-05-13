package com.ys.ui.utils;

import java.text.DecimalFormat;

/**
 * Created by river on 2016/5/14.
 */
public class NumberUtils {
    /**
     * DecimalFormat转换最简便
     */
    public static String m2(double n) {
        DecimalFormat df = new DecimalFormat("##0.00");
        return df.format(n);
    }
}
