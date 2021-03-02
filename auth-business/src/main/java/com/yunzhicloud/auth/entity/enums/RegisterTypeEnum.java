package com.yunzhicloud.auth.entity.enums;

import com.yunzhicloud.core.enums.ValueNameEnum;

/**
 * @author shay
 * @date 2021/3/1
 */
public enum RegisterTypeEnum implements ValueNameEnum<Integer> {
    /**
     * 后台添加
     */
    Manual(0, "后台添加"),
    Mobile(1, "手机号"),
    Email(2, "邮箱注册");

    int value;
    String name;

    RegisterTypeEnum(final int value, final String name) {
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
