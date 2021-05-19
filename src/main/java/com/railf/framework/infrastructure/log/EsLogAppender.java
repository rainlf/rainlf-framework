package com.railf.framework.infrastructure.log;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.ThrowableProxyUtil;
import ch.qos.logback.core.AppenderBase;
import com.railf.framework.infrastructure.interceptor.BizInterceptor;
import com.railf.framework.infrastructure.util.UUIDUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

/**
 * @author : rain
 * @date : 2021/4/30 17:36
 * <p>
 * es log appender for logback
 */
public class EsLogAppender extends AppenderBase<ILoggingEvent> {
    @Override
    protected void append(ILoggingEvent iLoggingEvent) {
        String host = null;
        try {
            host = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
        }

        EsLog esLog = new EsLog(
                UUIDUtils.getUUID(),
                BizInterceptor.getBizId(),
                host,
                iLoggingEvent.getLevel().toString(),
                iLoggingEvent.getThreadName(),
                iLoggingEvent.getLoggerName(),
                iLoggingEvent.getFormattedMessage(),
                ThrowableProxyUtil.asString(iLoggingEvent.getThrowableProxy()),
                new Date(iLoggingEvent.getTimeStamp())
        );
        EsLogManager.getInstance().sendLog(esLog);
    }
}
