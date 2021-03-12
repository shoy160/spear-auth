package com.yunzhicloud.auth.entity.enums;

import com.yunzhicloud.core.enums.ValueNameEnum;

/**
 * @author shay
 * @date 2021/3/8
 */
public enum RegisterRuleEnum implements ValueNameEnum<Integer> {
    /**
     * 后台添加
     */
    Allow(1, "允许策略"),
    Deny(2, "禁止策略"),
    Forbidden(4, "禁止注册");

    int value;
    String name;

    RegisterRuleEnum(final int value, final String name) {
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
