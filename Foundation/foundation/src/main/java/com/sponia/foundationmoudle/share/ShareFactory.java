package com.sponia.foundation.share;

import android.content.Context;

import com.sponia.foundation.share.action.ShareAction;
import com.sponia.foundation.share.action.WXShareAction;

/**
 * @author shibo
 * @packageName com.sponia.foundation.share
 * @description
 * @date 16/9/30
 */

public class ShareFactory {

    public static ShareAction addShareAction(/*Context context, */@ShareMedia.SharePlatform String platform, String appId) {
        switch (platform) {
            case ShareMedia.WEI_XIN:
                WXShareAction wxShareAction = new WXShareAction(/*context, */appId);
                return wxShareAction;
            case ShareMedia.WEI_XIN_PENG_YOU_QUAN:
                WXShareAction wxShareActionPengYouQuan = new WXShareAction(/*context, */appId);
                return wxShareActionPengYouQuan;
            default:
                return null;
        }
    }
}
