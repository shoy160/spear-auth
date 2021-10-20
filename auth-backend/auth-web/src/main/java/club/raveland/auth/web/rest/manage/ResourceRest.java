package club.raveland.auth.web.rest.manage;

import club.raveland.auth.entity.dto.ResourceDTO;
import club.raveland.auth.service.ApplicationService;
import club.raveland.auth.web.command.manage.ResourceCmd;
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
 * 资源相关接口
 *
 * @author shay
 * @date 2021/3/1
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("manage/resource")
@Api(tags = "资源相关接口")
public class ResourceRest extends BaseRest {
    private final ApplicationService service;

    @PostMapping
    @ApiOperation(value = "创建资源")
    public ResultDTO<ResourceDTO> create(@Valid @RequestBody ResourceCmd cmd) {
        return success(new ResourceDTO());
    }

    @GetMapping("paged")
    @ApiOperation(value = "资源列表")
    public ResultDTO<PagedDTO<ResourceDTO>> paged(
            @Valid PageVO page,
            @ApiParam(value = "分组") String namespace
    ) {
        List<ResourceDTO> list = new ArrayList<>();
        PagedDTO<ResourceDTO> pagedDTO = new PagedDTO<>(0, list);
        return success(pagedDTO);
    }

    @GetMapping
    @ApiOperation(value = "资源详情")
    public ResultDTO<ResourceDTO> detail(
            @ApiParam(value = "资源ID", required = true) @RequestParam String id) {
        return success(new ResourceDTO());
    }

    @PutMapping
    @ApiOperation(value = "编辑资源")
    public ResultDTO edit(
            @ApiParam(value = "资源ID", required = true) @RequestParam String id,
            @Valid @RequestBody ResourceCmd cmd
    ) {
        return success();
    }

    @DeleteMapping
    @ApiOperation(value = "删除资源")
    public ResultDTO delete(
            @ApiParam(value = "资源ID", required = true) @RequestParam String id
    ) {
        return success();
    }
}
