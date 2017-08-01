package com.sponia.foundation.share.action;

import android.support.annotation.NonNull;

import com.sponia.foundation.share.ShareMedia;

/**
 * @author shibo
 * @packageName com.sponia.foundation.share
 * @description
 * @date 16/9/28
 */

public interface ShareListener {

    void onSuccess(@NonNull ShareMedia shareMedia);

    void onError(@NonNull ShareMedia shareMedia, Throwable throwable);

    void onCancel(@NonNull ShareMedia shareMedia);
}
