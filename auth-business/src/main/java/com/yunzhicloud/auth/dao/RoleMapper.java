package com.yunzhicloud.auth.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yunzhicloud.auth.entity.po.RolePO;
import org.springframework.stereotype.Repository;

/**
 * 角色仓储
 *
 * @author shay
 * @date 2021/3/5
 */
@Repository
public interface RoleMapper extends BaseMapper<RolePO> {
}
