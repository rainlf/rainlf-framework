package com.railf.framework.infrastructure.log;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author : rain
 * @date : 2021/4/30 17:55
 */
@Slf4j
public class EsLogManager implements InitializingBean {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Autowired
    private EsLogProperties esLogProperties;

    /**
     * log partition by day in es index
     */
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
    private final int initCapacity = 1000_0000;
    private final BlockingQueue<EsLog> esLogBlockingQueue = new LinkedBlockingQueue<>(initCapacity);

    /**
     * put data in no blocking model
     */
    public void sendLog(EsLog esLog) {
        esLogBlockingQueue.offer(esLog);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        new Thread(() -> {
            for (; ; ) {
                try {
                    // obtain data in blocking model
                    sendLog2Es(esLogBlockingQueue.take());
                } catch (Exception e) {
                    log.error("save log failed", e);
                    try {
                        TimeUnit.SECONDS.sleep(10);
                    } catch (InterruptedException interruptedException) {
                    }
                }
            }
        }, "es-log-thread").start();
        EsLogManager.instance = this;
    }

    private void sendLog2Es(EsLog esLog) throws IOException {
        String index = esLogProperties.getIndexName() + "-" + LocalDateTime.now().format(dtf);
        String id = esLog.getId();
        Map<String, Object> esLogMap = esLog.toMap();
        if (esLogMap != null) {
            IndexRequest indexRequest = new IndexRequest(index).id(id).source(esLogMap);
            restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        }
    }

    /**
     * instance model by spring component
     */
    private static EsLogManager instance;

    public static EsLogManager getInstance() {
        return instance;
    }
}
