package com.sponia.foundation.net;

import com.sponia.foundation.utils.LogUtil;

/**
 * @author shibo
 * @packageName com.sponia.foundation.net
 * @description
 * @date 16/9/1
 */
public class NetException extends Exception{

    private static final long serialVersionUID = 1L;
    private String resultMsg;
    private int resultCode;

    public NetException(String resultMsg, int resultCode) {
        this.resultMsg = resultMsg;
        this.resultCode = resultCode;
        LogUtil.defaultLog("");
        switch (resultCode) {
        }
    }
}