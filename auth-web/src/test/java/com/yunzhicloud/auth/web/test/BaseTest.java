package com.yunzhicloud.auth.web.test;

import com.yunzhicloud.auth.core.AuthConstants;
import com.yunzhicloud.auth.entity.dto.UserDTO;
import com.yunzhicloud.auth.service.PoolService;
import com.yunzhicloud.auth.web.AuthApplication;
import com.yunzhicloud.core.session.SessionDTO;
import com.yunzhicloud.core.session.YzSession;
import com.yunzhicloud.core.utils.JsonUtils;
import com.yunzhicloud.test.YzCloudSpringRunner;
import com.yunzhicloud.test.YzCloudTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.Closeable;

/**
 * @author shay
 * @date 2021/3/9
 */
@RunWith(YzCloudSpringRunner.class)
@SpringBootTest(classes = AuthApplication.class)
@YzCloudTest(value = AuthConstants.SERVICE_NAME)
public abstract class BaseTest {

    @Resource
    protected YzSession session;

    private final SessionDTO currentSession;

    protected BaseTest() {
        currentSession = new SessionDTO("148c08585fc2124f", "148c083380021192");
    }

    protected void useSession(Runnable action) {
        try (Closeable ignored = session.use(currentSession)) {
            action.run();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
