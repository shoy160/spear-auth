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
public class UserMobileCreateCmd {

    @ApiModelProperty("手机号码")
    private String mobile;

    @NotBlank(message = "密码不能为空")
    @ApiModelProperty("密码")
    private String password;
}
