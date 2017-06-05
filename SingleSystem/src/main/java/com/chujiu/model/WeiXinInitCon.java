package com.chujiu.model;

/**
 * Created by Kingt on 2017/6/5.
 */
public class WeiXinInitCon {

    private String appId;
    private String appSecurt;
    private String baseUrl;
    private String token;
    private static WeiXinInitCon wxCon;

    private WeiXinInitCon(){}

    public static WeiXinInitCon getInstance() {
        if (wxCon == null) {
            wxCon = new WeiXinInitCon();
        }
        return wxCon;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecurt() {
        return appSecurt;
    }

    public void setAppSecurt(String appSecurt) {
        this.appSecurt = appSecurt;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
