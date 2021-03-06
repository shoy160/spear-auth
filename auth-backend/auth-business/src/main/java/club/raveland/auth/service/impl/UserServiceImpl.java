package club.raveland.auth.service.impl;

import club.raveland.auth.dao.PoolMapper;
import club.raveland.auth.dao.UserMapper;
import club.raveland.auth.entity.dto.UserDTO;
import club.raveland.auth.entity.enums.GenderEnum;
import club.raveland.auth.entity.enums.RegisterTypeEnum;
import club.raveland.auth.entity.enums.VerifyTypeEnum;
import club.raveland.auth.entity.po.PoolPO;
import club.raveland.auth.entity.po.UserPO;
import club.raveland.auth.service.UserService;
import club.raveland.core.cache.Cache;
import club.raveland.core.domain.dto.PagedDTO;
import club.raveland.core.exception.BusinessException;
import club.raveland.core.session.RavelandSession;
import club.raveland.core.utils.CommonUtils;
import club.raveland.core.utils.EncryptionUtil;
import club.raveland.core.utils.EnumUtils;
import club.raveland.core.utils.IdentityUtils;
import club.raveland.data.utils.PagedUtils;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
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
    private final RavelandSession session;
    private final Cache<String, String> cache;
    private final static String VERIFY_CODE_CACHE_KEY = "auth:code:%s:%s";

    private UserDTO convert(UserPO entity) {
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
            throw new BusinessException("??????????????????");
        }
        //????????????
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
            throw new BusinessException("??????????????????????????????");
        }
        Integer count = mapper.selectCount(wrapper);
        if (count > 0) {
            throw new BusinessException("???????????????");
        }
        UserPO entity = new UserPO();
        entity.setId(IdentityUtils.string16Id());
        entity.setPoolId(poolId);
        entity.setEmail(email);
        entity.setMobile(mobile);
        entity.setAccount(account);
        if (CommonUtils.isNotEmpty(password)) {
            String salt = RandomUtil.randomString(16);
            entity.setPasswordSalt(salt);
            // ??????
            entity.setPassword(EncryptionUtil.md5(String.format("%s+%s", password, salt)));
        }
        mapper.insert(entity);
        return convert(entity);
    }

    @Override
    public UserDTO detail(String id) {
        UserPO entity = mapper.selectById(id);
        if (entity == null) {
            return null;
        }
        return convert(entity);
    }

    @Override
    public PagedDTO<UserDTO> paged(int page, int size) {
        LambdaQueryWrapper<UserPO> wrapper = new LambdaQueryWrapper<>();
        String poolId = session.requiredTenantId(String.class);
        wrapper.eq(UserPO::getPoolId, poolId);
        Page<UserPO> paged = mapper.selectPage(new Page<>(page, size), wrapper);
        return PagedUtils.convert(paged, this::convert);
    }

    @Override
    public UserDTO login(String account, String password, String verifyCode) {
        // ?????????
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
            throw new BusinessException("???????????????");
        }
        String pwdMd5 = EncryptionUtil.md5(String.format("%s+%s", password, entity.getPasswordSalt()));
        if (!pwdMd5.equalsIgnoreCase(entity.getPassword())) {
            throw new BusinessException("?????????????????????");
        }
        return convert(entity);
    }

    @Override
    public void sendVerifyCode(String mobile, String code, boolean check) {
        String poolId = session.requiredTenantId(String.class);
        //??????????????????
        if (check) {
            LambdaQueryWrapper<UserPO> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(UserPO::getPoolId, poolId)
                    .eq(UserPO::getMobile, mobile);
            Integer count = mapper.selectCount(wrapper);
            if (count <= 0) {
                throw new BusinessException("?????????????????????");
            }
        }
        if (StrUtil.isBlank(code)) {
            code = RandomUtil.randomNumbers(6);
        }
        String verifyKey = String.format(VERIFY_CODE_CACHE_KEY, poolId, mobile);
        cache.put(verifyKey, code, 5, TimeUnit.MINUTES);
    }

    @Override
    public UserDTO loginByMobile(String mobile, String code) {
        //?????????
        String poolId = session.requiredTenantId(String.class);
        String verifyKey = String.format(VERIFY_CODE_CACHE_KEY, poolId, mobile);
        String currentCode = cache.get(verifyKey);
        if (CommonUtils.isEmpty(currentCode) || !currentCode.equalsIgnoreCase(code)) {
            throw new BusinessException("??????????????????");
        }
        LambdaQueryWrapper<UserPO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserPO::getPoolId, poolId)
                .eq(UserPO::getMobile, mobile);
        UserPO entity = mapper.selectOne(wrapper);
        if (entity == null) {
            return create(null, mobile, null, null);
        }
        cache.remove(verifyKey);
        return convert(entity);
    }

    @Override
    public void updateLogin(String id, String ip, String userAgent) {
        LambdaUpdateWrapper<UserPO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(UserPO::getId, id)
                .set(UserPO::getLastLoginIp, ip)
                .set(UserPO::getLastLoginDate, new Date())
                .setSql("fd_login_count=fd_login_count+1");
        //todo ????????????

        mapper.update(null, updateWrapper);
    }
}
