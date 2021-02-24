package com.yunzhicloud.auth.web.test;

import com.yunzhicloud.auth.AuthConstants;
import com.yunzhicloud.auth.entity.po.PoolPO;
import com.yunzhicloud.auth.service.PoolService;
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
public class PoolTest {

    @Resource
    private PoolService service;

    @Test
    public void createTest() {
        PoolPO po = service.create("统一认证", "auth", "");
        log.info(JsonUtils.toJson(po));
    }
}
