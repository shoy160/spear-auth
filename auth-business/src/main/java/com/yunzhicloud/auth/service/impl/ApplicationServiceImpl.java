package com.yunzhicloud.auth.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.yunzhicloud.auth.dao.ApplicationMapper;
import com.yunzhicloud.auth.dao.PoolMapper;
import com.yunzhicloud.auth.entity.dto.ApplicationDTO;
import com.yunzhicloud.auth.entity.dto.PoolDTO;
import com.yunzhicloud.auth.entity.enums.StateEnum;
import com.yunzhicloud.auth.entity.po.ApplicationPO;
import com.yunzhicloud.auth.entity.po.PoolPO;
import com.yunzhicloud.auth.service.ApplicationService;
import com.yunzhicloud.core.cache.Cache;
import com.yunzhicloud.core.session.YzSession;
import com.yunzhicloud.core.utils.CommonUtils;
import com.yunzhicloud.core.utils.EnumUtils;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author shay
 * @date 2021/2/24
 */
@Service
@AllArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {
    private final ApplicationMapper mapper;
    private final PoolMapper poolMapper;
    private final YzSession session;
    private final Cache<String, Object> cache;

    private ApplicationDTO convert(ApplicationPO entity) {
        if (entity == null) {
            return null;
        }
        ApplicationDTO dto = CommonUtils.toBean(entity, ApplicationDTO.class);
        PoolPO pool = poolMapper.selectById(entity.getPoolId());
        if (pool != null) {
            dto.setPoolSecret(pool.getSecret());
        }
        dto.setState(EnumUtils.getEnum(entity.getState(), StateEnum.class));
        return dto;
    }

    @Override
    public ApplicationDTO create(String name, String redirect, String logo, String domain) {
        ApplicationPO entity = new ApplicationPO();
        entity.setId(RandomUtil.randomString(16));
        entity.setSecret(RandomUtil.randomString(32));
        String poolId = this.session.getTenantId(String.class, "yz_auth");
        entity.setPoolId(poolId);
        entity.setName(name);
        entity.setLogo(logo);
        entity.setDomain(domain);
        entity.setState(StateEnum.Normal.getValue());
        entity.setRedirectLogin(redirect);
        entity.setDomain(domain);
        mapper.insert(entity);
        return convert(entity);
    }

    @Override
    public ApplicationDTO detail(String id) {
        String key = String.format("auth:app:%s", id);
        Object value = cache.get(key);
        if (value != null) {
            return (ApplicationDTO) value;
        }
        ApplicationPO entity = mapper.selectById(id);
        ApplicationDTO dto = convert(entity);
        cache.put(key, dto, 5, TimeUnit.MINUTES);
        return dto;
    }
}
