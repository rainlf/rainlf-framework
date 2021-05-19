package com.railf.framework.infrastructure.log;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : rain
 * @date : 2021/4/30 17:37
 */
@Slf4j
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class EsLog {
    private String id;
    private String bizId;
    private String host;
    private String level;
    private String threadName;
    private String loggerName;
    private String message;
    private String stackTrace;
    private Date timestamp;

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        Field[] fields = this.getClass().getDeclaredFields();
        try {
            for (Field field : fields) {
                if (Modifier.isStatic(field.getModifiers())) {
                    continue;
                }
                field.setAccessible(true);
                Object value = field.get(this);
                if (value != null) {
                    map.put(field.getName(), value);
                }
            }
        } catch (IllegalAccessException e) {
            log.error("es log entity to map failed", e);
            return null;
        }
        return map;
    }
}
