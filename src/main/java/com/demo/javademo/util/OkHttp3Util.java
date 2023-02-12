package com.demo.javademo.util;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author: marco.pan
 * @ClassName: OkHttp3Util
 * @Description: OkHttp3工具类
 * @date: 2022年01月18日 10:48 上午
 */
@Slf4j
public class OkHttp3Util {
    public static final MediaType jsonMediaType = MediaType.parse("application/json;charset=utf-8");

    private OkHttp3Util() {
    }

    public static OkHttpClient getInstance() {
        return Singleton.INSTANCE.getInstance();
    }

    private enum Singleton {
        INSTANCE;

        private OkHttpClient okHttpClient;

        Singleton() {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.connectTimeout(5L, TimeUnit.SECONDS);
            builder.readTimeout(5L, TimeUnit.SECONDS);
            builder.writeTimeout(5L, TimeUnit.SECONDS);
            ConnectionPool connectionPool = new ConnectionPool(50, 60, TimeUnit.SECONDS);
            builder.connectionPool(connectionPool);
            okHttpClient = builder.build();
        }

        public OkHttpClient getInstance() {
            return okHttpClient;
        }
    }

    //GetOkHttp3Util
    public static String sendByGetUrl(String url) {
        try {
            Request request = new Request.Builder().url(url).build();
            Response response = OkHttp3Util.getInstance().newCall(request).execute();
            if (response.isSuccessful()) {
                String content = response.body().string();
                if (StringUtils.isNotBlank(content)) {
                    return content;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * post-form
     */
    public static String postForm(String url, Map<String, String> params) {
        try {
            FormBody.Builder builder = new FormBody.Builder();
            for (Map.Entry<String, String> m : params.entrySet()) {
                builder.add(m.getKey(), m.getValue());
            }
            RequestBody body = builder.build();
            Request request = new Request.Builder().post(body).url(url).build();
            Response response = OkHttp3Util.getInstance().newCall(request).execute();
            if (response.isSuccessful()) {
                String content = response.body().string();
                if (StringUtils.isNotBlank(content)) {
                    return content;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //post-JSON
    public static String postJson(String url, String jsonBody) {
        try {
            Request request = new Request.Builder()
                    .url(url)
                    .post(RequestBody.create(jsonMediaType, jsonBody))
                    .build();

            Response response = OkHttp3Util.getInstance().newCall(request).execute();
            if (response.isSuccessful()) {
                assert response.body() != null;
                String content = response.body().string();
                if (StringUtils.isNotBlank(content)) {
                    return content;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
