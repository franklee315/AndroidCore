package com.common.androidcore.net;

/**
 * @author lifan 创建于 2013年10月25日 下午4:08:53
 * @version Ver 1.0 2013年10月25日 改订
 */
public interface HttpResultListener {
    /**
     * 响应正常
     *
     * @param requestFlag 请求标识
     * @param response    响应报文
     */
    void onSuccess(int requestFlag, Object response);

    /**
     * 请求超时
     *
     * @param requestFlag 请求标识
     */
    void onTimeOut(int requestFlag);

    /**
     * 响应不正常
     *
     * @param requestFlag 请求标识
     * @param code        返回码
     */
    void onFailure(int requestFlag, int code);

}
