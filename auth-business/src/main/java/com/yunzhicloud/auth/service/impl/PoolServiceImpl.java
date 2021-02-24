package com.yunzhicloud.auth.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.yunzhicloud.auth.dao.PoolMapper;
import com.yunzhicloud.auth.entity.enums.StateEnum;
import com.yunzhicloud.auth.entity.po.PoolPO;
import com.yunzhicloud.auth.service.PoolService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author shay
 * @date 2021/2/24
 */
@Service
@AllArgsConstructor
public class PoolServiceImpl implements PoolService {
    private final PoolMapper mapper;

    @Override
    public PoolPO create(String name, String domain, String logo) {
        PoolPO entity = new PoolPO();
        entity.setId(RandomUtil.randomString(16));
        entity.setSecret(RandomUtil.randomString(32));
        entity.setName(name);
        entity.setDomain(domain);
        entity.setLogo(logo);
        entity.setState(StateEnum.Normal.getValue());
        mapper.insert(entity);
        return entity;
    }
}
