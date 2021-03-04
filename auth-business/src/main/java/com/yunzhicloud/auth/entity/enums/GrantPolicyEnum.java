package com.yunzhicloud.auth.entity.enums;

import com.yunzhicloud.core.enums.ValueNameEnum;

/**
 * 授权策略
 *
 * @author shay
 * @date 2021/3/4
 */
public enum GrantPolicyEnum implements ValueNameEnum<Integer> {
    /**
     * 允许
     */
    Allow(1, "允许"),
    Deny(2, "拒绝");

    final int value;
    final String name;

    GrantPolicyEnum(int value, String name) {
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
