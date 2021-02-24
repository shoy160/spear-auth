package com.yunzhicloud.auth.service;

import com.yunzhicloud.auth.entity.po.PoolPO;

/**
 * @author shay
 * @date 2021/2/24
 */
public interface PoolService {

    /**
     * 创建用户池
     *
     * @param name
     * @param domain
     * @param logo
     * @return
     */
    PoolPO create(String name, String domain, String logo);
}
