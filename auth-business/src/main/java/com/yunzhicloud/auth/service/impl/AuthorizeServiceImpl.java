package com.yunzhicloud.auth.service.impl;

import com.yunzhicloud.auth.dao.ApplicationMapper;
import com.yunzhicloud.auth.service.AuthorizeService;
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
