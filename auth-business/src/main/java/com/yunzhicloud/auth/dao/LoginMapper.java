package com.yunzhicloud.auth.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yunzhicloud.auth.entity.po.LoginPO;
import org.springframework.stereotype.Repository;

/**
 * 登录状态仓储
 *
 * @author shay
 * @date 2021/3/5
 */
@Repository
public interface LoginMapper extends BaseMapper<LoginPO> {
}
