package com.chujiu.manager.weixin.kit;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tianci on 2017/5/26.
 */
public class MessageKit {
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
}
