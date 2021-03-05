package com.yunzhicloud.auth.web.rest.manage;

import com.yunzhicloud.auth.entity.dto.GrantDTO;
import com.yunzhicloud.auth.service.ApplicationService;
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
 * 授权相关接口
 *
 * @author shay
 * @date 2021/3/1
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("manage/grant")
@Api(tags = "授权相关接口")
public class GrantRest extends BaseRest {
    private final ApplicationService service;

    @PostMapping
    @ApiOperation(value = "创建授权")
    public ResultDTO<GrantDTO> create(@Valid @RequestBody GrantCmd cmd) {
        return success(new GrantDTO());
    }

    @GetMapping("paged")
    @ApiOperation(value = "授权列表")
    public ResultDTO<PagedDTO<GrantDTO>> paged(
            @Valid PageVO page,
            @ApiParam(value = "分组") String namespace
    ) {
        List<GrantDTO> list = new ArrayList<>();
        PagedDTO<GrantDTO> pagedDTO = new PagedDTO<>(0, list);
        return success(pagedDTO);
    }

    @GetMapping
    @ApiOperation(value = "授权详情")
    public ResultDTO<GrantDTO> detail(
            @ApiParam(value = "授权ID", required = true) @RequestParam String id) {
        GrantDTO dto = new GrantDTO();
        dto.setId(id);
        return success(dto);
    }

    @PutMapping
    @ApiOperation(value = "编辑授权")
    public ResultDTO edit(
            @ApiParam(value = "授权ID", required = true) @RequestParam String id,
            @Valid @RequestBody GrantCmd cmd
    ) {
        return success();
    }

    @DeleteMapping
    @ApiOperation(value = "删除授权")
    public ResultDTO delete(
            @ApiParam(value = "授权ID", required = true) @RequestParam String id
    ) {
        return success();
    }
}
