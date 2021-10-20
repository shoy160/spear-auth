package club.raveland.auth.web.rest.manage;

import club.raveland.auth.entity.dto.RoleDTO;
import club.raveland.auth.web.command.manage.RoleCmd;
import club.raveland.core.domain.dto.PagedDTO;
import club.raveland.core.domain.dto.ResultDTO;
import club.raveland.web.model.vo.PageVO;
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
 * 角色相关接口
 *
 * @author shay
 * @date 2021/3/1
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("manage/role")
@Api(tags = "角色相关接口")
public class RoleRest extends BaseRest {

    @PostMapping
    @ApiOperation(value = "创建角色")
    public ResultDTO<RoleDTO> create(@Valid @RequestBody RoleCmd cmd) {
        return success(new RoleDTO());
    }

    @GetMapping("paged")
    @ApiOperation(value = "角色列表")
    public ResultDTO<PagedDTO<RoleDTO>> paged(
            @Valid PageVO page,
            @ApiParam(value = "分组", required = true) @RequestParam String namespace
    ) {
        List<RoleDTO> list = new ArrayList<>();
        PagedDTO<RoleDTO> pagedDTO = new PagedDTO<>(0, list);
        return success(pagedDTO);
    }

    @GetMapping
    @ApiOperation(value = "角色详情")
    public ResultDTO<RoleDTO> detail(
            @ApiParam(value = "角色ID", required = true) @RequestParam String id) {
        RoleDTO dto = new RoleDTO();
        dto.setId(id);
        return success(dto);
    }

    @PutMapping
    @ApiOperation(value = "编辑角色")
    public ResultDTO edit(
            @ApiParam(value = "角色ID", required = true) @RequestParam String id,
            @Valid @RequestBody RoleCmd cmd
    ) {
        return success();
    }

    @DeleteMapping
    @ApiOperation(value = "删除角色")
    public ResultDTO delete(
            @ApiParam(value = "角色ID", required = true) @RequestParam String id
    ) {
        return success();
    }
}
