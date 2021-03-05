package com.yunzhicloud.auth.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yunzhicloud.auth.entity.po.GroupPO;
import org.springframework.stereotype.Repository;

/**
 * 分组仓储
 *
 * @author shay
 * @date 2021/3/5
 */
@Repository
public interface GroupMapper extends BaseMapper<GroupPO> {
}
