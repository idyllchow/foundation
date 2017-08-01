package com.sponia.foundation.net;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.sponia.foundation.bean.BaseBean;
import com.sponia.foundation.utils.CellphoneUtil;
import com.sponia.foundation.utils.CommUtil;
import com.sponia.foundation.utils.LogUtil;
import com.sponia.foundation.utils.SponiaEncrypt;
import com.sponia.foundation.utils.StringUtil;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * com.sponia.stats.net
 * 网络链接动作行为类
 * 15/9/6
 * shibo
 */
public class NetAction {

    private IHttpListener httpListener;

    private final OkHttpClient okClient = new OkHttpClient();

    private static final MediaType M_JSON = MediaType.parse("application/json; charset=utf-8");

    private static final UploadManager uploadManager = new UploadManager();
    //超时时间
    private static final int TIME_OUT = 15;
    private static final String USER_AGENT = "Android_" + CellphoneUtil.getBrand() + "_" + CellphoneUtil.getOsVersion() + " OpenPlay_Stats_" + CellphoneUtil.getChannelName() + "_" + CommUtil.getAppVersionName() + "." + CommUtil.getAppVersionCode();
    private static final String OP_DEBUG_RANDOM = "X-OpenPlay-debug";
    private static final String OP_DEBUG_HASH = "X-OpenPlay-God";

    /**
     * 取消网络请求
     *
     * @param netMsg
     */
    public void cancelAction(NetMessage netMsg) {
        if (null != netMsg) {
            netMsg.isCancelMsg = true;
        }
    }

    public NetAction(IHttpListener httpListener) {
        this.httpListener = httpListener;
    }


    /**
     * ok get request
     *
     * @param url   请求地址
     * @param netMsg 消息标志
     * @param token  token,不需要时传null或“”
     */
    public void executeGet(final String host, final String url, final NetMessage netMsg, String token, boolean hideErrorMsg) {
        okResponse(builder(host, url, token).build(), netMsg, hideErrorMsg);
    }

    /**
     * ok post request
     *
     * @param url   请求地址
     * @param params 参数
     * @param netMsg 消息标志
     * @param token  token
     */
    public void executePost(final String host , final String url, final String params, final NetMessage netMsg, String token, boolean hideErrorMsg) {
        okResponse(builder(host, url, token).post(RequestBody.create(M_JSON, params)).build(), netMsg, hideErrorMsg);
    }

    /**
     * ok put请求
     *
     * @param url   请求地址
     * @param params 参数
     * @param netMsg 消息标志
     * @param token  token
     */
    public void executePut(final String host, final String url, final String params, final NetMessage netMsg, String token, boolean hideErrorMsg) {
        okResponse(builder(host, url, token).put(RequestBody.create(M_JSON, params)).build(), netMsg, hideErrorMsg);
    }

    /**
     * ok delete请求
     *
     * @param url   请求地址
     * @param params 请求参数
     * @param netMsg 请求消息标志
     * @param token  token
     */
    public void executeDelete(final String host, final String url, final String params, final NetMessage netMsg, String token, boolean hideErrorMsg) {
        okResponse(builder(host, url, token).delete(RequestBody.create(M_JSON, params)).build(), netMsg, hideErrorMsg);
    }

    /**
     * 7牛上传图片
     *
     * @param file
     * @param key
     * @param token
     * @param handler
     */
    public void executeUploadPic(File file, String key, String token, UpCompletionHandler handler) {
        uploadManager.put(file, key, token, handler, null);
    }

    /**
     * 发起网络请求,得到响应(返回NetMessagem和json字符串)
     *
     * @param request
     * @param netMsg
     * @param hideErrorMsg 不展示错误信息
     */
    private void okResponse(Request request, final NetMessage netMsg, final boolean hideErrorMsg) {
        if (CellphoneUtil.checkNetWorkAvailable()) {
            okClient.setConnectTimeout(TIME_OUT, TimeUnit.SECONDS);
            okClient.setReadTimeout(TIME_OUT, TimeUnit.SECONDS);
            okClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    int errorCode = -1;
                    LogUtil.defaultLog(netMsg.getMessageId() + " onFailure");
                    e.printStackTrace();
                    httpListener.onHttpError(netMsg, errorCode, hideErrorMsg);
                }

                @Override
                public void onResponse(Response response) throws IOException {

                    String str = response.body().string();
                    BaseBean baseBean;
                    int code = response.code();
                    LogUtil.defaultLog(netMsg.getMessageId() + "----isSuccessful-----" + response.isSuccessful() + "; response code: " + code + "; respon str: " + str);
                    try {
                        if ((code == 200 || code == 201 || code == 204)) { //200,201-请求成功，或执行成功,204-删除成功
                            if (!"list".equalsIgnoreCase(netMsg.getMsgProperty())) {
                                baseBean = JSON.parseObject(str, BaseBean.class);
                                if (baseBean != null && baseBean.error_code != 0) {
                                    httpListener.onHttpError(netMsg, baseBean.error_code, hideErrorMsg);
                                } else {
                                    httpListener.onHttpSuccess(netMsg, str);
                                }
                            } else {
                                httpListener.onHttpSuccess(netMsg, str);
                            }
                        } else {
                            if (code != 404 && code != 500 && code != 501 && code != 502 && code != 503 && code != 403 && code != 405) { //404资源不存在,500,服务器异常, 403被劫持严重,故加上
                                baseBean = JSON.parseObject(str, BaseBean.class);
                                if (baseBean != null && baseBean.error_code != 0) {
                                    code = baseBean.error_code;
                                }
                            }
                            httpListener.onHttpError(netMsg, code, hideErrorMsg);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            httpListener.onHttpError(netMsg, IHttpListener.ERROR_NONETWORKACTIVITYEXCEPTION, hideErrorMsg);
        }
    }

    private Request.Builder builder(final String host, final String url, String token) {
        String subTime = StringUtil.substringAfterIndex(String.valueOf(System.nanoTime()), 8);
        String timePartOne = subTime.substring(0, 2);
        String timePartTwo = subTime.substring(2, subTime.length());

        Request.Builder builder = new Request.Builder()
                .header("User-Agent", USER_AGENT)
                .header(OP_DEBUG_HASH, SponiaEncrypt.getClientHash(url, timePartOne, timePartTwo))
                .header(OP_DEBUG_RANDOM, timePartOne + "." + timePartTwo + ";" + UUID.randomUUID())
                .url(host + url);
        return TextUtils.isEmpty(token) ? builder : builder.header("token", token);
    }

    Interceptor encryptInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            String subTime = StringUtil.substringAfterIndex(String.valueOf(System.nanoTime()), 8);
            String timePartOne = subTime.substring(0, 2);
            String timePartTwo = subTime.substring(2, subTime.length());
            LogUtil.defaultLog("request url: " + request.urlString());
            return chain.proceed(request.newBuilder().header("User-Agent", USER_AGENT)
                    .header(OP_DEBUG_HASH, SponiaEncrypt.getClientHash(request.urlString(), timePartOne, timePartTwo))
                    .header(OP_DEBUG_RANDOM, timePartOne + "." + timePartTwo + ";" + UUID.randomUUID()).build());
        }
    };

}
