package com.chujiu.manager.weixin.controller;

import com.chujiu.manager.quartz.controller.RefreshAccessTokenTask;
import com.chujiu.manager.weixin.kit.MessageKit;
import com.chujiu.manager.weixin.kit.SecurityKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by tianci on 2017/5/25.
 */
@Controller
@RequestMapping("wx")
public class WinXinController {

    @Autowired
    private RefreshAccessTokenTask refreshAccessTokenTask;

    private static final String TOKEN = "tianciwx";
    //1）将token、timestamp、nonce三个参数进行字典序排序
    //2）将三个参数字符串拼接成一个字符串进行sha1加密
    //3）开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
    @RequestMapping(value = "wget", method = RequestMethod.GET)
    public void initWX(HttpServletRequest req, HttpServletResponse resp) {
        String timestamp = req.getParameter("timestamp");
        String nonce = req.getParameter("nonce");

        String signature = req.getParameter("signature");
        String echostr = req.getParameter("echostr");

        String[] arrs = {TOKEN, nonce, timestamp};
        String sha1 = "";
        //1.排序
        if (arrs != null) {
            Arrays.sort(arrs);
            String arrStr="";
            for (String str : arrs) {
                arrStr += str;
            }
            //2.sha1加密
            sha1 = SecurityKit.sha1(arrStr);
        }

        System.out.println(sha1.equals(signature));
        if (sha1.equals(signature)) {
            try {
                resp.getWriter().println(echostr);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @RequestMapping(value = "wget", method = RequestMethod.POST)
    public void receiveInfo(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        /*BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream(), "UTF-8"));
        String str = null;
        while ((str = br.readLine()) != null) {
            System.out.println(str);
        }*/
        Map<String, String> msgMap = MessageKit.reqMsg2Map(req);
        System.out.println("$############微信客户端发送的信息###############$");
        System.out.println(msgMap);
        System.out.println("$############微信客户端发送的信息###############$");

        String respCon = MessageKit.handlerMsg(msgMap);
        System.out.println("回复xml："+respCon);
        resp.setContentType("application/xml;chartset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(respCon);

    }

    /**
     * 项目启动则获取一次access_token
     */
    @PostConstruct
    public void getAccessToken() {
        refreshAccessTokenTask.refreshToken();
    }
}
