package com.yunzhicloud.auth.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yunzhicloud.auth.entity.po.ApplicationPO;
import org.springframework.stereotype.Repository;

/**
 * 应用仓储
 *
 * @author shay
 * @date 2021/2/24
 */
@Repository
public interface ApplicationMapper extends BaseMapper<ApplicationPO> {
}
