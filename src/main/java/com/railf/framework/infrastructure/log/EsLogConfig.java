package com.railf.framework.infrastructure.log;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * @author : rain
 * @date : 2021/5/19 13:31
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(EsLogProperties.class)
@ConditionalOnProperty(prefix = "es.log", value = "enable", havingValue = "true")
public class EsLogConfig {

    @Autowired
    private EsLogProperties esLogProperties;

    @Bean
    @ConditionalOnMissingBean
    public EsLogManager esLogManager() {
        return new EsLogManager();
    }

    @Bean
    @ConditionalOnMissingBean
    public RestHighLevelClient restHighLevelClient() throws InterruptedException {
        String schema = esLogProperties.getSchema();
        String host = esLogProperties.getHost();
        Integer port = esLogProperties.getPort();
        String username = esLogProperties.getUsername();
        String password = esLogProperties.getPassword();
        if (StringUtils.hasText(username) && StringUtils.hasText(password)) {
            return createRestHighLevelClient(schema, host, port, username, password);
        } else {
            log.warn("create rest high level client without security to {}://{}:{}", schema, host, port);
            return createRestHighLevelClient(schema, host, port);
        }
    }

    /**
     * create rest high level client without security
     */
    private RestHighLevelClient createRestHighLevelClient(String schema, String host, Integer port) throws InterruptedException {
        RestHighLevelClient restHighLevelClient = null;
        boolean fail = true;
        while (fail) {
            try {
                restHighLevelClient = new RestHighLevelClient(
                        RestClient.builder(new HttpHost(host, port, schema))
                                .setHttpClientConfigCallback(httpClientBuilder -> {
                                    CredentialsProvider provider = new BasicCredentialsProvider();
                                    return httpClientBuilder.setKeepAliveStrategy((response, context) -> 30 * 1000).setDefaultCredentialsProvider(provider);
                                }));
                fail = false;
            } catch (Exception e) {
                log.error("create restHighLevelClient failed", e);
                TimeUnit.SECONDS.sleep(60);
            }
        }
        return restHighLevelClient;
    }

    /**
     * create rest high level client with security
     */
    private RestHighLevelClient createRestHighLevelClient(String schema, String address, Integer port, String username, String password) throws InterruptedException {
        RestHighLevelClient restHighLevelClient = null;
        boolean fail = true;
        while (fail) {
            try {
                restHighLevelClient = new RestHighLevelClient(
                        RestClient.builder(new HttpHost(address, port, schema))
                                .setHttpClientConfigCallback(httpClientBuilder -> {
                                    CredentialsProvider provider = new BasicCredentialsProvider();
                                    AuthScope scope = new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT, AuthScope.ANY_REALM);
                                    UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(username, password);
                                    provider.setCredentials(scope, credentials);
                                    return httpClientBuilder.setKeepAliveStrategy((response, context) -> 30 * 1000).setDefaultCredentialsProvider(provider);
                                }));

                fail = false;
            } catch (Exception e) {
                log.error("create restHighLevelClient failed", e);
                TimeUnit.SECONDS.sleep(60);
            }
        }
        return restHighLevelClient;
    }
}
