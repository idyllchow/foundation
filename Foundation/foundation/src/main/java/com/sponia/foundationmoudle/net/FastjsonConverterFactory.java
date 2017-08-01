//package com.sponia.foundation.net;
//
//import retrofit2.Converter;
//import com.squareup.okhttp.RequestBody;
//import com.squareup.okhttp.ResponseBody;
//
//import java.lang.annotation.Annotation;
//import java.lang.reflect.Type;
//
//
///**
// * @author shibo
// * @packageName com.sponia.foundation.net
// * @description
// * @date 16/5/20
// */
//public class FastjsonConverterFactory extends Converter.Factory{
//
//    public static FastjsonConverterFactory create() {
//        return new FastjsonConverterFactory();
//    }
//
//    public FastjsonConverterFactory() {
//    }
//
//    @Override
//    public Converter<ResponseBody, ?> fromResponseBody(Type type, Annotation[] annotations) {
//        return new FastjsonResponseBodyConverter<>(type);
//    }
//
//    @Override
//    public Converter<?, RequestBody> toRequestBody(Type type, Annotation[] annotations) {
//        return new FastjsonRequestBodyConverter<>();
//    }
//}
