package club.raveland.auth.web.command.manage;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author shay
 * @date 2021/3/4
 */
@Getter
@Setter
@ApiModel
public class GroupCmd {
    @NotBlank(message = "分组名称不能为空")
    @ApiModelProperty("分组名称")
    private String name;

    @NotBlank(message = "分组标识不能为空")
    @ApiModelProperty("分组标识")
    private String code;

    @ApiModelProperty("分组描述")
    private String remark;
}
