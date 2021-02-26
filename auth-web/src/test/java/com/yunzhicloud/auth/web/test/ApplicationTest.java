package com.yunzhicloud.auth.web.test;

import com.yunzhicloud.auth.AuthConstants;
import com.yunzhicloud.auth.entity.dto.ApplicationDTO;
import com.yunzhicloud.auth.entity.po.ApplicationPO;
import com.yunzhicloud.auth.service.ApplicationService;
import com.yunzhicloud.auth.web.AuthApplication;
import com.yunzhicloud.core.utils.JsonUtils;
import com.yunzhicloud.test.YzCloudSpringRunner;
import com.yunzhicloud.test.YzCloudTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author shay
 * @date 2021/2/24
 */
@Slf4j
@RunWith(YzCloudSpringRunner.class)
@SpringBootTest(classes = AuthApplication.class)
@YzCloudTest(value = AuthConstants.SERVICE_NAME)
public class ApplicationTest {
    @Resource
    private ApplicationService service;

    @Test
    public void createTest() {
        ApplicationDTO po = service.create("统一认证中心", "https://auth.app-chengdu1.yunzhicloud.com", "", "auth");
        log.info(JsonUtils.toJson(po));
    }
}
