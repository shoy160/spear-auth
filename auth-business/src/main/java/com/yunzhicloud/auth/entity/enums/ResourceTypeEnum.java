package com.yunzhicloud.auth.entity.enums;

import com.yunzhicloud.core.enums.ValueNameEnum;

/**
 * @author shay
 * @date 2021/3/4
 */
public enum ResourceTypeEnum implements ValueNameEnum<Integer> {
    /**
     * 用户
     */
    Data(1, "数据"),
    Api(2, "API"),
    Menu(3, "菜单"),
    Button(4, "按钮");

    final int value;
    final String name;

    ResourceTypeEnum(int value, String name) {
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
