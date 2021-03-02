package com.yunzhicloud.auth.entity.enums;

import com.yunzhicloud.core.enums.ValueNameEnum;

/**
 * @author shay
 * @date 2021/3/1
 */
public enum GenderEnum implements ValueNameEnum<Integer> {
    /**
     * 后台添加
     */
    None(0, "暂无"),
    Male(1, "男士"),
    Female(2, "女士");

    int value;
    String name;

    GenderEnum(final int value, final String name) {
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
