package com.geocentric.foundation.net;

import com.geocentric.foundation.util.LogUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

public class NetTask {

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
                                .url((chain.request().url().toString()));
                        Request request;
                        request = builder.build();
                        return chain.proceed(request);
                    }
                }).build();
        return new Retrofit.Builder()
//                .baseUrl(ApiConfig.HOST)
                .addConverterFactory(new com.geocentric.foundation.net.FastJsonConvertFactory())
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
                .addConverterFactory(new com.geocentric.foundation.net.FastJsonConvertFactory())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okClient)
                .build().create(ApiService.class);

    }


}
