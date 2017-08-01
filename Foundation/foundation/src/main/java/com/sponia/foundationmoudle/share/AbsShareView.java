package com.sponia.foundation.share;

import android.view.View;

import com.sponia.foundation.share.action.ShareListener;

/**
 * @author shibo
 * @packageName com.sponia.foundation.share
 * @description
 * @date 16/9/29
 */

public abstract class AbsShareView implements View.OnClickListener{
    protected ShareListener shareListener;

    /**
     * 显示ShareView
     */
    public abstract void show();

    /**
     * 隐藏ShareView
     */
    public abstract void dismiss();

    public void setShareListener(ShareListener listener) {
        shareListener = listener;
    }
}
