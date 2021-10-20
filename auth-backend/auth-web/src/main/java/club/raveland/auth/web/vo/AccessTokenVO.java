package club.raveland.auth.web.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author shay
 * @date 2021/2/24
 */
@Getter
@Setter
@ApiModel
@ToString
public class AccessTokenVO {
    
    @ApiModelProperty("AccessToken")
    private String access_token;
    @ApiModelProperty("TokenType")
    private String token_type;
    @ApiModelProperty("AccessToken")
    private Integer expires_in;
    @ApiModelProperty("RefreshToken")
    private String refresh_token;
    @ApiModelProperty("权限范围")
    private String scope;
    @ApiModelProperty("用户ID")
    private String user_id;
}
