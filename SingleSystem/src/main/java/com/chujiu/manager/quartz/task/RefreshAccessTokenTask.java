package com.chujiu.manager.quartz.task;

import com.alibaba.fastjson.JSON;
import com.chujiu.json.AccessToken;
import com.chujiu.json.ErrorEntity;
import com.chujiu.model.WeiXinContext;
import com.chujiu.model.WeiXinFinalValue;
import com.chujiu.model.WeiXinInitCon;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by Kingt on 2017/5/25.
 */
@Component
public class RefreshAccessTokenTask {
    private String accessToken;
    public void refreshToken() {

        HttpGet get = null;
        CloseableHttpClient client = null;
        CloseableHttpResponse resp = null;
        try {
            client = HttpClients.createDefault();
            String url = WeiXinFinalValue.ACCESS_TOKEN_URL;

            //微信基本信息
            WeiXinInitCon wxCon = WeiXinInitCon.getInstance();

            url = url.replaceAll("APPID",wxCon.getAppId());
            url = url.replaceAll("APPSECRET", wxCon.getAppSecurt());
            get = new HttpGet(url);
            resp = client.execute(get);
            int statusCode = resp.getStatusLine().getStatusCode();
            if (statusCode >= 200 && statusCode<300) {
                HttpEntity entity = resp.getEntity();
                String content = EntityUtils.toString(entity);
                try {
                    AccessToken at = JSON.parseObject(content, AccessToken.class);
                    this.accessToken = at.getAccess_token();
                    WeiXinContext.setAccessToken(at.getAccess_token());
                    System.out.println("#$##########################################################$");
                    System.out.println("获取access_token成功！！access_token:" + WeiXinContext.getAccessToken());
                    System.out.println("#$##########################################################$");
                } catch (Exception e) {
                    ErrorEntity err = JSON.parseObject(content, ErrorEntity.class);
                    System.out.println("获取access_token异常:errcode"+err.getErrcode()+",errmsg:"+err.getErrmsg());
                    //重新获取一次
                    refreshToken();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (resp != null) {
                try {
                    resp.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (client != null) {
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
