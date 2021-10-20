package club.raveland.auth.entity.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author shay
 * @date 2021/3/8
 */
@Getter
@Setter
@ToString
public class UserTokenDTO {
    private String id;

    private String userId;

    private String appId;

    private String poolId;
    /**
     * AccessToken
     */
    private String accessToken;
    /**
     * RefreshToken
     */
    private String refreshToken;
    /**
     * UserAgent
     */
    private String userAgent;
    /**
     * AccessToken过期时间
     */
    private Date expiredDate;

    /**
     * RefreshToken过期时间
     */
    private Date expiredRefreshDate;
}
