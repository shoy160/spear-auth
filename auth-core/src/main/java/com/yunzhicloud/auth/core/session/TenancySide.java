package com.yunzhicloud.auth.core.session;

import com.yunzhicloud.core.enums.ValueNameEnum;

/**
 * 租户站点类型
 *
 * @author shay
 * @date 2021/3/1
 */
public enum TenancySide implements ValueNameEnum<Integer> {
    /**
     * 租户
     */
    Tenant(1, "租户"),
    Host(2, "主机");
    int value;
    String name;

    TenancySide(final int value, final String name) {
        this.value = value;
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }
}
