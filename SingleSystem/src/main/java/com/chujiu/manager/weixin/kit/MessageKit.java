package com.chujiu.manager.weixin.kit;

import com.chujiu.model.WeiXinFinalValue;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.XMLWriter;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.*;

/**
 * Created by tianci on 2017/5/26.
 */
public class MessageKit {

    private static Map<String, String> replyMsgs = new HashMap<>();

    static{
        replyMsgs.put("你好", "你好，亲");
        replyMsgs.put("hello", "hello，亲");
        replyMsgs.put("fuck", "fuck，亲");
        replyMsgs.put("牛掰", "你好，牛掰");

        replyMsgs.put("A0001",
                "问君能有几多愁，五人四坑赶快投！\n" +
                "队友坑我千百遍，我待队友如初恋。\n" +
                "走位不对，补兵不会，团战撤退。\n" +
                "文能挂机喷队友，武能越塔送人头。\n" +
                "静则百年不见人，动则千里送超神。\n" +
                "英勇闪现送一血，卖起队友不回头。"
        );
    }

    /**
     * 将微信返回值转换为map
     * @param request
     * @return
     * @throws IOException
     */
    public static Map<String, String> reqMsg2Map(HttpServletRequest request) throws IOException {
        try {
            String xml = req2Xml(request);
            Map<String, String> maps = new HashMap<>();

            Document document = DocumentHelper.parseText(xml);
            Element root = document.getRootElement();
            List<Element> eles = root.elements();

            for (Element e : eles) {
                maps.put(e.getName(), e.getTextTrim());
            }
            return maps;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将微信返回的消息转换为xml格式字符串
     * @param request
     * @return
     * @throws IOException
     */
    public static String req2Xml(HttpServletRequest request) throws IOException {
        BufferedReader br = null;
        br = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
        String str = null;
        StringBuffer sb = new StringBuffer();
        while ((str = br.readLine()) != null) {
            sb.append(str);
        }
        return sb.toString();
    }

    public static String handlerMsg(Map<String, String> msgMap) throws IOException {
        String msgType = msgMap.get("MsgType");
        if (WeiXinFinalValue.MSG_TEXT_TYPE.equals(msgType)) {
            return textTypeHandler(msgMap);
        } else if (WeiXinFinalValue.MSG_EVENT_TYPE.equals(msgType)) {
            return eventTypeHandler(msgMap);
        }
        return null;
    }

    private static String textTypeHandler(Map<String, String> msgMap) throws IOException {
        Map<String, String> map = new HashMap<>();
        map.put("ToUserName", msgMap.get("FromUserName"));
        map.put("FromUserName", msgMap.get("ToUserName"));
        map.put("CreateTime", new Date().getTime() + "");
        map.put("MsgType", msgMap.get("MsgType"));
        String replyContent = "您说什么呢，我听不懂啊！";
        String con = msgMap.get("Content");
        if (replyMsgs.containsKey(con)) {
            replyContent = replyMsgs.get(con);
        }
        map.put("Content", replyContent);
        return map2xml(map);
    }

    private static String eventTypeHandler(Map<String, String> msgMap) throws IOException {
        String eventKey = msgMap.get("EventKey");
        Map<String, String> map = new HashMap<>();
        map.put("ToUserName", msgMap.get("FromUserName"));
        map.put("FromUserName", msgMap.get("ToUserName"));
        map.put("CreateTime", new Date().getTime() + "");
        map.put("MsgType", WeiXinFinalValue.MSG_TEXT_TYPE);
        String replyContent = "您说什么呢，我听不懂啊！";
        if (replyMsgs.containsKey(eventKey)) {
            replyContent = replyMsgs.get(eventKey);
        }
        map.put("Content", replyContent);
        return map2xml(map);
    }

    private static String map2xml(Map<String, String> map) throws IOException {
        Document d = DocumentHelper.createDocument();
        Element root = d.addElement("xml");
        Set<String> keys = map.keySet();

        for (String key : keys) {
            root.addElement(key).addText(map.get(key));
        }
        StringWriter sw = new StringWriter();
        XMLWriter xw = new XMLWriter(sw);
        xw.setEscapeText(false);
        xw.write(d);
        return sw.toString();
    }


}
