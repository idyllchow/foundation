package com.sponia.foundation.share.action;

import com.sponia.foundation.R;
import com.sponia.foundation.common.Common;

/**
 * @author shibo
 * @packageName com.sponia.foundation.share.action
 * @description
 * @date 16/9/29
 */

public class SinaShareAction extends ShareAction {


    @Override
    protected void setShareIcon() {
        shareIcon = Common.application.getResources().getDrawable(R.drawable.met_ic_clear);
    }

    @Override
    protected boolean doShare() {
        if (shareData == null) {
            return false;
        }

        return false;
    }
}
