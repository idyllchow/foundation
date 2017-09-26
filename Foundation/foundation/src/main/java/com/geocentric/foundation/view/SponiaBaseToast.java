package com.geocentric.foundation.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.geocentric.foundation.utils.DensityUtil;
import com.geocentric.foundation.utils.DrawableUtil;
import com.geocentric.foundation.utils.LogUtil;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author shibo
 * @packageName com.geocentric.foundation.view
 * @description 自定义toast, 显示最后一条
 * @date 16/4/10
 */
public class SponiaBaseToast {

    private static TextView textView = null;
    private static CountDownTimer timer;
    private static Drawable drawable = null; // toast样式
    private static int num = 0; // 记数
    private static int tWidth;
    private static int tHeight;
    private static Queue<SponiaToast> toastQueue = new LinkedList<SponiaToast>();// 吐司队列

    /***
     * 显示在屏幕顶部的吐司框 （默认样式）
     *
     * @param ctx
     * @param msg  提示语
     * @param time 显示时间(单位：毫秒)
     */
    public static void showMsgInTop(Context ctx, String msg, int time) {
        setParametersInit();
        setViewInit(ctx, msg, Gravity.TOP, time);
    }

    /***
     * 显示在屏幕顶部的吐司框 （可定制长宽和背景样式）
     *
     * @param ctx
     * @param msg         提示语
     * @param time         显示时间(单位：毫秒)
     * @param width       宽度
     * @param height      高度
     * @param newDrawable 背景
     */
    public static void showMsgInTop(Context ctx, String msg, int time,
                                    int width, int height, Drawable newDrawable) {
        tWidth = width;
        tHeight = height;
        drawable = newDrawable;
        setViewInit(ctx, msg, Gravity.TOP, time);
    }

    /***
     * 显示在屏幕中部的吐司框（默认样式）
     *
     * @param ctx
     * @param msg 提示语
     * @param time 显示时间(单位：毫秒)
     */
    public static void showMsgInCenter(Context ctx, String msg, int time) {
        setParametersInit();
        setViewInit(ctx, msg, Gravity.CENTER, time);
    }

    /***
     * 显示在屏幕中部的吐司框 （可定制长宽和背景样式）
     *
     * @param ctx
     * @param msg         提示语
     * @param time         显示时间(单位：毫秒)
     * @param width       宽度
     * @param height      高度
     * @param newDrawable 背景
     */
    public static void showMsgInCenter(Context ctx, String msg, int time,
                                       int width, int height, Drawable newDrawable) {
        tWidth = width;
        tHeight = height;
        drawable = newDrawable;
        setViewInit(ctx, msg, Gravity.CENTER, time);
    }

    /***
     * 显示在屏幕底部的吐司框（默认样式）
     *
     * @param ctx
     * @param msg 提示语
     * @param time 显示时间(单位：毫秒)
     */
    public static void showMsgInBottom(final Context ctx, final String msg,
                                       final int time) {
        setParametersInit();
        setViewInit(ctx, msg, Gravity.BOTTOM, time);
    }

    /***
     * 显示在屏幕底部的吐司框 （可定制长宽和背景样式）
     *
     * @param ctx
     * @param msg         提示语
     * @param time         显示时间(单位：毫秒)
     * @param width       宽度
     * @param height      高度
     * @param newDrawable 背景
     */
    public static void showMsgInBottom(final Context ctx, final String msg,
                                       final int time, int width, int height, Drawable newDrawable) {
        tWidth = width;
        tHeight = height;
        drawable = newDrawable;
        setViewInit(ctx, msg, Gravity.BOTTOM, time);
    }

    /***
     * 设置默认的宽高和背景
     */
    private static void setParametersInit() {
        try {
            drawable = DrawableUtil.createRoundRectDrawable(0x90000000,
                    DensityUtil.dip2px(5));
//			tWidth = Common.getScreenWidth();
            tWidth = LayoutParams.WRAP_CONTENT;
            tHeight = LayoutParams.WRAP_CONTENT;
        } catch (Exception e) {
            LogUtil.defaultLog("SponiaBaseToast:Common.screenWidth为空");
            LogUtil.defaultLog(e);
        }
    }

    /***
     * 生成toast控件
     *
     * @param ctx
     * @param msg
     * @param gravity
     */
    private static synchronized void setViewInit(Context ctx, String msg, int gravity,
                                                 int time) {
        Toast toast = Toast.makeText(ctx, msg, Toast.LENGTH_LONG);

        LinearLayout linearLayout = new LinearLayout(ctx);
        linearLayout.setGravity(Gravity.CENTER);
        linearLayout.setPadding(DensityUtil.dip2px(10), DensityUtil.dip2px(10),
                DensityUtil.dip2px(10), DensityUtil.dip2px(10));
        LayoutParams lp = new LayoutParams(tWidth, tHeight);

        textView = new TextView(ctx);
        textView.setTextSize(16);
        textView.setTextColor(0xffffffff);
        textView.setBackgroundDrawable(drawable);
        textView.setPadding(DensityUtil.dip2px(20), DensityUtil.dip2px(10),
                DensityUtil.dip2px(20), DensityUtil.dip2px(10));
        textView.setGravity(Gravity.CENTER);

        linearLayout.addView(textView, lp);
        toast.setView(linearLayout);
        textView.setText(msg);
        toast.setGravity(gravity, 0, 0);

        SponiaToast geocentricToast = new SponiaToast(toast, time);
        toastQueue.offer(geocentricToast);

        startCountDownTimer();
    }

    /***
     * 启动倒计时控制吐司的显示和消失
     *
     */
    private static synchronized void startCountDownTimer() {
        // 取队列第一个toast;
        SponiaToast geocentricToast = toastQueue.peek();
        final Toast tempToast = geocentricToast.getToast();

        int time = geocentricToast.time;
        if (time == Toast.LENGTH_LONG) {
            time = 3000;
        } else if (time == Toast.LENGTH_SHORT) {
            time = 2500;
        }
        tempToast.show();
        num = 0;
        timer = new CountDownTimer(time, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                num = num + 1;
                if ((num % 2) != 0) {
                    tempToast.show();
                }
            }

            @Override
            public void onFinish() {
                closeToast(tempToast);
                closeTimer();
                // 删除队列第一个元素
                toastQueue.poll();
                if (toastQueue.size() != 0) {
                    // 继续显示队列里下一个toast
                    startCountDownTimer();
                }
            }
        };
        timer.start();
    }

    /***
     * 关闭吐司
     */
    private static void closeToast(Toast t) {
        if (null != t) {
            t.cancel();
            t = null;
        }
    }

    /***
     * 关闭计时器
     */
    private static void closeTimer() {
        if (null != timer) {
            timer.cancel();
            timer = null;
        }
    }

    static class SponiaToast {
        private Toast toast;
        private int time;

        public SponiaToast(Toast toast, int time) {
            this.toast = toast;
            this.time = time;
        }

        public Toast getToast() {
            return toast;
        }

        public void setToast(Toast toast) {
            this.toast = toast;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }
    }

}
