package com.yunzhicloud.auth.web.command;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.yunzhicloud.core.exception.BusinessException;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 授权参数
 *
 * @author shay
 * @date 2021/2/24
 */
@Getter
@Setter
@ApiModel
public class TokenCmd {
    private final static String TYPE_CODE = "authorization_code";
    private final static String TYPE_CLIENT = "client_credentials";
    private final static String TYPE_PASSWORD = "password";
    private final static String TYPE_REFRESH = "refresh_token";
    private final static String[] TYPES = new String[]{
            TYPE_CODE, TYPE_CLIENT, TYPE_PASSWORD, TYPE_REFRESH
    };
    @ApiModelProperty("授权类型(authorization_code,client_credentials,password,refresh_token)")
    @NotBlank(message = "授权类型不能为空")
    private String grant_type;
    @ApiModelProperty("客户端ID")
    @NotBlank(message = "客户端ID不能为空")
    private String client_id;
    @ApiModelProperty("客户端秘钥(grant_type=authorization_code,client_credentials,refresh_token)")
    private String client_secret;
    @ApiModelProperty("授权码(grant_type=authorization_code)")
    private String code;
    @ApiModelProperty("跳转uri")
    private String redirect_uri;
    @ApiModelProperty("用户名(grant_type=password)")
    private String username;
    @ApiModelProperty("用户密码(grant_type=password)")
    private String password;
    @ApiModelProperty("刷新令牌(grant_type=refresh_token)")
    private String refresh_token;

    public boolean isCode() {
        return TYPE_CODE.equals(this.grant_type);
    }

    public void validate() {
        if (!ArrayUtil.contains(TYPES, this.grant_type)) {
            throw new BusinessException(String.format("不受支持的授权类型:%s", this.grant_type));
        }
        switch (this.grant_type) {
            case TYPE_CODE:
                if (StrUtil.isBlank(this.client_secret)) {
                    throw new BusinessException("客户端秘钥不能为空");
                }
                if (StrUtil.isBlank(this.code)) {
                    throw new BusinessException("授权码不能为空");
                }
                break;
            case TYPE_CLIENT:
                if (StrUtil.isBlank(this.client_secret)) {
                    throw new BusinessException("客户端秘钥不能为空");
                }
                break;
            case TYPE_PASSWORD:
                if (StrUtil.isBlank(this.username)) {
                    throw new BusinessException("客户端秘钥不能为空");
                }
                if (StrUtil.isBlank(this.password)) {
                    throw new BusinessException("客户端秘钥不能为空");
                }
                break;
            case TYPE_REFRESH:
                if (StrUtil.isBlank(this.client_secret)) {
                    throw new BusinessException("客户端秘钥不能为空");
                }
                if (StrUtil.isBlank(this.refresh_token)) {
                    throw new BusinessException("客户端秘钥不能为空");
                }
                break;
            default:
                break;
        }
    }
}
