package club.raveland.auth.entity.po;

import club.raveland.core.domain.HaveDate;
import club.raveland.core.domain.SoftDelete;
import club.raveland.core.domain.entity.BasePO;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author shoy
 * @date 2021/10/20
 */
@Getter
@Setter
public class BaseAuthPO extends BasePO implements HaveDate, SoftDelete {

    private static final long serialVersionUID = -1785863488488971606L;

    /**
     * 是否删除 0否 1是
     */
    @TableLogic
    @TableField("fd_deleted")
    private boolean isDel;

    /**
     * 创建时间
     */
    @TableField(value = "fd_create_date", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新时间
     */
    @Version
    @TableField(value = "fd_update_date")
    private Date updateTime;
}