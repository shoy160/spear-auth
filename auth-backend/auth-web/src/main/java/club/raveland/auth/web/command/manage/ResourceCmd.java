package club.raveland.auth.web.command.manage;

import club.raveland.auth.entity.dto.ResourceActionDTO;
import club.raveland.auth.entity.enums.ResourceTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author shay
 * @date 2021/3/4
 */
@Getter
@Setter
@ApiModel
public class ResourceCmd {

    @ApiModelProperty("分组")
    @NotBlank(message = "分组不能为空")
    private String namespace;

    @ApiModelProperty("资源名称")
    @NotBlank(message = "资源名称不能为空")
    private String name;
    @ApiModelProperty("资源类型")
    @NotBlank(message = "资源类型不能为空")
    private ResourceTypeEnum type;

    @ApiModelProperty("URL")
    private String url;

    @ApiModelProperty("图标")
    private String icon;
    
    @ApiModelProperty("资源描述")
    private String remark;

    @ApiModelProperty("操作列表")
    private List<ResourceActionDTO> actions;
}
