package club.raveland.auth.entity.dto;

import club.raveland.auth.entity.enums.RegisterRuleEnum;
import club.raveland.auth.entity.enums.StateEnum;
import club.raveland.auth.entity.enums.VerifyTypeEnum;
import club.raveland.data.domain.dto.BaseDateDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author shay
 * @date 2021/3/2
 */
@Getter
@Setter
@ToString
@ApiModel
public class PoolDTO extends BaseDateDTO {
    /**
     * 用户池ID
     */
    @ApiModelProperty("用户池ID")
    private String id;

    /**
     * 用户池名称
     */
    @ApiModelProperty("用户池名称")
    private String name;

    /**
     * 用户池编码
     */
    @ApiModelProperty("用户池编码")
    private String code;

    /**
     * 用户池描述
     */
    @ApiModelProperty("用户池描述")
    private String remark;

    /**
     * 用户池Logo
     */
    @ApiModelProperty("用户池Logo")
    private String logo;

    /**
     * 用户池域名
     */
    @ApiModelProperty("用户池域名")
    private String domain;

    /**
     * 用户池秘钥
     */
    @ApiModelProperty("用户池秘钥")
    private String secret;

    /**
     * 用户数
     */
    @ApiModelProperty("用户数")
    private Integer userCount;

    /**
     * 状态：1.正常,4.删除
     */
    @ApiModelProperty("状态")
    private StateEnum state;

    /**
     * 最后注册时间
     */
    @ApiModelProperty("最后注册时间")
    private Date lastRegister;

    /**
     * 是否开启单点登录
     */
    @ApiModelProperty("是否开启单点登录")
    private Boolean enableSso;

    /**
     * 安全域
     */
    @ApiModelProperty("安全域")
    private String corsDomain;

    /**
     * JWT有效时间(秒),默认1296000
     */
    @ApiModelProperty("JWT有效时间")
    private Integer jwtExpired;

    /**
     * 登录失败次数限制
     */
    @ApiModelProperty("登录失败次数限制")
    private Integer failLoginLimit;

    /**
     * 登录失败时间段(秒)
     */
    @ApiModelProperty("登录失败时间段")
    private Integer failLoginTime;

    /**
     * 验证规则：1.邮箱验证，2.手机验证
     */
    @ApiModelProperty("验证规则")
    private VerifyTypeEnum verifyRule;

    /**
     * 注册控制：1.开启允许策略,2.开启禁止策略,4.禁止注册
     */
    @ApiModelProperty("验证规则")
    private RegisterRuleEnum registerRule;
}
