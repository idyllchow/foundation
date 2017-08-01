package com.sponia.foundation.net;

import android.os.Build;

import com.sponia.foundation.BuildConfig;
import com.sponia.foundation.utils.LogUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * @author shibo
 * @packageName com.openplay.soccerplayer.network.api
 * @description
 * @date 16/9/2
 */
public class NetTask {

    private static final String OP_DEBUG_RANDOM = "X-OpenPlay-debug";
    private static final String OP_DEBUG_HASH = "X-OpenPlay-God";
    private static final String USER_AGENT = "Android_" + Build.MODEL + "_" + Build.VERSION.RELEASE + " OpenPlay_Football_" +
            BuildConfig.VERSION_NAME + " (" + BuildConfig.VERSION_CODE + ")";

    private static IRetrofitListener retrofitListener;

    public NetTask(IRetrofitListener retrofitListener) {
        this.retrofitListener = retrofitListener;
    }

    public static ApiService getDefault(final boolean needToken) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okClient = new OkHttpClient
                .Builder()
                .addInterceptor(logging.setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {

                        Request.Builder builder = chain.request().newBuilder()
                                .addHeader("User-Agent", USER_AGENT)
                                .url((chain.request().url().toString()));
                        Request request;
                        request = builder.build();
                        return chain.proceed(request);
                    }
                }).build();
        return new Retrofit.Builder()
//                .baseUrl(ApiConfig.HOST)
                .addConverterFactory(new FastJsonConvertFactory())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okClient)
                .build().create(ApiService.class);

    }

    public static ApiService getExternal(String baseUrl) {
        OkHttpClient okClient = new OkHttpClient
                .Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request;
                        request = chain.request().newBuilder().build();
                        LogUtil.defaultLog("request url: " + chain.request().url());
                        return chain.proceed(request);
                    }
                })
                .build();
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(new FastJsonConvertFactory())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okClient)
                .build().create(ApiService.class);

    }


}
