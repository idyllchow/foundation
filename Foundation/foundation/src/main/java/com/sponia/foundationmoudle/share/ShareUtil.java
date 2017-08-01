package com.sponia.foundation.share;

import android.content.Context;

import com.sponia.foundation.share.action.ShareAction;

import java.util.HashMap;
import java.util.Map;

/**
 * @author shibo
 * @packageName com.sponia.foundation.share
 * @description
 * @date 16/9/28
 */

public class ShareUtil {
    private Context context;
    private static Map<String, ShareAction> shareActionMap = new HashMap<>();

    public static void addSharePlatform(/*Context context, */@ShareMedia.SharePlatform String platform, String appId) {
        if (!shareActionMap.containsKey(platform)) {
            ShareAction shareAction = ShareFactory.addShareAction(/*context, */platform, appId);
            if(shareAction != null) {
                shareActionMap.put(platform, shareAction);
            }
        }
    }

    public static Map<String, ShareAction> getShareActionMap() {
        return shareActionMap;
    }
}
