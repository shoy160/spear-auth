package com.yunzhicloud.auth.service;

import com.yunzhicloud.auth.entity.dto.PoolDTO;
import com.yunzhicloud.core.domain.dto.PagedDTO;

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

    /**
     * 分页
     *
     * @param page page
     * @param size size
     * @return
     */
    PagedDTO<PoolDTO> paged(int page, int size);

}
