package club.raveland.auth.entity.enums;

import club.raveland.core.enums.ValueNameEnum;

/**
 * 授权对象类型
 *
 * @author shay
 * @date 2021/3/4
 */
public enum GrantTargetTypeEnum implements ValueNameEnum<Integer> {
    /**
     * 用户
     */
    User(1, "用户"),
    Role(2, "角色"),
    Group(3, "分组"),
    Organization(4, "组织机构");

    final int value;
    final String name;

    GrantTargetTypeEnum(int value, String name) {
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
