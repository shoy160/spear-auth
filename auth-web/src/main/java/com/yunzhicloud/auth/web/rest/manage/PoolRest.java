package com.yunzhicloud.auth.web.rest.manage;

import com.yunzhicloud.auth.service.PoolService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shay
 * @date 2021/3/1
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("mange/pool")
@Api(tags = "用户池接口")
public class PoolRest extends BaseRest {
    private final PoolService service;

}
