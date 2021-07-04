package com.railf.framework.adapter.facade;

import com.railf.framework.adapter.dto.APIResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.railf.framework.infrastructure.i18n.LocaleMessage.local;

/**
 * @author : rain
 * @date : 2021/5/19 16:37
 */
@Slf4j
@RestController
public class I18nApi {

    @GetMapping("i18n")
    public APIResponse<String> i18nMessage() {
        return APIResponse.ok(local("hello", "rain"));
    }
}
