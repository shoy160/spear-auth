package com.yunzhicloud.auth.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author shay
 * @date 2021/3/4
 */
@Getter
@Setter
@ToString
@ApiModel
public class PoolStatisticDTO {

    @ApiModelProperty("用户池ID")
    private String id;

    /**
     * 今日新增
     */
    @ApiModelProperty("今日新增")
    private Integer countNewUser;
    /**
     * 7日登陆
     */
    @ApiModelProperty("7日登陆")
    private Integer countLogin;
    /**
     * 7日注册
     */
    @ApiModelProperty("7日注册")
    private Integer countRegister;
    /**
     * 总用户数
     */
    @ApiModelProperty("总用户数")
    private Integer countUser;
    /**
     * 用户新增趋势
     */
    @ApiModelProperty("用户新增趋势")
    private Map<Date, Integer> userRegisters;

    /**
     * 最近登录
     */
    @ApiModelProperty("最近登录")
    private List<UserDTO> lastLoginList;

    /**
     * 最近注册
     */
    @ApiModelProperty("最近注册")
    private List<UserDTO> lastRegisterList;
}
