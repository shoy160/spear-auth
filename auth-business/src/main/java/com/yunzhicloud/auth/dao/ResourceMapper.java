package com.yunzhicloud.auth.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yunzhicloud.auth.entity.po.ResourcePO;
import org.springframework.stereotype.Repository;

/**
 * 资源仓储
 *
 * @author shay
 * @date 2021/3/5
 */
@Repository
public interface ResourceMapper extends BaseMapper<ResourcePO> {
}
