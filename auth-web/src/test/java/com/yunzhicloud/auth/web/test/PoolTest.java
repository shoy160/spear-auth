package com.yunzhicloud.auth.web.test;

import com.yunzhicloud.auth.entity.dto.PoolDTO;
import com.yunzhicloud.auth.service.PoolService;
import com.yunzhicloud.core.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.annotation.Resource;
import java.io.Closeable;

/**
 * @author shay
 * @date 2021/2/24
 */
@Slf4j
public class PoolTest extends BaseTest {

    @Resource
    private PoolService service;


    @Test
    public void createTest() {
        useSession(() -> {
            PoolDTO po = service.create("测试用户池", "test", "test", "", "");
            log.info(JsonUtils.toJson(po));
        });
    }
}
