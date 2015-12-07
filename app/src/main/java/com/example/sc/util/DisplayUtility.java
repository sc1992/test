package com.example.sc.util;

import android.util.DisplayMetrics;

import com.example.sc.AppApplication;

/**
 * Created by sc on 15-10-12.
 */
public class DisplayUtility {
    static DisplayMetrics mMetrics= AppApplication.application.getResources().getDisplayMetrics();

    /**
     * 获取屏幕密度
     * @return
     */
    public static float getDestiny(){
        return mMetrics.density;
    }

    public static int dip2px(float dipValue){
        return (int)(dipValue*getDestiny()+0.5f);
    }

    public static int px2dp(float pxValue){
        return (int)(pxValue/getDestiny()+0.5f);
    }
}
