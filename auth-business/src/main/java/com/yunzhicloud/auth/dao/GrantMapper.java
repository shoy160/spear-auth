package com.yunzhicloud.auth.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yunzhicloud.auth.entity.po.GrantPO;
import org.springframework.stereotype.Repository;

/**
 * 授权仓储
 *
 * @author shay
 * @date 2021/3/5
 */
@Repository
public interface GrantMapper extends BaseMapper<GrantPO> {
}
