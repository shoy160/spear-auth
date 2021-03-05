package com.yunzhicloud.auth.web.rest.manage;

import com.yunzhicloud.auth.entity.dto.ApplicationDTO;
import com.yunzhicloud.auth.entity.dto.GrantDTO;
import com.yunzhicloud.auth.entity.dto.UserDTO;
import com.yunzhicloud.auth.entity.enums.GrantPolicyEnum;
import com.yunzhicloud.auth.service.ApplicationService;
import com.yunzhicloud.auth.web.command.manage.ApplicationCmd;
import com.yunzhicloud.auth.web.command.manage.GrantCmd;
import com.yunzhicloud.auth.web.rest.BaseRest;
import com.yunzhicloud.core.domain.dto.PagedDTO;
import com.yunzhicloud.core.domain.dto.ResultDTO;
import com.yunzhicloud.web.vo.PageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

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
    public ResultDTO<ApplicationDTO> create(@Valid @RequestBody ApplicationCmd cmd) {
        ApplicationDTO dto = service.create(cmd.getName(), cmd.getRedirect(), cmd.getLogo(), cmd.getDomain());
        return success(dto);
    }

    @GetMapping("paged")
    @ApiOperation(value = "应用列表")
    public ResultDTO<PagedDTO<ApplicationDTO>> paged(@Valid PageVO page) {
        PagedDTO<ApplicationDTO> paged = service.paged(page.getPageNum(), page.getPageSize());
        return success(paged);
    }

    @GetMapping
    @ApiOperation(value = "应用详情")
    public ResultDTO<ApplicationDTO> detail(
            @ApiParam(value = "应用ID", required = true) @RequestParam String id) {
        ApplicationDTO dto = service.detail(id);
        return success(dto);
    }

    @PutMapping
    @ApiOperation(value = "编辑应用")
    public ResultDTO edit(
            @ApiParam(value = "应用ID", required = true) @RequestParam String id,
            @Valid @RequestBody ApplicationCmd cmd
    ) {
        return success();
    }

    @PutMapping("secret")
    @ApiOperation(value = "刷新秘钥")
    public ResultDTO refreshSecret(@ApiParam(value = "应用ID", required = true) @RequestParam String id) {
        return success();
    }

    @PutMapping("access/all")
    @ApiOperation(value = "设置默认访问策略")
    public ResultDTO defaultAccess(
            @ApiParam(value = "应用ID", required = true) @RequestParam String id,
            @ApiParam(value = "默认策略", required = true) @RequestParam GrantPolicyEnum policy
    ) {
        return success();
    }

    @GetMapping("access/list")
    @ApiOperation(value = "访问控制列表")
    public ResultDTO<List<GrantDTO>> accessList(@ApiParam(value = "应用ID", required = true) @RequestParam String id) {
        List<GrantDTO> list = new ArrayList<>();
        return success(list);
    }

    @PostMapping("access")
    @ApiOperation(value = "添加访问控制")
    public ResultDTO<GrantDTO> createAccess(
            @ApiParam(value = "应用ID", required = true) @RequestParam String id,
            @Valid @RequestBody GrantCmd cmd
    ) {
        GrantDTO dto = new GrantDTO();
        return success(dto);
    }

    @DeleteMapping("access")
    @ApiOperation(value = "删除访问控制")
    public ResultDTO deleteAccess(
            @ApiParam(value = "应用ID", required = true) @RequestParam String id,
            @ApiParam(value = "授权ID", required = true) @RequestParam String grantId
    ) {
        return success();
    }

    @GetMapping("logon")
    @ApiOperation(value = "登录状态列表")
    public ResultDTO<List<UserDTO>> logon(@ApiParam(value = "应用ID", required = true) @RequestParam String id) {
        List<UserDTO> list = new ArrayList<>();
        return success(list);
    }

    @PostMapping("logout")
    @ApiOperation(value = "强制下线")
    public ResultDTO logout(
            @ApiParam(value = "应用ID", required = true) @RequestParam String id,
            @ApiParam(value = "用户ID", required = true) @RequestParam String userId
    ) {
        return success();
    }

    @DeleteMapping
    @ApiOperation(value = "删除应用")
    public ResultDTO delete(
            @ApiParam(value = "应用ID", required = true) @RequestParam String id
    ) {
        return success();
    }
}
