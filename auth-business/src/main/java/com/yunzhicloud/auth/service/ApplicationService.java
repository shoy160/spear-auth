package com.yunzhicloud.auth.service;

import com.yunzhicloud.auth.entity.dto.ApplicationDTO;
import com.yunzhicloud.auth.entity.enums.StateEnum;
import com.yunzhicloud.core.exception.BusinessException;

/**
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
     * @param id
     * @return
     */
    ApplicationDTO get(String id);

    /**
     * 获取并检测应用
     *
     * @param id
     * @return
     */
    default ApplicationDTO getAndCheck(String id) {
        ApplicationDTO app = get(id);
        if (app == null) {
            throw new BusinessException("应用不存在");
        }
        if (app.getState() != StateEnum.Normal) {
            throw new BusinessException("应用已下线");
        }
        return app;
    }
}
