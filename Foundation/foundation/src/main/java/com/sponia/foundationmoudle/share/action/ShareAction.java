package com.sponia.foundation.share.action;

import android.graphics.drawable.Drawable;
import android.util.Log;

import com.sponia.foundation.share.ShareData;

/**
 * @author shibo
 * @packageName com.sponia.foundation.share.action
 * @description
 * @date 16/9/29
 */

public abstract class ShareAction {

    protected ShareData shareData;
    protected Drawable shareIcon;

    protected abstract void setShareIcon();

    public Drawable getShareIcon() {
        return shareIcon;
    }

    protected abstract boolean doShare();

    public boolean share() {
        try {
            return doShare();
        } catch (Exception e) {
            Log.e("ShareAction", "doShare failed", e);
            return false;
        }
    }
}
