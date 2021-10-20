package club.raveland.auth.entity.dto;

import club.raveland.auth.entity.enums.ResourceTypeEnum;
import club.raveland.data.domain.dto.BaseDateDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author shay
 * @date 2021/3/4
 */
@Getter
@Setter
@ToString
@ApiModel
public class ResourceDTO extends BaseDateDTO {
    @ApiModelProperty("资源ID")
    private String id;
    @ApiModelProperty("资源分组ID")
    private String namespace;
    @ApiModelProperty("资源名称")
    private String name;
    @ApiModelProperty("资源类型")
    private ResourceTypeEnum type;
    @ApiModelProperty("资源图标")
    private String icon;
    @ApiModelProperty("资源URL")
    private String url;
    @ApiModelProperty("资源描述")
    private String remark;
    @ApiModelProperty("资源操作列表")
    private List<ResourceActionDTO> actions;
}
