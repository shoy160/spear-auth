package club.raveland.auth.service;

import club.raveland.auth.entity.dto.GrantDTO;
import club.raveland.auth.entity.enums.GrantTypeEnum;

import java.util.List;

/**
 * 授权服务
 *
 * @author shay
 * @date 2021/3/9
 */
public interface GrantService {
    /**
     * 授权列表
     *
     * @param poolId    poolId
     * @param namespace 分组ID
     * @param type      类型
     * @return list
     */
    List<GrantDTO> list(String poolId, String namespace, GrantTypeEnum type);
}
