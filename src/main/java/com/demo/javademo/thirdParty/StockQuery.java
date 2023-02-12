package com.demo.javademo.thirdParty;

import com.demo.javademo.util.OkHttp3Util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author：marco.pan
 * @ClassName：StockQuery
 * @Description：
 * @date: 2022年07月08日 20:28
 */
public class StockQuery {
    // 天气情况查询接口地址
    public static String API_URL = "http://web.juhe.cn:8080/finance/stock/hs";
    // 接口请求Key
    public static String API_KEY = "9cb40fa280da866cc6606cd921cc87e2";

    public static void main(String[] args) {
        Map<String, Object> params = new HashMap<>();//组合参数
        params.put("gid", "sh601009");
        params.put("key", API_KEY);
        String queryParams = urlencode(params);
        String requestUrl = API_URL + "?" + queryParams;
        String response = OkHttp3Util.sendByGetUrl(requestUrl);
        System.out.println(response);
        String result = OkHttp3Util.postJson("http://localhost:8080/api/user",
                "{\n" +
                        "    \"id\": 1,\n" +
                        "    \"age\": 18,\n" +
                        "    \"name\": \"1234\"\n" +
                        "}");
        System.out.println(result);
    }

    /**
     * 将map型转为请求参数型
     *
     * @param data
     * @return
     */
    public static String urlencode(Map<String, ?> data) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, ?> i : data.entrySet()) {
            try {
                sb.append(i.getKey()).append("=")
                        .append(URLEncoder.encode(i.getValue() + "", "UTF-8"))
                        .append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        String result = sb.toString();
        return result.substring(0, result.lastIndexOf("&"));
    }
}
