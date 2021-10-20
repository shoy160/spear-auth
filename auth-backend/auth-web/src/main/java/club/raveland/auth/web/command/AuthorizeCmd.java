package club.raveland.auth.web.command;

import cn.hutool.core.util.ArrayUtil;
import club.raveland.core.exception.BusinessException;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 认证参数
 *
 * @author shay
 * @date 2021/2/24
 */
@Getter
@Setter
@ApiModel
public class AuthorizeCmd {
    private final static String TYPE_CODE = "code";
    private final static String TYPE_TOKEN = "token";

    private final static String[] TYPES = new String[]{
            TYPE_CODE, TYPE_TOKEN
    };

    @ApiModelProperty("请求类型(code,token)")
    @NotBlank(message = "请求类型不能为空")
    private String response_type;

    @ApiModelProperty("客户端ID")
    @NotBlank(message = "客户端ID不能为空")
    private String client_id;

    @ApiModelProperty("跳转uri")
    private String redirect_uri;

    @ApiModelProperty("授权范围")
    private String scope;

    public boolean typeCode() {
        return TYPE_CODE.equals(this.response_type);
    }

    public boolean typeToken() {
        return TYPE_TOKEN.equals(this.response_type);
    }

    public void validate() {
        if (!ArrayUtil.contains(TYPES, this.response_type)) {
            throw new BusinessException(String.format("不受支持的请求类型:%s", this.response_type));
        }
    }
}
