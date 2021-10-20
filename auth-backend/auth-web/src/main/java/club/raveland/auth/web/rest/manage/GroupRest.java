package club.raveland.auth.web.rest.manage;

import club.raveland.auth.entity.dto.GroupDTO;
import club.raveland.auth.web.command.manage.GroupCmd;
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
