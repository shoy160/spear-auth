package club.raveland.auth.core;

import club.raveland.auth.core.domain.dto.TokenDTO;

import java.util.Map;

/**
 * @author shay
 * @date 2021/3/8
 */
public interface TokenStore {
    
    /**
     * 生成凭证
     *
     * @param claims claims
     * @return token
     */
    TokenDTO generateToken(Map<String, String> claims);
}
