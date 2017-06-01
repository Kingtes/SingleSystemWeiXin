package com.chujiu.manager.weixin.kit;

import com.alibaba.fastjson.JSON;
import com.chujiu.model.WeiXinContext;
import com.chujiu.model.WeiXinFinalValue;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by tianci on 2017/6/1.
 */
public class WeiXinKit {

    /**
     * 向微信接口发送post请求
     * @param url
     * @param data
     * @param type
     * @return
     */
    public static String post2wx(String url, String data, String type) {
        CloseableHttpResponse resp = null;
        CloseableHttpClient client = null;

        try {
            client = HttpClients.createDefault();
            HttpPost post = new HttpPost(url);
            post.addHeader("Content-Type", type);
            StringEntity entity = new StringEntity(data, ContentType.create(type, "UTF-8"));
            post.setEntity(entity);
            resp = client.execute(post);
            int statusCode = resp.getStatusLine().getStatusCode();
            if (statusCode >= 200 && statusCode < 300) {
                String str = EntityUtils.toString(resp.getEntity());
                System.out.println(str);
                return str;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (client != null) {
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (resp != null) {
                try {
                    resp.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
