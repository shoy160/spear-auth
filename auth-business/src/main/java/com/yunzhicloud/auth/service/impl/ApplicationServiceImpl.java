package com.yunzhicloud.auth.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.yunzhicloud.auth.dao.ApplicationMapper;
import com.yunzhicloud.auth.entity.enums.StateEnum;
import com.yunzhicloud.auth.entity.po.ApplicationPO;
import com.yunzhicloud.auth.service.ApplicationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author shay
 * @date 2021/2/24
 */
@Service
@AllArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {
    private final ApplicationMapper mapper;

    @Override
    public ApplicationPO create(String name, String redirect, String logo, String domain) {
        ApplicationPO entity = new ApplicationPO();
        entity.setId(RandomUtil.randomString(16));
        entity.setSecret(RandomUtil.randomString(32));
        entity.setPoolId("yz_auth");
        entity.setName(name);
        entity.setLogo(logo);
        entity.setDomain(domain);
        entity.setState(StateEnum.Normal.getValue());
        entity.setRedirectLogin(redirect);
        entity.setDomain(domain);
        mapper.insert(entity);
        return entity;
    }

    @Override
    public ApplicationPO get(String id) {
        ApplicationPO entity = mapper.selectById(id);
        return entity;
    }
}
