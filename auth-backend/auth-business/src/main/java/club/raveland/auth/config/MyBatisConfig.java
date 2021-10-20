package club.raveland.auth.config;

import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import club.raveland.auth.core.AuthConstants;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author shay
 * @date 2021/2/24
 */
@Configuration
@EnableTransactionManagement
@MapperScan(value = AuthConstants.MAPPER_PACKAGE)
public class MyBatisConfig {

}
