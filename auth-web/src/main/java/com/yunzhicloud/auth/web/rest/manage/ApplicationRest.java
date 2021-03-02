package com.yunzhicloud.auth.web.rest.manage;

import com.yunzhicloud.auth.entity.dto.ApplicationDTO;
import com.yunzhicloud.auth.service.ApplicationService;
import com.yunzhicloud.auth.web.command.manage.ApplicationCmd;
import com.yunzhicloud.core.domain.ResultDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 应用相关接口
 *
 * @author shay
 * @date 2021/3/1
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("manage/app")
@Api(tags = "应用相关接口")
public class ApplicationRest extends BaseRest {
    private final ApplicationService service;

    @PostMapping
    @ApiOperation(value = "创建应用")
    public ResultDTO<ApplicationDTO> create(@Validated @RequestBody ApplicationCmd cmd) {
        String poolId = currentPool();
        ApplicationDTO dto = service.create(cmd.getName(), cmd.getRedirect(), cmd.getLogo(), cmd.getDomain());
        return success(dto);
    }
}
