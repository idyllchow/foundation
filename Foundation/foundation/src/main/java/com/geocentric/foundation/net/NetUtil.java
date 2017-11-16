package com.geocentric.foundation.net;

import android.text.TextUtils;

import com.geocentric.foundation.util.DeviceUtils;
import com.geocentric.foundation.util.EncryptUtils;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class NetUtil {

    /**
     * 将map形式的url参数拼接成url中的参数形式
     *
     * @param params
     * @return
     */
    public static String handlerURLParams(HashMap<String, String> params) {
        if (params == null || params.size() == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (Entry<String, String> entry : params.entrySet()) {
            String value = entry.getValue();
            if (TextUtils.isEmpty(value)) {
                value = "";
            }
            sb.append(entry.getKey() + "=" + value + "&");
        }
        if (sb.length() > 0) {//删除最后一个&符号
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    /**
     * 根据当前的网络状态，获取代理
     *
     * @return
     */
    public static final Proxy getProxy() {
        Proxy proxy = null;
        switch (DeviceUtils.checkNetWorkStatus()) {
            case DeviceUtils.DIANXINWAP:
                proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.0.0.200", 80));
                break;
            case DeviceUtils.LIANTONGWAP:
            case DeviceUtils.YIDONGWAP:
                proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.0.0.172", 80));
                break;
            case DeviceUtils.WIFI:
                String proHost = android.net.Proxy.getDefaultHost();
                int proPort = android.net.Proxy.getDefaultPort();
                if (proHost != null && proPort != -1) {  //设置了代理
                    InetSocketAddress inetAddress = new InetSocketAddress(proHost, proPort);
                    proxy = new Proxy(Proxy.Type.HTTP, inetAddress);
                }
                break;
        }
        return proxy;
    }


    public static String signRequest(HashMap<String, String> params, String key) {
        if (params == null || params.size() == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();

        List<Entry<String,String>> list = new ArrayList<Entry<String,String>>(params.entrySet());
        Collections.sort(list,new Comparator<Entry<String,String>>() {
            //升序排序
            public int compare(Entry<String, String> o1,
                               Entry<String, String> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }

        });

        for(Map.Entry<String,String> mapping:list){
            String value = mapping.getValue();
            if (!TextUtils.isEmpty(value)) { //排除值为空的参数
                sb.append(mapping.getKey()+ "=" + value + "&");
            }
        }
        //最后添加key
        sb.append(key);

        return EncryptUtils.md5(sb.toString());
    }
}
