package club.raveland.auth.entity.dto;

import club.raveland.data.domain.dto.BaseDateDTO;
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
public class GroupDTO extends BaseDateDTO {
    @ApiModelProperty("分组ID")
    private String id;
    @ApiModelProperty("分组名称")
    private String name;
    @ApiModelProperty("分组编码")
    private String code;
    @ApiModelProperty("分组备注")
    private String remark;
}
