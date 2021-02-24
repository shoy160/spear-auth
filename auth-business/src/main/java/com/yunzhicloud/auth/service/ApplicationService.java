package com.yunzhicloud.auth.service;

import com.yunzhicloud.auth.entity.enums.StateEnum;
import com.yunzhicloud.auth.entity.po.ApplicationPO;
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
    ApplicationPO create(String name, String redirect, String logo, String domain);

    /**
     * 获取应用详情
     *
     * @param id
     * @return
     */
    ApplicationPO get(String id);

    /**
     * 获取并检测应用
     *
     * @param id
     * @return
     */
    default ApplicationPO getAndCheck(String id) {
        ApplicationPO app = get(id);
        if (app == null) {
            throw new BusinessException("应用不存在");
        }
        if (app.getState() != StateEnum.Normal.getValue()) {
            throw new BusinessException("应用已下线");
        }
        return app;
    }
}
