package com.chujiu.manager.httpclient;

import com.alibaba.fastjson.JSON;
import com.chujiu.json.AccessToken;
import com.chujiu.json.TemplateMsg;
import com.chujiu.manager.quartz.controller.RefreshAccessTokenTask;
import com.chujiu.model.WeiXinContext;
import com.chujiu.model.WeiXinFinalValue;
import com.chujiu.model.WeiXinMenu;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tianci on 2017/5/25.
 */
public class HttpClient {

    private static String accessToken;

    @Before
    public void initAccessToken() {
        RefreshAccessTokenTask rat = new RefreshAccessTokenTask();
        rat.refreshToken();
        this.accessToken = rat.getAccessToken();
    }

    /**
     * 微信获取access_token测试
     */
    @Test
    public void testHttpClient() {
        try {
            CloseableHttpClient client = HttpClients.createDefault();
            String url = WeiXinFinalValue.ACCESS_TOKEN_URL;
            url = url.replaceAll("APPID", WeiXinFinalValue.APPID);
            url = url.replaceAll("APPSECRET", WeiXinFinalValue.APPSECRET);
            HttpGet get = new HttpGet(url);
            CloseableHttpResponse resp = client.execute(get);
            int statusCode = resp.getStatusLine().getStatusCode();
            if (statusCode >= 200 && statusCode<300) {
                HttpEntity entity = resp.getEntity();
                String content = EntityUtils.toString(entity);
                AccessToken at = JSON.parseObject(content, AccessToken.class);
                System.out.println(at.getAccess_token()+"****"+at.getExpires_in());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testMenu() {
        try {
            List<WeiXinMenu> wms = new ArrayList<>();
            WeiXinMenu wm1 = new WeiXinMenu();
            wm1.setId(1);
            wm1.setName("首页");
            wm1.setType("view");
            wm1.setUrl("http://kingtes88.55555.io/SingleSystem/index.html");
            wms.add(wm1);

            WeiXinMenu wm2 = new WeiXinMenu();
            wm2.setName("测试资源");
            List<WeiXinMenu> wms_sub = new ArrayList<>();
            WeiXinMenu wmSub = new WeiXinMenu();
            wmSub.setName("诗词歌赋");
            wmSub.setType("click");
            wmSub.setKey("A0001");
            wms_sub.add(wmSub);


            wmSub = new WeiXinMenu();
            wmSub.setName("扫描测试");
            wmSub.setType("scancode_waitmsg");
            wmSub.setKey("A0002");
            wms_sub.add(wmSub);

            wm2.setSub_button(wms_sub);
            wms.add(wm2);

            Map<String, List<WeiXinMenu>> map = new HashMap<>();
            map.put("button", wms);
            String jsonStr = JSON.toJSONString(map);

            CloseableHttpClient client = HttpClients.createDefault();
            String url = WeiXinFinalValue.MENU_ADD;



            url = url.replaceAll("ACCESS_TOKEN", accessToken);
            HttpPost post = new HttpPost(url);
            post.addHeader("Content-Type", "application/json");
            StringEntity entity = new StringEntity(jsonStr, ContentType.create("application/json", "utf-8"));
            post.setEntity(entity);
            CloseableHttpResponse resp = client.execute(post);
            int sc = resp.getStatusLine().getStatusCode();
            if (sc >= 200 && sc < 300) {
                System.out.println(EntityUtils.toString(resp.getEntity()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public static String postTemplateMsg(TemplateMsg tm) {
        CloseableHttpResponse resp = null;
        CloseableHttpClient client = null;

        String url = WeiXinFinalValue.SEND_TEMPLATE_MSG;
        url.replace("ACCESS_TOKEN",accessToken);
        return null;
    }
}
