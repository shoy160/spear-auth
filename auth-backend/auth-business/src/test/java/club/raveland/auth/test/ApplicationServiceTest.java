package club.raveland.auth.test;

import club.raveland.core.utils.IdentityUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author shay
 * @date 2021/2/24
 */
@Slf4j
public class ApplicationServiceTest {

    @Test
    public void test() {
        String id = IdentityUtils.string16Id();
        log.info(id);
    }
}
