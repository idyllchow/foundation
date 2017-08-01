package com.sponia.foundation.net;

/**
 * @author shibo
 * @packageName com.openplay.soccerplayer.network
 * @description
 * @date 16/9/5
 */
public interface IRetrofitListener {

    void onRetrofitSuccess(NetMessage netMsg, String responseData);

    void onRetrofitError(NetMessage netMsg, int errCode, String errMsg);
}
