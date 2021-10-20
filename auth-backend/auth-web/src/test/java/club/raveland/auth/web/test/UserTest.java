package club.raveland.auth.web.test;

import club.raveland.auth.entity.dto.UserDTO;
import club.raveland.auth.service.UserService;
import club.raveland.core.security.Token;
import club.raveland.core.utils.JsonUtils;
import club.raveland.web.security.JwtTokenBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;

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
        Token t = JwtTokenBuilder.verify(token, "yvzoww1vhinsir4rmorteirfz6h4ersn");
        Assert.assertNotNull(t);
    }
}
