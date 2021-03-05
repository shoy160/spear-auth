package com.yunzhicloud.auth.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yunzhicloud.auth.dao.PoolMapper;
import com.yunzhicloud.auth.dao.UserMapper;
import com.yunzhicloud.auth.entity.dto.UserDTO;
import com.yunzhicloud.auth.entity.enums.GenderEnum;
import com.yunzhicloud.auth.entity.enums.RegisterTypeEnum;
import com.yunzhicloud.auth.entity.enums.VerifyTypeEnum;
import com.yunzhicloud.auth.entity.po.PoolPO;
import com.yunzhicloud.auth.entity.po.UserPO;
import com.yunzhicloud.auth.service.UserService;
import com.yunzhicloud.core.cache.Cache;
import com.yunzhicloud.core.exception.BusinessException;
import com.yunzhicloud.core.session.YzSession;
import com.yunzhicloud.core.utils.CommonUtils;
import com.yunzhicloud.core.utils.EnumUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author shay
 * @date 2021/3/1
 */
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper mapper;
    private final PoolMapper poolMapper;
    private final YzSession session;
    private final Cache<String, String> cache;
    private final static String VERIFY_CODE_CACHE_KEY = "auth:code:%s:%s";

    private static UserDTO convertToDto(UserPO entity) {
        UserDTO dto = CommonUtils.toBean(entity, UserDTO.class);
        dto.setGender(EnumUtils.getEnum(entity.getGender(), GenderEnum.class));
        dto.setVerifyType(EnumUtils.getEnum(entity.getVerifyType(), VerifyTypeEnum.class));
        dto.setRegisterType(EnumUtils.getEnum(entity.getRegisterType(), RegisterTypeEnum.class));
        return dto;
    }

    @Override
    public UserDTO create(String email, String mobile, String password, String account) {
        String poolId = session.requiredTenantId(String.class);
        PoolPO pool = poolMapper.selectById(poolId);
        if (pool == null) {
            throw new BusinessException("用户池不存在");
        }
        //账号验证
        LambdaQueryWrapper<UserPO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserPO::getPoolId, poolId);
        if (StrUtil.isNotEmpty(email)) {
            wrapper.and(t ->
                    t.and(t1 -> t1.apply("(fd_verify_type & {0})>0", VerifyTypeEnum.Email.getValue())
                            .eq(UserPO::getEmail, email))
                            .or(t1 -> t1.eq(UserPO::getAccount, account)))
            ;
        } else if (StrUtil.isNotEmpty(mobile)) {
            wrapper.eq(UserPO::getMobile, mobile);
        } else {
            throw new BusinessException("请输入手机号码或邮箱");
        }
        Integer count = mapper.selectCount(wrapper);
        if (count > 0) {
            throw new BusinessException("用户已存在");
        }
        UserPO entity = new UserPO();
        entity.setId(IdUtil.simpleUUID());
        entity.setPoolId(poolId);
        entity.setEmail(email);
        entity.setMobile(mobile);
        entity.setAccount(account);
        //密码
        entity.setPassword(password);
        mapper.insert(entity);
        return convertToDto(entity);
    }

    @Override
    public UserDTO detail(String id) {
        UserPO entity = mapper.selectById(id);
        if (entity == null) {
            return null;
        }
        return convertToDto(entity);
    }

    @Override
    public UserDTO login(String account, String password, String verifyCode) {
        String poolId = session.requiredTenantId(String.class);
        LambdaQueryWrapper<UserPO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserPO::getPoolId, poolId)
                .and(t ->
                        t.eq(UserPO::getAccount, account)
                                .or(t1 ->
                                        t1.eq(UserPO::getEmail, account)
                                                .apply("(fd_verify_type & {0})>0", VerifyTypeEnum.Email.getValue())
                                )
                );
        UserPO entity = mapper.selectOne(wrapper);
        if (entity == null) {
            throw new BusinessException("账号不存在");
        }
        if (!password.equalsIgnoreCase(entity.getPassword())) {
            throw new BusinessException("登录密码不正确");
        }
        entity.setLastLoginIp("127.0.0.1");
        entity.setLoginCount(entity.getLoginCount() + 1);
        //todo 登录日志
        mapper.updateById(entity);
        return convertToDto(entity);
    }

    @Override
    public void sendVerifyCode(String mobile, boolean check) {
        String poolId = session.requiredTenantId(String.class);
        //检查手机号码
        if (check) {
            LambdaQueryWrapper<UserPO> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(UserPO::getPoolId, poolId)
                    .eq(UserPO::getMobile, mobile);
            Integer count = mapper.selectCount(wrapper);
            if (count <= 0) {
                throw new BusinessException("手机号码未注册");
            }
        }
        String code = RandomUtil.randomNumbers(6);
        String verifyKey = String.format(VERIFY_CODE_CACHE_KEY, poolId, mobile);
        cache.put(verifyKey, code, 5, TimeUnit.MINUTES);
    }

    @Override
    public UserDTO loginByMobile(String mobile, String code, String verifyCode) {
        //验证码
        String poolId = session.requiredTenantId(String.class);
        String verifyKey = String.format(VERIFY_CODE_CACHE_KEY, poolId, mobile);
        String currentCode = cache.get(verifyKey);

        if (CommonUtils.isEmpty(currentCode) || currentCode.equalsIgnoreCase(code)) {
            throw new BusinessException("短信验证码不正确");
        }

        LambdaQueryWrapper<UserPO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserPO::getPoolId, poolId)
                .eq(UserPO::getMobile, mobile);
        UserPO entity = mapper.selectOne(wrapper);
        if (entity == null) {
            return create(null, mobile, null, null);
        }
        cache.remove(verifyKey);
        return convertToDto(entity);
    }
}
