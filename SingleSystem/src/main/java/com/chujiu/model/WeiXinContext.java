package com.chujiu.model;

/**
 * Created by tianci on 2017/5/26.
 */
public class WeiXinContext {
    private static String accessToken;

    public static void setAccessToken(String accessToken) {
        WeiXinContext.accessToken = accessToken;
    }

    public static String getAccessToken() {
        return accessToken;
    }
}
