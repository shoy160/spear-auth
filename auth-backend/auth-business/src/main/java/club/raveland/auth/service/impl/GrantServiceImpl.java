package club.raveland.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import club.raveland.auth.dao.GrantMapper;
import club.raveland.auth.entity.dto.GrantDTO;
import club.raveland.auth.entity.enums.GrantPolicyEnum;
import club.raveland.auth.entity.enums.GrantTargetTypeEnum;
import club.raveland.auth.entity.enums.GrantTypeEnum;
import club.raveland.auth.entity.po.GrantPO;
import club.raveland.auth.service.GrantService;
import club.raveland.core.utils.CommonUtils;
import club.raveland.core.utils.EnumUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shay
 * @date 2021/3/9
 */
@Service
@AllArgsConstructor
public class GrantServiceImpl implements GrantService {

    private final GrantMapper mapper;

    private GrantDTO convert(GrantPO entity) {
        GrantDTO dto = CommonUtils.toBean(entity, GrantDTO.class);
        dto.setType(EnumUtils.getEnum(entity.getType(), GrantTypeEnum.class));
        dto.setTargetType(EnumUtils.getEnum(entity.getTargetType(), GrantTargetTypeEnum.class));
        dto.setPolicy(EnumUtils.getEnum(entity.getPolicy(), GrantPolicyEnum.class));
        return dto;
    }

    @Override
    public List<GrantDTO> list(String poolId, String namespace, GrantTypeEnum type) {
        LambdaQueryWrapper<GrantPO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(GrantPO::getPoolId, poolId)
                .eq(GrantPO::getNamespace, namespace)
                .eq(GrantPO::getType, type);
        List<GrantPO> entities = mapper.selectList(wrapper);
        List<GrantDTO> dtoList = new ArrayList<>();
        for (GrantPO entity : entities) {
            dtoList.add(convert(entity));
        }
        return dtoList;
    }
}
