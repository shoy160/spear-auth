package com.yunzhicloud.auth.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yunzhicloud.auth.entity.po.RegisterRulePO;
import org.springframework.stereotype.Repository;

/**
 * 注册规则仓储
 *
 * @author shay
 * @date 2021/3/5
 */
@Repository
public interface RegisterRuleMapper extends BaseMapper<RegisterRulePO> {
}
