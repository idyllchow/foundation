package com.sponia.foundation.share;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;
import com.facebook.rebound.SpringUtil;
import com.sponia.foundation.R;
import com.sponia.foundation.share.action.ShareAction;

import java.util.Map;

/**
 * @author shibo
 * @packageName com.sponia.foundation.share
 * @description
 * @date 16/9/28
 */

public class ShareView extends AbsShareView {

    private Context context;
    private SharePopupWindow pw;
    private View shareView;
    private View shareContentView;
    private GridView gvShare;
    private TextView tvShareCancel;

    private int mMainRootHeight = 0;
    private Map<String, ShareAction> shareActionMap;

    public ShareView(Context context) {
        this.context = context;
        initView();
    }

    @Override
    public void show() {
        if (pw != null) {
            pw.show();
        }
    }

    @Override
    public void dismiss() {
        if (pw != null && pw.isShowing()) {
            pw.dismiss();
        }
    }

    @Override
    public void onClick(View v) {

    }

    private void initView() {
        shareView = LayoutInflater.from(context).inflate(R.layout.share_view, null, false);
        shareContentView = shareView.findViewById(R.id.lly_share_content);
        gvShare = (GridView) shareView.findViewById(R.id.gv_share_platform);
        tvShareCancel = (TextView) shareView.findViewById(R.id.tv_share_cancel);

        shareActionMap = ShareUtil.getShareActionMap();
        gvShare.setAdapter(new ShareAdapter());

        pw = new SharePopupWindow(context);
        pw.setContentView(shareView);
    }

    class ShareAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return shareActionMap == null ? 0 : shareActionMap.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_share_view, null, false);
                holder = new ViewHolder();
                holder.imgShare = (ImageView) convertView.findViewById(R.id.img_share_item);
                holder.tvShare = (TextView) convertView.findViewById(R.id.tv_share_item);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            if (shareActionMap.keySet().iterator().hasNext()) {
                holder.tvShare.setText(shareActionMap.keySet().iterator().next());
                holder.imgShare.setImageDrawable(shareActionMap.get(shareActionMap.keySet().iterator().next()).getShareIcon());
            }


            return convertView;
        }

        class ViewHolder {
            ImageView imgShare;
            TextView tvShare;
        }
    }

    class SharePopupWindow extends PopupWindow {

        public SharePopupWindow(Context context) {
            super(context);
        }

        public void show() {
            if (isShowing()) {
                this.showAtLocation(shareView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                this.update();
                SpringSystem springSystem = SpringSystem.create();
                springSystem.createSpring().setEndValue(1).setSpringConfig(SpringConfig.fromBouncinessAndSpeed(6, 10)).addListener(new SimpleSpringListener() {
                    @Override
                    public void onSpringUpdate(Spring spring) {
                        float progress = (float) spring.getCurrentValue();
                        shareContentView.setTranslationY((float) SpringUtil.mapValueFromRangeToRange(progress, 0, 1, mMainRootHeight / 2, 0));
                        shareContentView.setScaleY((float) SpringUtil.mapValueFromRangeToRange(progress, 0, 1, 0.5, 1.0));
                        shareContentView.setAlpha(progress);
                        shareContentView.setAlpha(progress);
                    }
                });
            }
        }
    }
}
