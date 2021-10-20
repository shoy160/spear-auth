package club.raveland.auth.service;

import club.raveland.auth.entity.dto.PoolDTO;
import club.raveland.core.domain.dto.PagedDTO;

/**
 * @author shay
 * @date 2021/2/24
 */
public interface PoolService {

    /**
     * 创建用户池
     *
     * @param name   name
     * @param code   code
     * @param domain domain
     * @param logo   logo
     * @param remark remark
     * @return dto
     */
    PoolDTO create(String name, String code, String domain, String logo, String remark);

    /**
     * 用户池详情
     *
     * @param id id
     * @return dto
     */
    PoolDTO detail(String id);

    /**
     * 分页
     *
     * @param page page
     * @param size size
     * @return
     */
    PagedDTO<PoolDTO> paged(int page, int size);

    /**
     * 刷新秘钥
     *
     * @param id id
     * @return 新的秘钥
     */
    String refreshSecret(String id);

    /**
     * 删除用户池
     *
     * @param id id
     */
    void remove(String id);
}
