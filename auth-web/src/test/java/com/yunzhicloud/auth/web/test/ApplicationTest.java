package com.yunzhicloud.auth.web.test;

import com.yunzhicloud.auth.entity.dto.ApplicationDTO;
import com.yunzhicloud.auth.service.ApplicationService;
import com.yunzhicloud.core.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @author shay
 * @date 2021/2/24
 */
@Slf4j
public class ApplicationTest extends BaseTest {
    @Resource
    private ApplicationService service;

    @Test
    public void createTest() {
        useSession(() -> {
            ApplicationDTO po = service.create("消息中心", "https://message.app-chengdu1.yunzhicloud.com", "", "message");
            log.info(JsonUtils.toJson(po));
        });
    }
}
