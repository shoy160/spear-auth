package com.yunzhicloud.auth.web.rest.manage;

import com.yunzhicloud.auth.entity.dto.ApplicationDTO;
import com.yunzhicloud.auth.entity.dto.GroupDTO;
import com.yunzhicloud.auth.service.ApplicationService;
import com.yunzhicloud.auth.web.command.manage.ApplicationCmd;
import com.yunzhicloud.auth.web.command.manage.GroupCmd;
import com.yunzhicloud.auth.web.rest.BaseRest;
import com.yunzhicloud.core.domain.dto.PagedDTO;
import com.yunzhicloud.core.domain.dto.ResultDTO;
import com.yunzhicloud.web.vo.PageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.acl.Group;
import java.util.ArrayList;
import java.util.List;

/**
 * 分组相关接口
 *
 * @author shay
 * @date 2021/3/1
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("manage/group")
@Api(tags = "分组相关接口")
public class GroupRest extends BaseRest {

    @PostMapping
    @ApiOperation(value = "创建分组")
    public ResultDTO<GroupDTO> create(@Valid @RequestBody GroupCmd cmd) {
        return success(new GroupDTO());
    }

    @GetMapping("paged")
    @ApiOperation(value = "分组列表")
    public ResultDTO<PagedDTO<GroupDTO>> paged(@Valid PageVO page) {
        List<GroupDTO> list = new ArrayList<>();
        list.add(new GroupDTO());
        PagedDTO<GroupDTO> pagedDTO = PagedDTO.paged(list);
        return success(pagedDTO);
    }

    @GetMapping
    @ApiOperation(value = "分组详情")
    public ResultDTO<GroupDTO> detail(
            @ApiParam(value = "分组ID", required = true) @RequestParam String id) {
        GroupDTO dto = new GroupDTO();
        dto.setId(id);
        return success(dto);
    }

    @PutMapping
    @ApiOperation(value = "编辑分组")
    public ResultDTO edit(
            @ApiParam(value = "分组ID", required = true) @RequestParam String id,
            @Valid @RequestBody GroupCmd cmd
    ) {
        return success();
    }

    @DeleteMapping
    @ApiOperation(value = "删除分组")
    public ResultDTO delete(
            @ApiParam(value = "分组ID", required = true) @RequestParam String id
    ) {
        return success();
    }
}
