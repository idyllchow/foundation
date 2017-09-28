package com.geocentric.foundation.utils;

import android.util.DisplayMetrics;
import android.view.View;

import com.geocentric.foundation.common.Common;


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
