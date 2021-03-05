package com.yunzhicloud.auth.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yunzhicloud.auth.dao.PoolMapper;
import com.yunzhicloud.auth.entity.dto.PoolDTO;
import com.yunzhicloud.auth.entity.enums.StateEnum;
import com.yunzhicloud.auth.entity.po.PoolPO;
import com.yunzhicloud.auth.service.PoolService;
import com.yunzhicloud.core.cache.Cache;
import com.yunzhicloud.core.domain.dto.PagedDTO;
import com.yunzhicloud.core.session.YzSession;
import com.yunzhicloud.core.utils.CommonUtils;
import com.yunzhicloud.core.utils.EnumUtils;
import com.yunzhicloud.data.utils.PagedUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.geom.Point2D;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * @author shay
 * @date 2021/2/24
 */
@Service
@AllArgsConstructor
public class PoolServiceImpl implements PoolService {
    private final PoolMapper mapper;
    private final YzSession session;
    private final Cache<String, Object> cache;

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
        entity.setState(StateEnum.Normal.getValue());
        mapper.insert(entity);
        return convert(entity);
    }

    @Override
    public PoolDTO detail(String id) {
        String key = String.format("auth:pool:%s", id);
        Object value = cache.get(key);
        if (value != null) {
            return (PoolDTO) value;
        }
        PoolPO entity = mapper.selectById(id);
        PoolDTO dto = convert(entity);
        cache.put(key, dto, 5, TimeUnit.MINUTES);
        return dto;
    }

    @Override
    public PagedDTO<PoolDTO> paged(int page, int size) {
        LambdaQueryWrapper<PoolPO> wrapper = new LambdaQueryWrapper<>();
        String userId = session.userIdAsString();
        if (CommonUtils.isNotEmpty(userId)) {
            wrapper.eq(PoolPO::getTenantId, userId);
        }
        Page<PoolPO> paged = mapper.selectPage(new Page<>(page, size), wrapper);
        return PagedUtils.convert(paged, this::convert);
    }
}
