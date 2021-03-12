package com.yunzhicloud.auth.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yunzhicloud.auth.entity.po.ApplicationPO;
import com.yunzhicloud.auth.entity.po.PoolPO;
import org.springframework.stereotype.Repository;

/**
 * 应用仓储
 *
 * @author shay
 * @date 2021/2/24
 */
@Repository
public interface ApplicationMapper extends BaseMapper<ApplicationPO> {

    /**
     * 是否存在编码
     *
     * @param domain domain
     * @return exists
     */
    default boolean existsDomain(String domain) {
        LambdaQueryWrapper<ApplicationPO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ApplicationPO::getDomain, domain);
        Integer count = selectCount(wrapper);
        return count > 0;
    }
}
