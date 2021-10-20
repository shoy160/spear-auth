package club.raveland.auth.web.test;

import club.raveland.auth.entity.dto.ApplicationDTO;
import club.raveland.auth.service.ApplicationService;
import club.raveland.core.utils.JsonUtils;
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
