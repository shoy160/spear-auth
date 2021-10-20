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
public class UserEmailCreateCmd {
    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty("用户名")
    private String account;

    @NotBlank(message = "邮箱不能为空")
    @ApiModelProperty("邮箱")
    private String email;

    @NotBlank(message = "密码不能为空")
    @ApiModelProperty("密码")
    private String password;
}
