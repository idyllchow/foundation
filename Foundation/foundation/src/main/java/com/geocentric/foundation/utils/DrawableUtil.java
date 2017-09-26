package com.geocentric.foundation.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.geocentric.foundation.BaseActivity;
import com.geocentric.foundation.common.Common;

import java.io.InputStream;

/**
 * @author shibo
 * @packageName com.geocentric.foundation.utils
 * @description 用于创建各种形状的Drawable
 * @date 16/4/10
 */
public class DrawableUtil {
    /**
     * 绘制圆角矩形
     *
     * @param color
     *            背景色
     * @param corner
     *            圆角尺寸
     * @return
     */
    public static final Drawable createRoundRectDrawable(final int color,
                                                         final float corner) {
        Drawable drawable = new Drawable() {
            Paint paint = new Paint();
            RectF rect;

            @Override
            public void setColorFilter(ColorFilter cf) {
            }

            @Override
            public void setAlpha(int alpha) {
            }

            @Override
            public int getOpacity() {
                return 0;
            }

            @Override
            public void setBounds(int left, int top, int right, int bottom) {
                super.setBounds(left, top, right, bottom);
                rect = new RectF(left, top, right, bottom);
            }

            @Override
            public void draw(Canvas canvas) {
                paint.setColor(color);
                paint.setAntiAlias(true);
                canvas.drawRoundRect(rect, corner, corner, paint);
            }
        };
        return drawable;
    }

    /**
     * 绘制矩形
     *
     * @param color
     *            背景色
     * @return
     */
    public static final Drawable createRectDrawable(final int color) {
        Drawable drawable = new Drawable() {
            Paint paint = new Paint();
            RectF rect;

            @Override
            public void setColorFilter(ColorFilter cf) {
            }

            @Override
            public void setAlpha(int alpha) {
            }

            @Override
            public int getOpacity() {
                return 0;
            }

            @Override
            public void setBounds(int left, int top, int right, int bottom) {
                super.setBounds(left, top, right, bottom);
                rect = new RectF(left, top, right, bottom);
            }

            @Override
            public void draw(Canvas canvas) {
                paint.setColor(color);
                paint.setAntiAlias(true);
                canvas.drawRect(rect, paint);
            }
        };
        return drawable;
    }

    /**
     * 绘制圆形
     *
     * @param color
     *            背景色
     * @return
     */
    public static final Drawable createCircleDrawable(final int color) {
        Drawable drawable = new Drawable() {
            Paint paint = new Paint();
            RectF rect;

            @Override
            public void setColorFilter(ColorFilter cf) {
            }

            @Override
            public void setAlpha(int alpha) {
            }

            @Override
            public int getOpacity() {
                return 0;
            }

            @Override
            public void setBounds(int left, int top, int right, int bottom) {
                super.setBounds(left, top, right, bottom);
                rect = new RectF(left, top, right, bottom);
            }

            @Override
            public void draw(Canvas canvas) {
                paint.setColor(color);
                paint.setAntiAlias(true);
                canvas.drawCircle(rect.centerX(), rect.centerY(),
                        Math.min(rect.width(), rect.height()) / 2, paint);
            }
        };
        return drawable;
    }

    /****
     * 获取drawable资源
     *
     * @param path
     * @return
     */
    public static Drawable getDrawableFromFile(String path) {
        try {
            Drawable drawable = null;
            InputStream inputStream = getFileStream(path);
            if (path.endsWith("9.png")) {
                TypedValue value = new TypedValue();
                value.density = TypedValue.DENSITY_DEFAULT;

                drawable = Drawable.createFromResourceStream(
                        Common.application.getResources(), value, inputStream,
                        path);
                if (drawable == null) {
                    drawable = NinePatchDrawable.createFromStream(
                            getFileStream(path), null);
                }
                // return
                // NinePatchDrawable.createFromStream(getFileStream(path),
                // null); // 这句话 可以替代上面的部分
            } else {
                Bitmap bm = BitmapFactory.decodeStream(inputStream);
                if (bm != null) {
                    bm.setDensity(DisplayMetrics.DENSITY_DEFAULT);

                    drawable = new BitmapDrawable(bm);
                    // drawable = new
                    // BitmapDrawable(ActivityController.mContext.getResources(),
                    // bm);
                }
                if (inputStream != null) {
                    inputStream.close();
                    inputStream = null;
                }
            }
            return drawable;
        } catch (Exception e) {
            return NinePatchDrawable
                    .createFromStream(getFileStream(path), null);
        }
    }

    public static InputStream getFileStream(String paramString) {
        return BaseActivity.class.getResourceAsStream(paramString);
    }
}
