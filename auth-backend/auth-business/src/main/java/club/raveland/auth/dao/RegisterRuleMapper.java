package club.raveland.auth.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import club.raveland.auth.entity.po.RegisterRulePO;
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
