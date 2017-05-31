package com.chujiu.model;

/**
 * Created by tianci on 2017/5/25.
 */
public class WeiXinFinalValue {
    public static String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    public static String APPID = "wx253a04de73e6b730";
    public static String APPSECRET = "b5c4976b922f976902b27bc6e77dd6d2";
    public static String MENU_ADD = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

    public static String MSG_TEXT_TYPE = "text";
    public static String MSG_IMAGE_TYPE = "image";
    public static String MSG_VOICE_TYPE = "voice";
    public static String MSG_VIDEO_TYPE = "video";
    public static String MSG_SHORTVIDEO_TYPE = "shortvideo";
    public static String MSG_LOCATION_TYPE = "location";
    public static String MSG_EVENT_TYPE = "event";

    /**
     * 模板消息post请求url
     */
    public static String SEND_TEMPLATE_MSG = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
}
