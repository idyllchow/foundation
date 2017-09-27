package com.geocentric.foundation.net;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.geocentric.foundation.BaseActivity;
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
        if (mContext instanceof BaseActivity) {
            ((BaseActivity) mContext).dismissLoading();
        }
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
        if (mContext instanceof BaseActivity) {
            ((BaseActivity) mContext).dismissLoading();
            _onError(errMsg);
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
            //服务端定义错误码
            case IHttpListener.ERROR_VERIFY_EXCEPTION:
                errorStr = mContext.getString(R.string.error_verify_exception);
                break;
            case IHttpListener.ERROR_JSON_PARSE_EXCEPTION:
                errorStr = mContext.getString(R.string.error_json_parse_exception);
                break;
            case IHttpListener.ERROR_REQUEST_VERIFY_EXCEPTION:
                errorStr = mContext.getString(R.string.error_request_verify_exception);
                break;
            case IHttpListener.ERROR_PERMISSION_EXCEPTION:
                errorStr = mContext.getString(R.string.error_permission_exception);
                break;
            case IHttpListener.ERROR_TOKEN_EXCEPTION:
                errorStr = mContext.getString(R.string.error_token_exception);
                break;
            case IHttpListener.ERROR_NO_ACCOUNT_EXCEPTION:
                errorStr = mContext.getString(R.string.error_no_account_exception);
                break;
            case IHttpListener.ERROR_PHONE_PWD_EXCEPTION:
                errorStr = mContext.getString(R.string.error_phone_pwd_exception);
                break;
            case IHttpListener.ERROR_PHONE_USED_EXCEPTION:
                errorStr = mContext.getString(R.string.error_phone_used_exception);
                break;
            case IHttpListener.ERROR_VERIFY_CODE_EXCEPTION:
                errorStr = mContext.getString(R.string.error_verify_code_exception);
                break;
            case IHttpListener.ERROR_INVITATION_CODE_EXCEPTION:
                errorStr = mContext.getString(R.string.error_invitation_code_exception);
                break;
            case IHttpListener.ERROR_NO_RESOUCE_EXCEPTION:
                errorStr = mContext.getString(R.string.error_no_resource_exception);
                break;
            case IHttpListener.ERROR_ACCOUNT_ACTIVATION_EXCEPTION:
                errorStr = mContext.getString(R.string.error_account_activation_exception);
                break;
            case IHttpListener.ERROR_SHIRT_NO_EXCEPTION:
                errorStr = mContext.getString(R.string.error_shirt_no_exception);
                break;
            case IHttpListener.ERROR_EMAIL_USED_EXCEPTION:
                errorStr = mContext.getString(R.string.error_email_used_exception);
                break;
            case IHttpListener.ERROR_NO_UPDATE_EXCEPTION:
                errorStr = mContext.getString(R.string.error_no_update_exception);
                break;
            case IHttpListener.ERROR_NO_JOIN_EXCEPTION:
                errorStr = mContext.getString(R.string.error_no_join_exception);
                break;
            //此部分返回码应该和http返回码对应,或者401统一是否返回bean
            case IHttpListener.ERROR_RESOURCE_NOT_FOUND_EXCEPTION:
                errorStr = mContext.getString(R.string.error_no_resource_exception);
                break;
            case IHttpListener.ERROR_SERVER_EXCEPTION:
                errorStr = mContext.getString(R.string.error_server_exception);
                break;
            case IHttpListener.ERROR_SERVER_ONE_EXCEPTION:
                errorStr = mContext.getString(R.string.error_server_exception);
                break;
            case IHttpListener.ERROR_SERVER_TWO_EXCEPTION:
                errorStr = mContext.getString(R.string.error_server_exception);
                break;
            case IHttpListener.ERROR_SERVER_THREE_EXCEPTION:
                errorStr = mContext.getString(R.string.error_server_exception);
                break;
            case IHttpListener.ERROR_NO_PERMISSION_EXCEPTION:
                errorStr = mContext.getString(R.string.error_permission_exception);
                break;
            case IHttpListener.ERROR_PARAMS_EXCEPTION:
                errorStr = mContext.getString(R.string.error_params_exception);
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
