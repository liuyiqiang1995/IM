package com.qdcares.smart.mq.util;

import java.util.UUID;

/**
 * UUID工具类
 */
public class UUIDUtil {

    /**
     * 获取UUID
     * @return
     */
    public static String getUUID(){
        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        return uuid;
    }

}
