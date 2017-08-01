package com.sponia.foundation.share;

import android.net.Uri;

/**
 * @author shibo
 * @packageName com.sponia.foundation.share
 * @description
 * @date 16/9/29
 */

public class ShareData {

    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String content;
    /**
     * 链接
     */
    private String link;
    /**
     * 图片
     */
    private byte[] picture;
    /**
     * 缩略图Uri
     */
    private Uri picUri;

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getLink() {
        return link;
    }

    public byte[] getPicture() {
        return picture;
    }

    public Uri getPicUri() {
        return picUri;
    }
}
