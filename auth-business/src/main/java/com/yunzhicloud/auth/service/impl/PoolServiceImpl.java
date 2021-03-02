package com.yunzhicloud.auth.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.yunzhicloud.auth.dao.PoolMapper;
import com.yunzhicloud.auth.entity.dto.PoolDTO;
import com.yunzhicloud.auth.entity.enums.StateEnum;
import com.yunzhicloud.auth.entity.po.PoolPO;
import com.yunzhicloud.auth.service.PoolService;
import com.yunzhicloud.core.utils.CommonUtils;
import com.yunzhicloud.core.utils.EnumUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.geom.Point2D;
import java.time.LocalDateTime;

/**
 * @author shay
 * @date 2021/2/24
 */
@Service
@AllArgsConstructor
public class PoolServiceImpl implements PoolService {
    private final PoolMapper mapper;

    private PoolDTO convert(PoolPO entity) {
        if (entity == null) {
            return null;
        }
        PoolDTO dto = CommonUtils.toBean(entity, PoolDTO.class);
        dto.setState(EnumUtils.getEnum(entity.getState(), StateEnum.class));
        return dto;
    }

    @Override
    public PoolDTO create(String name, String domain, String logo) {
        PoolPO entity = new PoolPO();
        entity.setId(RandomUtil.randomString(16));
        entity.setSecret(RandomUtil.randomString(32));
        entity.setName(name);
        entity.setDomain(domain);
        entity.setLogo(logo);
        entity.setCreateTime(LocalDateTime.now());
        entity.setState(StateEnum.Normal.getValue());
        mapper.insert(entity);
        return convert(entity);
    }

    @Override
    public PoolDTO detail(String id) {
        PoolPO entity = mapper.selectById(id);
        return convert(entity);
    }
}
