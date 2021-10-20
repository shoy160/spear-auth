package club.raveland.auth.service.impl;

import club.raveland.auth.dao.ApplicationMapper;
import club.raveland.auth.service.AuthorizeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author shay
 * @date 2021/2/24
 */
@Service
@AllArgsConstructor
public class AuthorizeServiceImpl implements AuthorizeService {
    private final ApplicationMapper mapper;

}
