package com.demo.javademo.util;

import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.Properties;

@Slf4j
public class PreloadUtils {
    private static Properties appProp = new Properties();

    static {
        synchronized (PreloadUtils.class) {
            loadConfig();
        }
    }

    public static void loadConfig() {
        log.info("----- load properties 'preload.properties' begin -----");

        InputStream in = PreloadUtils.class.getClassLoader().getResourceAsStream("preload.properties");
        try {
            appProp.load(in);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        log.info("----- load properties 'preload.properties' end -----");
    }

    public static Properties getConfig() {
        if (appProp == null) {
            loadConfig();
        }

        return appProp;
    }
}
