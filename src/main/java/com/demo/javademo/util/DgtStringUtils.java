package com.demo.javademo.util;

import com.alibaba.fastjson.JSONObject;

/**
 * 字符串工具类，继承自org.springframework.util.StringUtils
 * 
 * @author Allen Wang
 */
public class DgtStringUtils extends org.springframework.util.StringUtils {

    public static String toHexStr(String strSource) {
        return toHexStr(strSource, "UTF-8");
    }

    public static String toHexStr(String strPart, String encodeType) {
        try {
            return toHexStr(strPart.getBytes(encodeType));
        } catch (Exception e) {
            return "";
        }
    }

    public static String toHexStr(byte[] b) {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            result.append(String.format("%02X", b[i]));
        }
        return result.toString();
    }

    public static String toJsonStr(String text) {
        Object jsonObj = JSONObject.parse(text);
        return JSONObject.toJSONString(jsonObj);
    }

    public static String object2JsonString(Object obj)
    {
        String jsonStr = "";
        try {
            //将java对象转换为json对象
            JSONObject json = (JSONObject) JSONObject.toJSON(obj);
            //将json对象转换为字符串
            jsonStr = json.toString();

        } catch (Exception e) {
            // TODO, Add log
        }

        return jsonStr;
    }
}
