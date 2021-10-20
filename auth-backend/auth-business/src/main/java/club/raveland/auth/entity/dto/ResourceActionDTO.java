package club.raveland.auth.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author shay
 * @date 2021/3/4
 */
@Getter
@Setter
@ToString
@ApiModel
public class ResourceActionDTO {
    /**
     * 动作
     */
    @ApiModelProperty("动作")
    private String action;
    /**
     * 描述
     */
    @ApiModelProperty("描述")
    private String remark;
}
