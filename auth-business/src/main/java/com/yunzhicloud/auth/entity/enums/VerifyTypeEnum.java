package com.yunzhicloud.auth.entity.enums;

import com.yunzhicloud.core.enums.ValueNameEnum;

/**
 * @author shay
 * @date 2021/3/1
 */
public enum VerifyTypeEnum implements ValueNameEnum<Integer> {
    /**
     * 未验证
     */
    None(0, "未验证"),
    Mobile(1, "手机号"),
    Email(1 << 1, "邮箱");

    int value;
    String name;

    VerifyTypeEnum(final int value, final String name) {
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
