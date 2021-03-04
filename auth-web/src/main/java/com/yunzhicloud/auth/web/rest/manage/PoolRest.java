package com.yunzhicloud.auth.web.rest.manage;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.yunzhicloud.auth.entity.dto.PoolDTO;
import com.yunzhicloud.auth.entity.dto.PoolStatisticDTO;
import com.yunzhicloud.auth.web.command.manage.PoolCmd;
import com.yunzhicloud.auth.web.rest.BaseRest;
import com.yunzhicloud.core.domain.ResultDTO;
import com.yunzhicloud.core.utils.CommonUtils;
import com.yunzhicloud.web.vo.PageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 用户池相关接口
 *
 * @author shay
 * @date 2021/3/1
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("manage/pool")
@Api(tags = "用户池相关接口")
public class PoolRest extends BaseRest {
//    private final PoolService service;

    private PoolDTO createDto() {
        return createDto(null);
    }

    private PoolDTO createDto(String id) {
        PoolDTO dto = new PoolDTO();
        if (CommonUtils.isEmpty(id)) {
            id = IdUtil.simpleUUID();
        }
        dto.setId(id);
        dto.setName("Demo");
        dto.setLogo("https://files.authing.co/user-contents/photos/222c3166-0e18-414e-8eb8-6b8f0c1b544a.png");
        dto.setRemark("demo");
        dto.setCreateTime(new Date());
        dto.setDomain("");
        dto.setUserCount(0);
        dto.setSecret(RandomUtil.randomString(32));
        dto.setLastRegister(DateUtil.offsetMinute(new Date(), RandomUtil.randomInt(-60, -12)));
        return dto;
    }

    @PostMapping
    @ApiOperation(value = "创建用户池")
    public ResultDTO<PoolDTO> create(@Valid @RequestBody PoolCmd cmd) {
        return success(createDto());
    }

    @GetMapping
    @ApiOperation(value = "用户池详情")
    public ResultDTO<PoolDTO> detail(@ApiParam(value = "用户池ID", required = true) @RequestParam String id) {
        return success(createDto(id));
    }

    @GetMapping("paged")
    @ApiOperation(value = "用户池列表")
    public ResultDTO<List<PoolDTO>> list(@Valid PageVO pageVO) {
        List<PoolDTO> list = new ArrayList<>();
        list.add(createDto());
        return success(list);
    }

    @PutMapping
    @ApiOperation(value = "编辑用户池")
    public ResultDTO edit(@ApiParam(value = "用户池ID", required = true) @RequestParam String id) {
        return success();
    }

    @PutMapping("secret")
    @ApiOperation(value = "刷新秘钥")
    public ResultDTO refreshSecret(@ApiParam(value = "用户池ID", required = true) @RequestParam String id) {
        return success();
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
        return success();
    }
}
