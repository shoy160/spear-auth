package club.raveland.auth.entity.enums;

import club.raveland.core.enums.ValueNameEnum;

/**
 * @author shay
 * @date 2021/2/24
 */
public enum StateEnum implements ValueNameEnum<Integer> {
    /**
     * 正常
     */
    Normal(1, "正常"),
    Delete(4, "删除");

    final int value;
    final String name;

    StateEnum(int value, String name) {
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
