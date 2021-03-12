package com.yunzhicloud.auth.web.rest.manage;

import com.yunzhicloud.auth.entity.dto.UserDTO;
import com.yunzhicloud.auth.service.UserService;
import com.yunzhicloud.auth.web.command.manage.UserEditCmd;
import com.yunzhicloud.auth.web.command.manage.UserEmailCreateCmd;
import com.yunzhicloud.auth.web.command.manage.UserMobileCreateCmd;
import com.yunzhicloud.core.domain.dto.PagedDTO;
import com.yunzhicloud.core.domain.dto.ResultDTO;
import com.yunzhicloud.web.vo.PageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 用户相关接口
 *
 * @author shay
 * @date 2021/3/1
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("manage/user")
@Api(tags = "用户相关接口")
public class UserRest extends BaseRest {

    private final UserService service;

    @PostMapping("email")
    @ApiOperation(value = "创建邮箱用户")
    public ResultDTO<UserDTO> createEmail(@Valid @RequestBody UserEmailCreateCmd cmd) {
        UserDTO dto = service.createByEmail(cmd.getEmail(), cmd.getPassword(), cmd.getAccount());
        return success(dto);
    }

    @PostMapping("mobile")
    @ApiOperation(value = "创建手机用户")
    public ResultDTO<UserDTO> createMobile(@Valid @RequestBody UserMobileCreateCmd cmd) {
        UserDTO dto = service.createByMobile(cmd.getMobile(), cmd.getPassword());
        return success(dto);
    }

    @GetMapping("paged")
    @ApiOperation(value = "用户列表")
    public ResultDTO<PagedDTO<UserDTO>> paged(@Valid PageVO page) {
        PagedDTO<UserDTO> paged = service.paged(page.getPageNum(), page.getPageSize());
        return success(paged);
    }

    @GetMapping
    @ApiOperation(value = "用户详情")
    public ResultDTO<UserDTO> detail(
            @ApiParam(value = "用户ID", required = true) @RequestParam String id) {
        UserDTO dto = service.detail(id);
        return success(dto);
    }

    @PutMapping
    @ApiOperation(value = "编辑用户")
    public ResultDTO edit(
            @ApiParam(value = "用户ID", required = true) @RequestParam String id,
            @Valid @RequestBody UserEditCmd cmd
    ) {
        //todo
        return success();
    }

    @DeleteMapping
    @ApiOperation(value = "删除用户")
    public ResultDTO delete(
            @ApiParam(value = "用户ID", required = true) @RequestParam String id
    ) {
        //todo
        return success();
    }
}
