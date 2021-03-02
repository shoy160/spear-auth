package com.yunzhicloud.auth.service;

import com.yunzhicloud.auth.entity.dto.PoolDTO;

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
    PoolDTO create(String name, String domain, String logo);

    /**
     * 用户池详情
     *
     * @param id id
     * @return dto
     */
    PoolDTO detail(String id);

}
