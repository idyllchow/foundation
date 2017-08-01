package com.sponia.foundation.share.action;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;

import com.sponia.foundation.R;
import com.sponia.foundation.common.Common;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXImageObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * @author shibo
 * @packageName com.sponia.foundation.share.action
 * @description
 * @date 16/9/29
 */

public class WXShareAction extends ShareAction {

    private static final int TIMELINE_SUPPORTED_VERSION = 0x21020001;
    private static final int MAX_DECODE_PICTURE_SIZE = 1920 * 1440;
    private IWXAPI mAPI;
    private boolean isPengYouQuan;
    /**
     * 缩略图大小
     */
    private int thumbNailSize = 80;

    public WXShareAction(/*Context context, */String appId) {
        mAPI = WXAPIFactory.createWXAPI(Common.application, appId, true);
        mAPI.registerApp(appId);
    }

    @Override
    protected void setShareIcon() {
        shareIcon = Common.application.getResources().getDrawable(R.drawable.met_ic_clear);
    }

    public boolean isWXAppSupportSession() {
        return mAPI.isWXAppInstalled() && mAPI.isWXAppSupportAPI();
    }

    public boolean isWXAppSupportTimeline() {
        return mAPI.isWXAppInstalled() && mAPI.isWXAppSupportAPI()
                && mAPI.getWXAppSupportAPI() >= TIMELINE_SUPPORTED_VERSION;
    }

    @Override
    protected boolean doShare() {
        if (shareData == null) {
            return false;
        }
        if (shareData.getPicUri() == null) { //没有图片
            if (shareData.getLink() == null || shareData.getLink().trim().length() == 0) { //纯文字
                shareText(shareData.getTitle(), shareData.getContent(), isPengYouQuan);
            } else { //文字加链接
                shareHypeLink(shareData.getTitle(), shareData.getContent(), null, shareData.getLink(), thumbNailSize, isPengYouQuan);
            }
        } else { //有图片
            if (!TextUtils.isEmpty(shareData.getLink())) { //链接不为空
                shareHypeLink(shareData.getTitle(), shareData.getContent(), shareData.getPicUri().toString().substring(7), shareData.getLink(), thumbNailSize, isPengYouQuan);
            } else { //链接为空
                sharePicture(shareData.getTitle(), shareData.getContent(), shareData.getPicUri().toString().substring(7), shareData.getPicture(), thumbNailSize, isPengYouQuan);
            }
        }
        return false;
    }

    public void setPengYouQuan(boolean pengYouQuan) {
        isPengYouQuan = pengYouQuan;
    }

    /**
     * 无论是对话，还是朋友圈中，都只显示text字段，如果text中有链接，链接在对话和朋友圈中都可点击。
     *
     * @param title      这个字段传null就好，没有目前微信的SDK没有用到。
     * @param text       要分享的文本。
     * @param isTimeline 分享至微信聊天，还是朋友圈。true:分享至朋友圈;false:分享至微信聊天
     */
    public void shareText(String title, String text, boolean isTimeline) {

        Log.e("Share:", "分享文本到微信！");
        // 初始化一个WXTextObject对象
        WXTextObject textObj = new WXTextObject();
        textObj.text = text;

        // 用WXTextObject对象初始化一个WXMediaMessage对象
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObj;
        // 发送文本类型的消息时，title字段不起作用
        // msg.title = "Will be ignored";
        msg.title = title;
        msg.description = text;

        // 构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("text"); // transaction字段用于唯一标识一个请求
        req.message = msg;
        req.scene = isTimeline ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;

        // 调用api接口发送数据到微信
        mAPI.sendReq(req);
    }

    /**
     * 分享图片。在对话中，显示title，pic，description三个元素。在朋友圈中，显示title、pic两个元素。
     *
     * @param title
     * @param text       朋友圈中不显示。
     * @param picPath    缩略图的本地路径
     * @param isTimeline 分享至微信聊天，还是朋友圈。true:分享至朋友圈;false:分享至微信聊天
     */
    public void sharePicture(String title, String text, String picPath, byte[] imageData, int thumbNailSize, boolean isTimeline) {
        WXImageObject imageObject = new WXImageObject();
        if (imageData != null) {
            imageObject.imageData = imageData;
        } else {
            imageObject.imagePath = picPath;
        }
        WXMediaMessage msg = new WXMediaMessage(imageObject);
        msg.title = title;
        msg.description = text;
        if (picPath != null) {
            Log.e("Share PicPath:", picPath);
            Bitmap thumb = extractThumbNail(picPath, thumbNailSize, thumbNailSize, true);
            if (thumb != null) {
                Log.e("Share:", "Picture is null");
            }
            msg.setThumbImage(thumb);
        }
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("image");
        req.message = msg;
        req.scene = isTimeline ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
        mAPI.sendReq(req);
    }

    /**
     * 分享链接。在对话中，显示title，pic，description三个元素。在朋友圈中，显示title、pic两个元素。
     *
     * @param title      标题
     * @param text       描述,朋友圈中不显示。
     * @param picPath    缩略图的本地路径
     * @param url        链接的地址
     * @param isTimeline 分享至微信聊天，还是朋友圈。true:分享至朋友圈;false:分享至微信聊天
     */
    public void shareHypeLink(String title, String text, String picPath, String url, int thumbNailSize, boolean isTimeline) {
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = url;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = title;
        msg.description = text;
        if (picPath != null) {
            Log.e("Share PicPath:", picPath);
            Bitmap thumb = extractThumbNail(picPath, thumbNailSize, thumbNailSize, true);
            if (thumb != null) {
                Log.e("Share:", "图片不为null");
            }
            msg.setThumbImage(thumb);
        }
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = isTimeline ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
        mAPI.sendReq(req);

    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    private Bitmap extractThumbNail(final String path, final int height, final int width, final boolean crop) {
        BitmapFactory.Options options = new BitmapFactory.Options();

        try {
            options.inJustDecodeBounds = true;
            Bitmap tmp = BitmapFactory.decodeFile(path, options);
            if (tmp != null) {
                tmp.recycle();
            }

            final double beY = options.outHeight * 1.0 / height;
            final double beX = options.outWidth * 1.0 / width;
            options.inSampleSize = (int) (crop ? (beY > beX ? beX : beY) : (beY < beX ? beX : beY));
            if (options.inSampleSize <= 1) {
                options.inSampleSize = 1;
            }

            // NOTE: out of memory error
            while (options.outHeight * options.outWidth / options.inSampleSize > MAX_DECODE_PICTURE_SIZE) {
                options.inSampleSize++;
            }

            int newHeight = height;
            int newWidth = width;
            if (crop) {
                if (beY > beX) {
                    newHeight = (int) (newWidth * 1.0 * options.outHeight / options.outWidth);
                } else {
                    newWidth = (int) (newHeight * 1.0 * options.outWidth / options.outHeight);
                }
            } else {
                if (beY < beX) {
                    newHeight = (int) (newWidth * 1.0 * options.outHeight / options.outWidth);
                } else {
                    newWidth = (int) (newHeight * 1.0 * options.outWidth / options.outHeight);
                }
            }

            options.inJustDecodeBounds = false;

            Bitmap bm = BitmapFactory.decodeFile(path, options);
            if (bm == null) {
                return null;
            }

            final Bitmap scale = Bitmap.createScaledBitmap(bm, newWidth, newHeight, true);
            if (scale != null) {
                bm = scale;
            }

            if (crop) {
                final Bitmap cropped = Bitmap.createBitmap(bm, (bm.getWidth() - width) >> 1,
                        (bm.getHeight() - height) >> 1, width, height);
                if (cropped == null) {
                    return bm;
                }

                bm.recycle();
                bm = cropped;
            }
            return bm;

        } catch (final OutOfMemoryError e) {

        }

        return null;
    }

}
