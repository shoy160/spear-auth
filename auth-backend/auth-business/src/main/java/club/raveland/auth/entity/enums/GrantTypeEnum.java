package club.raveland.auth.entity.enums;

import club.raveland.core.enums.ValueNameEnum;

/**
 * 授权类型
 *
 * @author shay
 * @date 2021/3/4
 */
public enum GrantTypeEnum implements ValueNameEnum<Integer> {
    /**
     * 登录授权
     */
    User(1, "登录授权"),
    Role(2, "资源授权");

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
