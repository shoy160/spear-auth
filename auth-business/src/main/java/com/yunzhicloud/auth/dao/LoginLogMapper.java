package com.yunzhicloud.auth.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yunzhicloud.auth.entity.po.LoginLogPO;
import org.springframework.stereotype.Repository;

/**
 * 登录日志仓储
 *
 * @author shay
 * @date 2021/3/1
 */
@Repository
public interface LoginLogMapper extends BaseMapper<LoginLogPO> {
}
