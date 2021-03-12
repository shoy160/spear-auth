package com.yunzhicloud.auth.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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

    /**
     * 是否存在编码
     *
     * @param code code
     * @return exists
     */
    default boolean exists(String code) {
        LambdaQueryWrapper<PoolPO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PoolPO::getCode, code);
        Integer count = selectCount(wrapper);
        return count > 0;
    }

    /**
     * 是否存在编码
     *
     * @param domain domain
     * @return exists
     */
    default boolean existsDomain(String domain) {
        LambdaQueryWrapper<PoolPO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PoolPO::getDomain, domain);
        Integer count = selectCount(wrapper);
        return count > 0;
    }
}
