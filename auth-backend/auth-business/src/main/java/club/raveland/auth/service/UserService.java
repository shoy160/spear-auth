package club.raveland.auth.service;

import club.raveland.auth.entity.dto.UserDTO;
import club.raveland.core.domain.dto.PagedDTO;

/**
 * 用户服务
 *
 * @author shay
 * @date 2021/3/1
 */
public interface UserService {

    /**
     * 创建用户
     *
     * @param email    邮箱
     * @param mobile   手机号
     * @param password 密码
     * @param account  账号
     * @return dto
     */
    UserDTO create(String email, String mobile, String password, String account);

    /**
     * 获取用户详情
     *
     * @param id id
     * @return dto
     */
    UserDTO detail(String id);

    /**
     * 用户分页
     *
     * @param page page
     * @param size size
     * @return paged
     */
    PagedDTO<UserDTO> paged(int page, int size);

    /**
     * 密码登录
     *
     * @param account  账号
     * @param password 密码
     * @param code     图形验证码
     * @return dto
     */
    UserDTO login(String account, String password, String code);

    /**
     * 发送验证码
     *
     * @param mobile 手机号码
     * @param check  是否检查手机号
     */
    default void sendVerifyCode(String mobile, boolean check) {
        sendVerifyCode(mobile, null, check);
    }

    /**
     * 发送验证码
     *
     * @param mobile 手机号码
     * @param code   验证码
     * @param check  是否检查手机号
     */
    void sendVerifyCode(String mobile, String code, boolean check);

    /**
     * 手机验证码登录
     *
     * @param mobile 手机号
     * @param code   短信验证码
     * @return dto
     */
    UserDTO loginByMobile(String mobile, String code);

    /**
     * 更新登录
     *
     * @param id        id
     * @param ip        ip
     * @param userAgent userAgent
     */
    void updateLogin(String id, String ip, String userAgent);

    /**
     * 手机号创建
     *
     * @param mobile   手机号
     * @param password 登录密码
     * @return dto
     */
    default UserDTO createByMobile(String mobile, String password) {
        return create(null, mobile, password, null);
    }

    /**
     * 邮箱创建
     *
     * @param email    邮箱
     * @param password 密码
     * @param account  账号
     * @return dto
     */
    default UserDTO createByEmail(String email, String password, String account) {
        return create(email, null, password, account);
    }


}
