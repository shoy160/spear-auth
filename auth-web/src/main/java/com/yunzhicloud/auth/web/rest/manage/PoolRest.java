package com.yunzhicloud.auth.web.rest.manage;

import com.yunzhicloud.auth.entity.dto.PoolDTO;
import com.yunzhicloud.auth.entity.dto.PoolStatisticDTO;
import com.yunzhicloud.auth.service.PoolService;
import com.yunzhicloud.auth.web.command.manage.PoolCmd;
import com.yunzhicloud.auth.web.rest.BaseRest;
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
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 用户池相关接口
 *
 * @author shay
 * @date 2021/3/1
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("manage/pool")
@Api(tags = "用户池相关接口")
public class PoolRest extends BaseRest {

    private final PoolService service;

    @PostMapping
    @ApiOperation(value = "创建用户池")
    public ResultDTO<PoolDTO> create(@Valid @RequestBody PoolCmd cmd) {
        PoolDTO dto = service.create(cmd.getName(), cmd.getCode(), cmd.getDomain(), cmd.getLogo(), cmd.getRemark());
        return success(dto);
    }

    @GetMapping
    @ApiOperation(value = "用户池详情")
    public ResultDTO<PoolDTO> detail(@ApiParam(value = "用户池ID", required = true) @RequestParam String id) {
        PoolDTO dto = service.detail(id);
        return success(dto);
    }

    @GetMapping("paged")
    @ApiOperation(value = "用户池列表")
    public ResultDTO<PagedDTO<PoolDTO>> paged(@Valid PageVO pageVO) {
        PagedDTO<PoolDTO> paged = service.paged(pageVO.getPageNum(), pageVO.getPageSize());
        return success(paged);
    }

    @PutMapping
    @ApiOperation(value = "编辑用户池")
    public ResultDTO edit(
            @ApiParam(value = "用户池ID", required = true) @RequestParam String id,
            @Valid @RequestBody PoolCmd cmd
    ) {

        return success();
    }

    @PutMapping("secret")
    @ApiOperation(value = "刷新秘钥")
    public ResultDTO<String> refreshSecret(@ApiParam(value = "用户池ID", required = true) @RequestParam String id) {
        String secret = service.refreshSecret(id);
        return success(secret);
    }

    @GetMapping("statistic")
    @ApiOperation(value = "用户池统计")
    public ResultDTO<PoolStatisticDTO> statistic() {
        PoolStatisticDTO dto = new PoolStatisticDTO();
        dto.setLastLoginList(new ArrayList<>());
        dto.setLastRegisterList(new ArrayList<>());
        dto.setUserRegisters(new HashMap<>());
        return success(dto);
    }

    @DeleteMapping
    @ApiOperation(value = "删除用户池")
    public ResultDTO delete(
            @ApiParam(value = "用户池ID", required = true) @RequestParam String id
    ) {
        service.remove(id);
        return success();
    }
}
