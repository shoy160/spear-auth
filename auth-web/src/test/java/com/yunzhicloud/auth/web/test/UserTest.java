package com.yunzhicloud.auth.web.test;

import com.yunzhicloud.auth.core.AuthConstants;
import com.yunzhicloud.auth.entity.dto.UserDTO;
import com.yunzhicloud.auth.service.UserService;
import com.yunzhicloud.auth.web.AuthApplication;
import com.yunzhicloud.core.session.SessionDTO;
import com.yunzhicloud.core.session.YzSession;
import com.yunzhicloud.core.utils.JsonUtils;
import com.yunzhicloud.test.YzCloudSpringRunner;
import com.yunzhicloud.test.YzCloudTest;
import com.yunzhicloud.web.vo.Token;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.Closeable;
import java.io.IOException;

/**
 * @author shay
 * @date 2021/3/1
 */
@Slf4j
public class UserTest extends BaseTest {

    @Resource
    private UserService service;

    @Test
    public void createTest() {
        useSession(() -> {
            log.info(session.userIdAsString());
            UserDTO dto = service.createByEmail("test@qq.com", "123456", "shay");
            log.info(JsonUtils.toJson(dto));
        });
        log.info(session.userIdAsString());
    }

    @Test
    public void detailTest() {
        UserDTO dto = service.detail("5b54589e4ab247c2a34a3bbc62c9e5ef");
        log.info(JsonUtils.toJson(dto));
    }

    @Test
    public void tokenTest() {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiIiwiY2xhaW1zIjp7InZlcmlmeSI6IjcyMmE3MTFlLTYzMjktNGY2Zi1hOTYyLTBmYjM3ZTFkYjY1MyJ9LCJpZCI6ImU1NDFkZTNlYmY3NzRhZmM4MjE4YjM4MTFjZjI3ZTlmIiwiZXhwIjoxNjE1ODc5NzU5fQ.uYsy_2NDJhpEvvDvO5z3pPVqR6chwSgQHR0Y6ljEg08";
        Token t = Token.verify(token, "yvzoww1vhinsir4rmorteirfz6h4ersn");
        Assert.assertNotNull(t);
    }
}
