package com.geocentric.foundation.net;


public interface IHttpListener {


	/**
	 * 网络链接超时
	 */
	 int ERROR_SOCKETTIMEOUTEXCEPTION = 0x0001;
	/**
	 * 无网络活动状态
	 */
	int ERROR_NONETWORKACTIVITYEXCEPTION = 0x0002;
	/**
	 * 服务端异常，httpRespCode非200
	 */
	int ERROR_HTTPSERVICEERROREXCEPTION = 0x0003;
	/**
	 * 网络链接异常
	 */
	int ERROR_IOEXCEPTION = 0x0004;
	/**
	 * SSL证书信任失败
	 */
	int ERROR_SSLVERIFYEXCEPTION = 0x0005;

	
}
