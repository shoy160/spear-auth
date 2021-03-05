package com.yunzhicloud.auth.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yunzhicloud.auth.entity.po.PoolPO;
import org.springframework.stereotype.Repository;

/**
 * 用户池仓储
 *
 * @author shay
 * @date 2021/2/24
 */
@Repository
public interface PoolMapper extends BaseMapper<PoolPO> {
}
