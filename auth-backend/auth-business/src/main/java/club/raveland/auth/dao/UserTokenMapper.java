package club.raveland.auth.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import club.raveland.auth.entity.po.UserTokenPO;
import org.springframework.stereotype.Repository;

/**
 * 登录状态仓储
 *
 * @author shay
 * @date 2021/3/5
 */
@Repository
public interface UserTokenMapper extends BaseMapper<UserTokenPO> {
}
