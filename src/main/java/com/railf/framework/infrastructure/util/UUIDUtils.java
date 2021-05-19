package com.railf.framework.infrastructure.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * @author : rain
 * @date : 2021/4/30 17:27
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UUIDUtils {

    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String getUUID(String prefix) {
        return prefix + "-" + getUUID();
    }

}
