package com.railf.framework.infrastructure.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author : rain
 * @date : 2021/5/19 14:35
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder().title("API-DOCUMENT").build())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.railf.framework.adapter.facade"))
                .paths(PathSelectors.any())
                .build();
    }
}
