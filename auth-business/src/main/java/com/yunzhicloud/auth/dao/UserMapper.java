package com.yunzhicloud.auth.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yunzhicloud.auth.entity.po.UserPO;
import org.springframework.stereotype.Repository;

/**
 * 用户仓储
 *
 * @author shay
 * @date 2021/3/1
 */
@Repository
public interface UserMapper extends BaseMapper<UserPO> {
}
