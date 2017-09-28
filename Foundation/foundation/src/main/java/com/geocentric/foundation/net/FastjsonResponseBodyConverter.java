package com.geocentric.foundation.net;

import com.alibaba.fastjson.JSON;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.lang.reflect.Type;

import retrofit2.Converter;



public final class FastjsonResponseBodyConverter<T> implements Converter<ResponseBody,T> {

    private String charset;
    private final Type type;

    public FastjsonResponseBodyConverter(Type type) {
        this("UTF-8", type);
    }

    public FastjsonResponseBodyConverter(String charset, Type type) {
        this.charset = charset;
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        return JSON.parseObject(value.string(), type);
    }
}
