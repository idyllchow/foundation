package com.geocentric.foundation.common;

import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;

public class PreventContinuousClick implements OnClickListener {

    private OnClickListener onClickListener;
    private long delayMis = 1000;

    public PreventContinuousClick(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public PreventContinuousClick(OnClickListener onClickListener, long delayMis) {
        this.onClickListener = onClickListener;
        this.delayMis = delayMis;
    }

    private volatile boolean clicked = false;


    @Override
    public void onClick(final View v) {
        if (clicked) {

        } else {
            clicked = true;
            if (onClickListener != null) {
                v.setClickable(false);
                onClickListener.onClick(v);
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        clicked = false;
                        v.setClickable(true);
                    }
                }, delayMis);
            }
        }

    }

}
