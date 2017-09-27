package com.geocentric.foundation.net;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.geocentric.foundation.R;
import com.geocentric.foundation.utils.DeviceUtil;
import com.geocentric.foundation.utils.LogUtil;

import java.io.IOException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * @author shibo
 * @packageName com.sponia.openplayer.network.api
 * @description
 * @date 16/9/2
 */
public abstract class RxSubscribe<T extends BaseBean> extends Subscriber<T> {
    private Context mContext;

    public RxSubscribe(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onNext(T t) {
        if (t != null) {
            if (t.error_code == 0) {
                _onNext(t);
            } else if (!TextUtils.isEmpty(t.message)) {
                _onError(t.message);
            } else {
                _onError(getErrorMsg(t.error_code));
            }
        } else {
         //TODO
        }

    }

    @Override
    public void onError(Throwable e) {
        LogUtil.defaultLog(e);
        String errMsg = "";
        if(e instanceof HttpException){
            try {
                errMsg = JSON.parseObject(((HttpException) e).response().errorBody().string(), BaseBean.class).message;
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } else {
            errMsg = e.getMessage();
        }

    }

    protected abstract void _onNext(T t);

    protected void _onError(String message) {
        //网络判断
        if (!DeviceUtil.checkNetWorkAvailable()) {
            message = mContext.getResources().getString(R.string.network_error);
        }
        if (!TextUtils.isEmpty(message)) {
            // TODO: 2017/9/27 handle error
        }
    }

    private String getErrorMsg(int errorCode) {
        String errorStr;
        switch (errorCode) {
            case IHttpListener.ERROR_SOCKETTIMEOUTEXCEPTION:
                errorStr = getErrorClientTimeoutStr();
                break;
            case IHttpListener.ERROR_NONETWORKACTIVITYEXCEPTION:
                errorStr = getErrorNoNetWorkStr();
                break;
            case IHttpListener.ERROR_IOEXCEPTION:
                errorStr = getErrorCodeStr();
                break;
            case IHttpListener.ERROR_HTTPSERVICEERROREXCEPTION:
                errorStr = getErrorCodeStr();
                break;
            case IHttpListener.ERROR_SSLVERIFYEXCEPTION:
                errorStr = getErrorSSLStr();
                break;
            default:
                errorStr = getErrorUnKnown();
                break;
        }
        return errorStr;
    }

    private String getErrorNoNetWorkStr() {
        return mContext.getResources().getString(R.string.network_error);
    }

    private String getErrorClientTimeoutStr() {
        return mContext.getResources().getString(R.string.client_timeout);
    }

    private String getErrorCodeStr() {
        return mContext.getResources().getString(R.string.errorCode);
    }

    private String getErrorSSLStr() {
        return mContext.getResources().getString(R.string.sslVerifyFail);
    }

    private String getErrorUnKnown() {
        return mContext.getResources().getString(R.string.unknown);
    }
}
