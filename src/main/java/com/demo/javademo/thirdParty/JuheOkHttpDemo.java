package com.demo.javademo.thirdParty;

import com.demo.javademo.util.OkHttp3Util;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JuheOkHttpDemo {
    // 天气情况查询接口地址
    public static String API_URL = "http://apis.juhe.cn/simpleWeather/query";
    // 接口请求Key
    public static String API_KEY = "3cf2b7d030964697b52610cefe24b954";

    public static void main(String[] args) throws IOException {
        JuheOkHttpDemo example = new JuheOkHttpDemo();
        example.doRequestCityWeather();
    }

    //1.根据城市查询天气
    public void doRequestCityWeather() {
        Map<String, Object> params = new HashMap<>();//组合参数
        params.put("city", "上海");
        params.put("key", API_KEY);

        String response = getResultByUrl(API_URL, params, null);
        try {
            JSONObject jsonObject = JSONObject.fromObject(response);
            int error_code = jsonObject.getInt("error_code");
            if (error_code == 0) {
                System.out.println("调用接口成功");

                JSONObject result = jsonObject.getJSONObject("result");
                JSONObject realtime = result.getJSONObject("realtime");

                System.out.printf("城市：%s%n", result.getString("city"));
                System.out.printf("天气：%s%n", realtime.getString("info"));
                System.out.printf("温度：%s%n", realtime.getString("temperature"));
                System.out.printf("湿度：%s%n", realtime.getString("humidity"));
                System.out.printf("风向：%s%n", realtime.getString("direct"));
                System.out.printf("风力：%s%n", realtime.getString("power"));
                System.out.printf("空气质量：%s%n", realtime.getString("aqi"));
            } else {
                System.out.println("调用接口失败：" + jsonObject.getString("reason"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param strUrl 请求地址
     * @param params 请求参数
     * @param method 请求方法
     * @return 网络请求字符串
     * @throws Exception
     */
    public String getResultByUrl(String strUrl, Map params, String method) {
        if (method == null || method.equals("GET")) {
            strUrl = strUrl + "?" + urlencode(params);
        }
        log.info(strUrl);
        return OkHttp3Util.sendByGetUrl(strUrl);
    }

    //将map型转为请求参数型
    public static String urlencode(Map<String, Object> data) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry entry : data.entrySet()) {
            try {
                sb.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue() + "", "UTF-8")).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
