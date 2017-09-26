package com.geocentric.foundation.net;

import com.geocentric.foundation.utils.LogUtil;

/**
 * @author shibo
 * @packageName com.geocentric.foundation.net
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