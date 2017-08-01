package com.sponia.foundation.utils;

import android.util.DisplayMetrics;
import android.view.View;

import com.sponia.foundation.common.Common;

/**
 * @author shibo
 * @packageName com.sponia.foundation.utils
 * @description
 * @date 2017/8/1
 */

public class ViewUtil {

    public void getSharedViewWidthAndHeight(View view) {
        int mWidthPixels = 0;
        int mHeightPixels = 0;
        if (mWidthPixels == 0 || mHeightPixels == 0) {
            DisplayMetrics displayMetrics = Common.application.getResources().getDisplayMetrics();
            mWidthPixels = displayMetrics.widthPixels;
            mHeightPixels = displayMetrics.heightPixels;
        }
        //测量布局的宽高
        view.measure(View.MeasureSpec.makeMeasureSpec(mWidthPixels, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(mHeightPixels, View.MeasureSpec.UNSPECIFIED));
        //设置布局的尺寸和位置
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
    }
}
