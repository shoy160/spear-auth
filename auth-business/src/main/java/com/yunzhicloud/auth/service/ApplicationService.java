package com.yunzhicloud.auth.service;

import com.yunzhicloud.auth.entity.dto.ApplicationDTO;
import com.yunzhicloud.auth.entity.enums.StateEnum;
import com.yunzhicloud.core.domain.dto.PagedDTO;
import com.yunzhicloud.core.exception.BusinessException;

/**
 * 应用服务
 *
 * @author shay
 * @date 2021/2/24
 */
public interface ApplicationService {
    /**
     * 创建应用
     *
     * @param name
     * @param redirect
     * @param logo
     * @param domain
     * @return
     */
    ApplicationDTO create(String name, String redirect, String logo, String domain);

    /**
     * 获取应用详情
     *
     * @param id id
     * @return
     */
    ApplicationDTO detail(String id);

    /**
     * 分页
     *
     * @param page page
     * @param size size
     * @return
     */
    PagedDTO<ApplicationDTO> paged(int page, int size);

    /**
     * 刷新秘钥
     *
     * @param id id
     */
    String refreshSecret(String id);

    /**
     * 获取并检测应用
     *
     * @param id
     * @return
     */
    default ApplicationDTO getAndCheck(String id) {
        ApplicationDTO app = detail(id);
        if (app == null) {
            throw new BusinessException("应用不存在");
        }
        if (app.getState() != StateEnum.Normal) {
            throw new BusinessException("应用已下线");
        }
        return app;
    }
}
