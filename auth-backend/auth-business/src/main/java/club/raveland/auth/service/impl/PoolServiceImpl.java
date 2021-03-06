package club.raveland.auth.service.impl;

import club.raveland.auth.dao.PoolMapper;
import club.raveland.auth.entity.dto.PoolDTO;
import club.raveland.auth.entity.enums.RegisterRuleEnum;
import club.raveland.auth.entity.enums.StateEnum;
import club.raveland.auth.entity.enums.VerifyTypeEnum;
import club.raveland.auth.entity.po.PoolPO;
import club.raveland.auth.service.PoolService;
import club.raveland.core.cache.Cache;
import club.raveland.core.domain.dto.PagedDTO;
import club.raveland.core.exception.BusinessException;
import club.raveland.core.session.RavelandSession;
import club.raveland.core.utils.CommonUtils;
import club.raveland.core.utils.EnumUtils;
import club.raveland.core.utils.IdentityUtils;
import club.raveland.data.utils.PagedUtils;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author shay
 * @date 2021/2/24
 */
@Service
@AllArgsConstructor
public class PoolServiceImpl implements PoolService {
    private final PoolMapper mapper;
    private final RavelandSession session;
    private final Cache<String, PoolDTO> cache;

    private PoolDTO convert(PoolPO entity) {
        if (entity == null) {
            return null;
        }
        PoolDTO dto = CommonUtils.toBean(entity, PoolDTO.class);
        dto.setState(EnumUtils.getEnum(entity.getState(), StateEnum.class));
        dto.setVerifyRule(EnumUtils.getEnum(entity.getVerifyRule(), VerifyTypeEnum.class));
        dto.setRegisterRule(EnumUtils.getEnum(entity.getRegisterRule(), RegisterRuleEnum.class));
        return dto;
    }

    private PoolPO getUserPool(String id) {
        PoolPO po = mapper.selectById(id);
        if (po == null) {
            throw new BusinessException("用户池不存在");
        }
        String userId = session.requiredUserId(String.class);
        if (!userId.equals(po.getTenantId())) {
            throw new BusinessException("只有管理员才能刷新秘钥");
        }
        return po;
    }

    @Override
    public PoolDTO create(String name, String code, String domain, String logo, String remark) {
        if (mapper.exists(code)) {
            throw new BusinessException("用户池编码已存在");
        }
        if (CommonUtils.isNotEmpty(domain) && mapper.existsDomain(domain)) {
            throw new BusinessException("用户池域名已存在");
        }
        String userId = session.requiredUserId(String.class);
        PoolPO entity = new PoolPO();
        entity.setId(IdentityUtils.string16Id());
        entity.setCode(code);
        entity.setSecret(RandomUtil.randomString(32));
        entity.setName(name);
        entity.setDomain(domain);
        entity.setLogo(logo);
        entity.setRemark(remark);
        entity.setTenantId(userId);
        entity.setState(StateEnum.Normal.getValue());
        mapper.insert(entity);
        return convert(entity);
    }

    @Override
    public PoolDTO detail(String id) {
        String key = String.format("auth:pool:%s", id);
        PoolDTO value = cache.get(key);
        if (value != null) {
            return value;
        }
        PoolPO entity = mapper.selectById(id);
        PoolDTO dto = convert(entity);
        cache.put(key, dto, 5, TimeUnit.MINUTES);
        return dto;
    }

    @Override
    public PagedDTO<PoolDTO> paged(int page, int size) {
        String userId = session.requiredUserId(String.class);
        LambdaQueryWrapper<PoolPO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PoolPO::getTenantId, userId);
        Page<PoolPO> paged = mapper.selectPage(new Page<>(page, size), wrapper);
        return PagedUtils.convert(paged, this::convert);
    }

    @Override
    public String refreshSecret(String id) {
        PoolPO po = getUserPool(id);
        String newSecret = RandomUtil.randomString(32);
        LambdaUpdateWrapper<PoolPO> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(PoolPO::getId, po.getId()).set(PoolPO::getSecret, newSecret);
        int row = mapper.update(null, wrapper);
        if (row > 0) {
            return newSecret;
        }
        throw new BusinessException("刷新秘钥失败，请重试");
    }

    @Override
    public void remove(String id) {
        PoolPO po = getUserPool(id);
        int row = mapper.deleteById(po.getId());
        if (row <= 0) {
            throw new BusinessException("删除用户池失败，请重试");
        }
    }
}
