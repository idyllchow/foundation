package com.sponia.foundation.net;

import java.util.UUID;

/**
 * com.sponia.stats.net
 * 网络请求消息类型
 * 15/9/6
 * shibo
 */
public class NetMessage {

    protected String msgId;
    
    protected boolean isCancelMsg = false;
    /**
     * id对应bean属性,String,list,map
     */
    protected String idProperty;

    private String token;

    public NetMessage(String msgId, String idProperty, boolean isCancelMsg) {
        this.msgId = msgId;
        this.idProperty = idProperty;
        this.isCancelMsg = isCancelMsg;
    }
    
    public NetMessage(String msgId) {
        this.msgId = msgId;
    }
    
    public NetMessage () {
        msgId = UUID.randomUUID().toString().replaceAll("-", "");
        this.isCancelMsg = false;
    }
    
    /**
     * 返回是否被取消显示
     * @return
     */
    public boolean isCancelMsg() {
        return isCancelMsg;
    }
    
    /**
     * 获取消息id
     * @return
     */
    public String getMessageId() {
        return msgId;
    }

    /**
     * 获取消息id对应bean属性
     * @return
     */
    public String getMsgProperty() {
        return idProperty;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
