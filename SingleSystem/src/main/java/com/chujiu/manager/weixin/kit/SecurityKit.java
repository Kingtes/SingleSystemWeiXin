package com.chujiu.manager.weixin.kit;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by tianci on 2017/5/25.
 */
public class SecurityKit {
    public static String sha1(String str) {
        StringBuffer sb = new StringBuffer();
        try {
            MessageDigest md =  MessageDigest.getInstance("sha1");
            md.update(str.getBytes());
            byte[] msg = md.digest();
            for (byte b : msg) {
                sb.append(String.format("%02x", b));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
