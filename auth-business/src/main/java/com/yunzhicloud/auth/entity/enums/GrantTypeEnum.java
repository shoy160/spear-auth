package com.yunzhicloud.auth.entity.enums;

import com.yunzhicloud.core.enums.ValueNameEnum;

/**
 * 授权类型
 *
 * @author shay
 * @date 2021/3/4
 */
public enum GrantTypeEnum implements ValueNameEnum<Integer> {
    /**
     * 用户
     */
    User(1, "用户"),
    Role(2, "角色"),
    Group(3, "分组"),
    Organization(4, "组织机构");

    final int value;
    final String name;

    GrantTypeEnum(int value, String name) {
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
