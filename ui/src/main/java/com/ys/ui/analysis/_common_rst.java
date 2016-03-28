package com.ys.ui.analysis;

import java.lang.reflect.Field;

/**
 * Created by thinkpad on 2016/2/1.
 */
public class _common_rst {
    private String[] rst = null;
    public String get(Enum<?> e){
        int off = e.ordinal();

        if(off<0||rst==null||rst.length<(off+1))return "";
        return this.rst[off];
    }
    public boolean setRst(String[] rst){
        if(rst==null){
            System.out.println(">>>>err,rst null");
            return false;
        }
        this.rst = rst;
        return true;
       /* int j = 0;
        Field[] fields = this.getClass().getDeclaredFields();

        if(fields==null){
            System.out.println(">>>>err,rst fiels null");
            return false;
        }
        if(rst==null || fields.length>rst.length){
            System.out.println(">>>>err,fields.length:"+fields.length+", rst.length:"+rst.length);
            return false;
        }
        for (int i = 0; i < fields.length; i++)
        {
            try {

                fields[i].set(this, rst[i]);
                System.out.println(">>>>["+i+"]"+fields[i].getName()+":" +rst[i]);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return true;*/
    }
}
