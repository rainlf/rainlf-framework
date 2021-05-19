package com.railf.framework.infrastructure.interceptor;

import com.railf.framework.infrastructure.util.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.jboss.logging.MDC;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author : rain
 * @date : 2021/4/30 17:26
 */
@Slf4j
public class BizInterceptor implements HandlerInterceptor {
    private final static String BIZ = "biz";
    private final static String BIZ_ID = "bizId";
    private final static ThreadLocal<String> bizId = new ThreadLocal<>();

    public static String getBizId() {
        return bizId.get();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        bizId.set(UUIDUtils.getUUID(BIZ));
        MDC.put(BIZ_ID, UUIDUtils.getUUID());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        bizId.remove();
        MDC.remove(BIZ_ID);
    }
}
